/**
 * @ Author: turk
 * @ Description: Preverjanje tipov.
 */

package compiler.seman.type;

import static common.RequireNonNull.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;

import common.Constants;
import common.Report;
import compiler.common.Visitor;
import compiler.parser.ast.def.*;
import compiler.parser.ast.def.FunDef.Parameter;
import compiler.parser.ast.expr.*;
import compiler.parser.ast.expr.Unary.Operator;
import compiler.parser.ast.type.*;
import compiler.seman.common.NodeDescription;
import compiler.seman.type.type.Type;
import compiler.seman.type.type.Type.Atom.Kind;

public class TypeChecker implements Visitor {
    /**
     * Opis vozlišč in njihovih definicij.
     */
    private final NodeDescription<Def> definitions;

    /**
     * Opis vozlišč, ki jim priredimo podatkovne tipe.
     */
    private NodeDescription<Type> types;

    public TypeChecker(NodeDescription<Def> definitions, NodeDescription<Type> types) {
        requireNonNull(definitions, types);
        this.definitions = definitions;
        this.types = types;
    }

    HashSet<TypeDef> hashSet = new HashSet<TypeDef>();

    @Override
    public void visit(Call call) {
        var argumentTypes = new ArrayList<Type>();
        call.arguments.stream().forEach(argument -> argument.accept(this));

        call.arguments.stream().forEach(argument -> {
            var findType = types.valueFor(argument);
            findType.ifPresentOrElse(value -> {
                argumentTypes.add(value);
            }, () -> Report.error(call.position, "Napacen tip v argumentu!"));
        });

        if (Constants.Library.contains(call.name)) {
            switch (call.name) {
                case Constants.printStringLabel -> {
                    if (argumentTypes.size() != 1) {
                        Report.error(call.position, "Napacno stevilo argumentov!");
                    }
                    if (!argumentTypes.get(0).isStr()) {
                        Report.error(call.position, "Napacen tip argumenta!");
                    }
                    types.store(new Type.Atom(Kind.STR), call);
                    return;
                }
                case Constants.printLogLabel -> {
                    if (argumentTypes.size() != 1) {
                        Report.error(call.position, "Napacno stevilo argumentov!");
                    }
                    if (!argumentTypes.get(0).isLog()) {
                        Report.error(call.position, "Napacen tip argumenta!");
                    }
                    types.store(new Type.Atom(Kind.LOG), call);
                    return;
                }
                case Constants.randIntLabel -> {
                    if (argumentTypes.size() != 2) {
                        Report.error(call.position, "Napacno stevilo argumentov!");
                    }
                    if (!argumentTypes.get(0).isInt() || !argumentTypes.get(1).isInt()) {
                        Report.error(call.position, "Napacen tip argumenta!");
                    }
                    types.store(new Type.Atom(Kind.INT), call);
                    return;
                }
                default -> {
                    if (argumentTypes.size() != 1) {
                        Report.error(call.position, "Napacno stevilo argumentov!");
                    }
                    if (!argumentTypes.get(0).isInt()) {
                        Report.error(call.position, "Napacen tip argumenta!");
                    }
                    types.store(new Type.Atom(Kind.INT), call);
                    return;
                }
            }
        }

        var def = definitions.valueFor(call);
        if (def.isPresent()) {
            var getDef = def.get();
            var findType = types.valueFor(getDef);

            if (!findType.isPresent()) {
                getDef.accept(this);
                findType = types.valueFor(getDef);
            }

            if (findType.isPresent()) {
                var getType = findType.get();
                var asFunc = getType.asFunction();
                if (asFunc.isPresent()) {
                    var getAsFunc = asFunc.get();
                    if (getAsFunc.parameters.size() != argumentTypes.size()) {
                        Report.error(call.position, "Napačno stevilo argumentov!");
                    }
                    for (int i = 0; i < getAsFunc.parameters.size(); i++) {
                        if (!(getAsFunc.parameters.get(i).equals(argumentTypes.get(i)))) {
                            Report.error(call.position, "Argument napacnega tipa!");
                        }
                    }

                    var compareFunc = new Type.Function(argumentTypes, getAsFunc.returnType);
                    if (getAsFunc.equals(compareFunc)) {
                        types.store(getAsFunc.returnType, call);
                        return;
                    }
                }
            }
            Report.error(call.position, "Tip funkcije ni definiran!");
        }
    }

