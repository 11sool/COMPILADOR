package com.compilador.parser;

import java.util.List;

public class ProgramNode extends ASTNode {
    private final List<ASTNode> statements;

    public ProgramNode(List<ASTNode> statements) {
        this.statements = statements;
    }

    public List<ASTNode> getStatements() {
        return statements;
    }

    @Override
    public String toString(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(getIndent(indent)).append("Program\n");
        for (ASTNode statement : statements) {
            sb.append(statement.toString(indent + 1)).append("\n");
        }
        return sb.toString();
    }
}