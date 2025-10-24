package com.compilador.semantic;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    public static class Symbol {
        private final String name;
        private final String type;

        public Symbol(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return String.format("Symbol(name='%s', type='%s')", name, type);
        }
    }

    private final Map<String, Symbol> symbols;

    public SymbolTable() {
        this.symbols = new HashMap<>();
    }

    public void declare(String name, String type) throws SemanticException {
        if (symbols.containsKey(name)) {
            throw new SemanticException(
                    String.format("Error semántico: La variable '%s' ya está declarada", name)
            );
        }
        symbols.put(name, new Symbol(name, type));
    }

    public Symbol lookup(String name) {
        return symbols.get(name);
    }

    public boolean exists(String name) {
        return symbols.containsKey(name);
    }

    public String getType(String name) throws SemanticException {
        Symbol symbol = lookup(name);
        if (symbol == null) {
            throw new SemanticException(
                    String.format("Error semántico: La variable '%s' no está declarada", name)
            );
        }
        return symbol.getType();
    }

    public void print() {
        System.out.println("\n=== Tabla de Símbolos ===");
        if (symbols.isEmpty()) {
            System.out.println("(vacía)");
        } else {
            for (Symbol symbol : symbols.values()) {
                System.out.println("  " + symbol);
            }
        }
    }
}