    @Override
    public void visit(Binary binary) {
        binary.left.accept(this);
        binary.right.accept(this);

        var leftType = types.valueFor(binary.left);
        var rightType = types.valueFor(binary.right);

        if (leftType.isPresent() && rightType.isPresent()) {
            if (binary.operator.isAndOr()) {
                if (leftType.get().isLog() && rightType.get().isLog()) {
                    types.store(new Type.Atom(Kind.LOG), binary);
                    return;
                } else {
                    Report.error(binary.position, "Napaka pri logičnem izrazu!");
                }
            } else if (binary.operator.isArithmetic()) {
                if (leftType.get().isInt() && rightType.get().isInt()) {
                    types.store(new Type.Atom(Kind.INT), binary);
                    return;
                } else {
                    Report.error(binary.position, "Napaka pri aritmetičnem izrazu!");
                }
            } else if (binary.operator.isComparison()) {
                if (leftType.get().isInt() && rightType.get().isInt()) {
                    types.store(new Type.Atom(Kind.LOG), binary);
                    return;
                } else if (leftType.get().isLog() && rightType.get().isLog()) {
                    types.store(new Type.Atom(Kind.LOG), binary);
                    return;
                } else {
                    Report.error(binary.position, "Napaka pri primerjalnem izrazu!");
                }
            } else if (binary.operator.equals(Binary.Operator.ARR)) {
                if (leftType.get().isArray() && rightType.get().isInt()) {
                    var asArray = leftType.get().asArray();
                    types.store(asArray.get().type, binary);
                    return;
                } else
                    Report.error(binary.position, "Napaka pri izrazu s tabelami!");
            } else if (binary.operator.equals(Binary.Operator.ASSIGN)) {
                if (leftType.get().equals(rightType.get()) && ((rightType.get().isLog()) || rightType.get().isInt()
                        || rightType.get().isStr())) {
                    types.store(leftType.get(), binary);
                    return;
                } else
                    Report.error(binary.position, "Nedovoljeno prirejanje!");
            }
        }
    }

    @Override
    public void visit(Block block) {
        block.expressions.stream().forEach(expression -> {
            expression.accept(this);
        });
        var lastExpression = block.expressions.get(block.expressions.size() - 1);
        var lastExpressionType = types.valueFor(lastExpression);
        lastExpressionType.ifPresentOrElse(value -> types.store(value, block), () -> {
            Report.error(block.position, "Neveljaven tip zadnjega izraza!");
        });
    }

    @Override
    public void visit(For forLoop) {
        forLoop.counter.accept(this);
        forLoop.low.accept(this);
        forLoop.high.accept(this);
        forLoop.body.accept(this);
        forLoop.step.accept(this);

        var counterType = types.valueFor(forLoop.counter);
        counterType.ifPresentOrElse(value -> {
            if (!value.isInt())
                Report.error(forLoop.counter.position, "Neveljaven tip števca v for zanki!");
        }, () -> Report.error(forLoop.counter.position, "Napaka v števcu zanke!"));
        var lowType = types.valueFor(forLoop.low);
        lowType.ifPresentOrElse(value -> {
            if (!value.isInt())
                Report.error(forLoop.low.position, "Neveljaven tip spodnje meje v for zanki!");
        }, () -> Report.error(forLoop.low.position, "Napaka v spodnji meji zanke!"));
        var highType = types.valueFor(forLoop.high);
        highType.ifPresentOrElse(value -> {
            if (!value.isInt())
                Report.error(forLoop.high.position, "Neveljaven tip zgornje meje v for zanki!");
        }, () -> Report.error(forLoop.high.position, "Napaka v zgornji meji zanke!"));
        var stepType = types.valueFor(forLoop.step);
        stepType.ifPresentOrElse(value -> {
            if (!value.isInt())
                Report.error(forLoop.step.position, "Neveljaven tip inkrementa v for zanki!");
        }, () -> Report.error(forLoop.step.position, "Napaka v inkrementu zanke!"));

        types.store(new Type.Atom(Kind.VOID), forLoop);
    }

