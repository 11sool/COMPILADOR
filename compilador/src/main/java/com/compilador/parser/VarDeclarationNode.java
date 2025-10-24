package com.compilador.parser;

public class VarDeclarationNode extends ASTNode {
    private final String type;
    private final String name;
    private final ASTNode value;

    public VarDeclarationNode(String type, String name, ASTNode value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ASTNode getValue() {
        return value;
    }

    @Override
    public String toString(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(getIndent(indent)).append("VarDeclaration\n");
        sb.append(getIndent(indent + 1)).append("type: ").append(type).append("\n");
        sb.append(getIndent(indent + 1)).append("name: ").append(name).append("\n");
        sb.append(getIndent(indent + 1)).append("value:\n");
        sb.append(value.toString(indent + 2));
        return sb.toString();
    }
}