/**
 * @ Author: turk
 * @ Description: Preverjanje tipov.
 */

package compiler.seman.type;

import static common.RequireNonNull.requireNonNull;

import java.util.ArrayList;

import common.Report;
import compiler.common.Visitor;
import compiler.parser.ast.def.*;
import compiler.parser.ast.def.FunDef.Parameter;
import compiler.parser.ast.expr.*;
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

    @Override
    public void visit(Call call) {
        var def = definitions.valueFor(call);
        def.ifPresent(value -> {
            var type = types.valueFor(value);
            type.ifPresent(typeValue -> {
                typeValue.asFunction().ifPresent(function -> {
                    if (function.parameters.size() != call.arguments.size())
                        Report.error("Napačno število argumentov!");
                    for (int i = 0; i < function.parameters.size(); i++) {
                        call.arguments.get(i).accept(this);
                        var argumentType = types.valueFor(call.arguments.get(i));
                        if (argumentType.isPresent()) {
                            if (!argumentType.get().equals(function.parameters.get(i))) {
                                Report.error("Argument " + i + " ni pravilnega tipa!");
                            }
                        }
                    }
                    types.store(function.returnType, call);
                });
            });
        });
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
                } else {
                    Report.error("Napaka pri logičnem izrazu!");
                }
            } else if (binary.operator.isArithmetic()) {
                if (leftType.get().isInt() && rightType.get().isInt()) {
                    types.store(new Type.Atom(Kind.INT), binary);
                } else {
                    Report.error("Napaka pri aritmetičnem izrazu!");
                }
            } else if (binary.operator.isComparison()) {
                if (leftType.get().isInt() && rightType.get().isInt()) {
                    types.store(new Type.Atom(Kind.LOG), binary);
                } else if (leftType.get().isLog() && rightType.get().isLog()) {
                    types.store(new Type.Atom(Kind.LOG), binary);
                } else {
                    Report.error("Napaka pri primerjalnem izrazu!");
                }
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
            Report.error("Neveljaven tip!");
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
                Report.error("Neveljaven tip števca v for zanki!");
        }, () -> Report.error("Napaka v števcu zanke!"));
        var lowType = types.valueFor(forLoop.low);
        lowType.ifPresentOrElse(value -> {
            if (!value.isInt())
                Report.error("Neveljaven tip spodnje meje v for zanki!");
        }, () -> Report.error("Napaka v spodnji meji zanke!"));
        var highType = types.valueFor(forLoop.high);
        highType.ifPresentOrElse(value -> {
            if (!value.isInt())
                Report.error("Neveljaven tip zgornje meje v for zanki!");
        }, () -> Report.error("Napaka v zgornji meji zanke!"));
        var stepType = types.valueFor(forLoop.step);
        stepType.ifPresentOrElse(value -> {
            if (!value.isInt())
                Report.error("Neveljaven tip inkrementa v for zanki!");
        }, () -> Report.error("Napaka v inkrementu zanke!"));

        types.store(new Type.Atom(Kind.VOID), forLoop);
    }

    @Override
    public void visit(Name name) {
        var def = definitions.valueFor(name);
        def.ifPresent(value -> {
            var type = types.valueFor(value);
            type.ifPresent(value2 -> types.store(value2, name));
        });
    }

    @Override
    public void visit(IfThenElse ifThenElse) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
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
                Report.error("Unknown type " + literal.position);
        }
    }

    @Override
    public void visit(Unary unary) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(While whileLoop) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(Where where) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
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
        funDef.body.accept(this);
        var bodyType = types.valueFor(funDef.body);
        if (bodyType.isPresent()) {
            var funType = types.valueFor(funDef.type);
            if (funType.isPresent()) {
                if (bodyType.get().equals(funType.get())) {
                    ArrayList<Type> paramTypes = new ArrayList<>();
                    funDef.parameters.stream().forEach(param -> {
                        var paramType = types.valueFor(param);
                        paramType.ifPresent(value -> paramTypes.add(value));
                    });
                    types.store(new Type.Function(paramTypes, funType.get()), funDef);
                    return;
                }
                Report.error("Function mismatch! " + funDef.name + " " + funType.get() + " " + bodyType.get());
            }
        }
    }

    @Override
    public void visit(TypeDef typeDef) {
        typeDef.type.accept(this);
        var def = types.valueFor(typeDef.type);
        def.ifPresentOrElse(value -> {
            types.store(value, typeDef);
        }, () -> Report.error("Type " + typeDef.name + " is not defined " + typeDef.position));
    }

    @Override
    public void visit(VarDef varDef) {
        varDef.type.accept(this);
        var def = types.valueFor(varDef.type);
        def.ifPresentOrElse(value -> {
            types.store(value, varDef);
        }, () -> Report.error("The type of the variable " + varDef.name + " is not defined " + varDef.position));
    }

    @Override
    public void visit(Parameter parameter) {
        parameter.type.accept(this);
        var def = types.valueFor(parameter.type);
        def.ifPresentOrElse(value -> {
            types.store(value, parameter);
        }, () -> Report.error("The type of the parameter " + parameter.name + " is not defined " + parameter.position));
    }

    @Override
    public void visit(Array array) {
        array.type.accept(this);
        var def = types.valueFor(array.type);
        def.ifPresentOrElse(value -> {
            types.store(new Type.Array(array.size, value), array);
        }, () -> Report.error("The type of the array " + array.position + " is not defined"));
    }

    @Override
    public void visit(Atom atom) {
        switch (atom.type) {
            case INT:
                types.store(new Type.Atom(Kind.INT), atom);
                break;
            case LOG:
                types.store(new Type.Atom(Kind.LOG), atom);
                break;
            case STR:
                types.store(new Type.Atom(Kind.STR), atom);
                break;
            default:
                Report.error("Unknown type " + atom.position);
        }
    }

    @Override
    public void visit(TypeName name) {
        var def = definitions.valueFor(name);
        if (def.isPresent()) {
            var getDef = def.get();
            var findType = types.valueFor(getDef);
            if (!findType.isPresent()) {
                getDef.accept(this);
                findType = types.valueFor(getDef);
            }
            if (findType.isPresent()) {
                var getType = findType.get();
                var asAtom = getType.asAtom();
                if (asAtom.isPresent()) {
                    types.store(asAtom.get(), name);
                    return;
                }
            }
        }

        Report.error("Type " + name.identifier + " is not defined " + name.position);
    }
}