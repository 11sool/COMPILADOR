package com.compilador.parser;

public class NumberNode extends ASTNode {
    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString(int indent) {
        return getIndent(indent) + "Number(" + value + ")";
    }
}