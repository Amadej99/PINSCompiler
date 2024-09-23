/**
 * @ Author: turk
 * @ Description: Kompleksnejša, a hitrejša, implementacija simbolne tabele.
 */

package compiler.seman.name.env;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import compiler.parser.ast.def.Def;
import org.bytedeco.llvm.LLVM.LLVMValueRef;

public class FastSymbolTable implements SymbolTable {
    private int currentScope = 0;
    private Map<Integer, ArrayList<String>> scopes = new HashMap<>();
    private Map<String, ArrayList<Triplet>> env = new HashMap<>();

    public FastSymbolTable() {
        scopes.put(currentScope, new ArrayList<>());
    }

    @Override
    public void insert(Def definition, Optional<LLVMValueRef> valueRef) throws DefinitionAlreadyExistsException {
        var definitions = env.get(definition.name);
        if (definitions != null) {
            if (!definitions.isEmpty() && definitions.get(definitions.size() - 1).scope == currentScope) {
                throw new DefinitionAlreadyExistsException(definition);
            }
            definitions.add(new Triplet(currentScope, definition, valueRef));
        } else {
            var stack = new ArrayList<Triplet>();
            stack.add(new Triplet(currentScope, definition, valueRef));
            env.put(definition.name, stack);
        }
        var existingScope = scopes.get(currentScope);
        if (existingScope == null) {
            var list = new ArrayList<String>();
            list.add(definition.name);
            scopes.put(currentScope, list);
        } else {
            existingScope.add(definition.name);
        }
    }

    @Override
    public Optional<Triplet> definitionFor(String name) {
        var definitions = env.get(name);
        if (definitions == null || definitions.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(definitions.get(definitions.size() - 1));
    }

    public Optional<Triplet> definitionFor(LLVMValueRef valueRef) {
        for (var entry : env.entrySet()) {
            for (var triplet : entry.getValue()) {
                if (triplet.valueRef.isPresent() && triplet.valueRef.get().equals(valueRef)) {
                    return Optional.of(triplet);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void pushScope() {
        currentScope++;
    }

    @Override
    public void popScope() {
        var scope = scopes.get(currentScope);
        if (scope == null) {
            currentScope--;
            return;
        }
        for (var name : scope) {
            var definitions = env.get(name);
            if (definitions.get(definitions.size() - 1).scope != currentScope) {
                throw new RuntimeException("Interna napaka prevajalnika.");
            }
            definitions.remove(definitions.size() - 1);
        }
        scopes.remove(currentScope);
        currentScope--;
    }

    @Override
    public void popScopeNonDestructive(){
        currentScope--;
    }

    public int getCurrentScope() {
        return currentScope;
    }
}
