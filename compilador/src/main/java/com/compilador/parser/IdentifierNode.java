package com.compilador.parser;

public class IdentifierNode extends ASTNode {
    private final String name;

    public IdentifierNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(int indent) {
        return getIndent(indent) + "Identifier(" + name + ")";
    }
}