    @Override
    public void visit(Name name) {
        var def = definitions.valueFor(name);
        def.ifPresent(value -> {
            value.accept(this);
            var type = types.valueFor(value);
            type.ifPresentOrElse(value2 -> types.store(value2, name), () -> {
                Report.error(name.position, "Napaka v imenu!");
            });
        });
    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        ifThenElse.condition.accept(this);
        ifThenElse.thenExpression.accept(this);
        ifThenElse.elseExpression.ifPresent(value -> value.accept(this));

        var conditionType = types.valueFor(ifThenElse.condition);
        conditionType.ifPresentOrElse(value -> {
            if (!value.isLog())
                Report.error(ifThenElse.condition.position, "Neveljaven tip pogojne izjave!");
        }, () -> Report.error(ifThenElse.condition.position, "Napaka v pogojni izjavi!"));

        types.store(new Type.Atom(Kind.VOID), ifThenElse);
    }

    @Override
    public void visit(Literal literal) {
        switch (literal.type) {
            case INT:
                types.store(new Type.Atom(Kind.INT), literal);
                break;
            case LOG:
                types.store(new Type.Atom(Kind.LOG), literal);
                break;
            case STR:
                types.store(new Type.Atom(Kind.STR), literal);
                break;
            default:
                Report.error(literal.position, "Neznan tip!");
        }
    }

    @Override
    public void visit(Unary unary) {
        unary.expr.accept(this);
        var exprType = types.valueFor(unary.expr);
        exprType.ifPresentOrElse(value -> {
            if (value.isLog() && unary.operator.equals(Operator.NOT)) {
                types.store(new Type.Atom(Kind.LOG), unary);
                return;
            }
            if (value.isInt() && (unary.operator.equals(Operator.SUB) || unary.operator.equals(Operator.ADD))) {
                types.store(new Type.Atom(Kind.INT), unary);
                return;
            } else {
                Report.error(unary.position, "Napaka v unarnem izrazu!");
            }
        }, () -> Report.error(unary.position, "Napaka v izrazu!"));
    }

    @Override
    public void visit(While whileLoop) {
        whileLoop.condition.accept(this);
        whileLoop.body.accept(this);

        var conditionType = types.valueFor(whileLoop.condition);
        conditionType.ifPresentOrElse(value -> {
            if (!value.isLog())
                Report.error(whileLoop.condition.position, "Neveljaven tip pogoja v while zanki!");
        }, () -> Report.error(whileLoop.condition.position, "Napaka v pogoju zanke!"));

        types.store(new Type.Atom(Kind.VOID), whileLoop);
    }

    @Override
    public void visit(Where where) {
        where.defs.accept(this);
        where.expr.accept(this);
        var exprType = types.valueFor(where.expr);
        exprType.ifPresentOrElse(value -> types.store(value, where), () -> {
            Report.error(where.position, "Neveljaven tip izraza!");
        });
    }

    @Override
    public void visit(Defs defs) {
        defs.definitions.stream().forEach(definition -> {
            definition.accept(this);
        });
    }

