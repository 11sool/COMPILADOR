package com.compilador.parser;

public abstract class ASTNode {

    public abstract String toString(int indent);

    @Override
    public String toString() {
        return toString(0);
    }

    protected String getIndent(int indent) {
        return "  ".repeat(indent);
    }
}