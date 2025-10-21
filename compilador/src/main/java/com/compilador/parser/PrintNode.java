package com.compilador.parser;

public class PrintNode extends ASTNode {
    private final ASTNode expression;

    public PrintNode(ASTNode expression) {
        this.expression = expression;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public String toString(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(getIndent(indent)).append("Print\n");
        sb.append(expression.toString(indent + 1));
        return sb.toString();
    }
}