    @Override
    public void visit(FunDef funDef) {
        funDef.parameters.stream().forEach(param -> param.accept(this));
        funDef.type.accept(this);

        var parameterTypes = new ArrayList<Type>();
        funDef.parameters.stream().forEach(param -> {
            var paramType = types.valueFor(param);
            paramType.ifPresentOrElse(value -> parameterTypes.add(value),
                    () -> Report.error(funDef.position, "Napaka v tipu parametra!"));
        });

        var funType = types.valueFor(funDef.type);
        funType.ifPresentOrElse(value -> types.store(new Type.Function(parameterTypes, value), funDef),
                () -> Report.error(funDef.type.position, "Napaka v tipih funkcije"));

        funDef.body.accept(this);
        var bodyType = types.valueFor(funDef.body);
        bodyType.ifPresentOrElse(value -> {
            var getFunType = funType.get();
            if (getFunType.equals(value)) {
                types.store(new Type.Function(parameterTypes, getFunType), funDef);
                return;
            } else {
                Report.error(funDef.type.position, "Neveljaven return type v funkciji!");
            }
        }, () -> Report.error(funDef.body.position, "Napaka v body funkcije!"));
    }

    @Override
    public void visit(TypeDef typeDef) {
        if (hashSet.contains(typeDef))
            Report.error("Zaznan cikel!");
        hashSet.add(typeDef);
        typeDef.type.accept(this);
        var def = types.valueFor(typeDef.type);
        def.ifPresentOrElse(value -> {
            types.store(value, typeDef);
        }, () -> Report.error(typeDef.position, "Type " + typeDef.name + " is not defined " + typeDef.position));
    }

    @Override
    public void visit(VarDef varDef) {
        varDef.type.accept(this);
        var def = types.valueFor(varDef.type);
        def.ifPresentOrElse(value -> {
            types.store(value, varDef);
        }, () -> Report.error(varDef.position,
                "The type of the variable " + varDef.name + " is not defined " + varDef.position));
    }

    @Override
    public void visit(Parameter parameter) {
        parameter.type.accept(this);
        var def = types.valueFor(parameter.type);
        def.ifPresentOrElse(value -> {
            types.store(value, parameter);
        }, () -> Report.error(parameter.position,
                "The type of the parameter " + parameter.name + " is not defined " + parameter.position));
    }

    @Override
    public void visit(Array array) {
        array.type.accept(this);
        var def = types.valueFor(array.type);

        def.ifPresentOrElse(value -> {
            var asAtom = value.asAtom();
            asAtom.ifPresent(value2 -> {
                types.store(new Type.Array(array.size, value2), array);
                return;
            });

            var asArray = value.asArray();
            asArray.ifPresent(value3 -> {
                types.store(new Type.Array(array.size, value3), array);
                return;
            });
        }, () -> Report.error("The type of the array " + array.position + " is not defined"));
    }

    @Override
    public void visit(Atom atom) {
        hashSet.clear();
        switch (atom.type) {
            case INT -> types.store(new Type.Atom(Kind.INT), atom);
            case LOG -> types.store(new Type.Atom(Kind.LOG), atom);
            case STR -> types.store(new Type.Atom(Kind.STR), atom);
            default -> Report.error(atom.position, "Nepoznan tip atomarnega izraza!");
        }
    }

    @Override
    public void visit(TypeName name) {
        var def = definitions.valueFor(name);
        def.ifPresentOrElse(value -> {
            var findType = types.valueFor(value);
            if (!findType.isPresent()) {
                value.accept(this);
                findType = types.valueFor(value);
            }
            findType.ifPresentOrElse(type -> {
                var asAtom = type.asAtom();
                asAtom.ifPresent(atom -> {
                    types.store(atom, name);
                    return;
                });

                var asArray = type.asArray();
                asArray.ifPresent(array -> {
                    types.store(array, name);
                    return;
                });
            }, () -> Report.error(name.position, "Nedefiniran tip!"));
        }, () -> Report.error(name.position, "Nedefiniran tip!"));
    }
}