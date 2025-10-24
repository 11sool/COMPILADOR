package com.compilador.semantic;

import com.compilador.parser.*;

public class SemanticAnalyzer {

    private final SymbolTable symbolTable;

    public SemanticAnalyzer() {
        this.symbolTable = new SymbolTable();
    }

    public void analyze(ProgramNode program) throws SemanticException {
        System.out.println("\n--- FASE 3: ANÁLISIS SEMÁNTICO ---");

        for (ASTNode statement : program.getStatements()) {
            analyzeStatement(statement);
        }

        symbolTable.print();
        System.out.println("\n✓ Análisis semántico completado sin errores");
    }

    private void analyzeStatement(ASTNode node) throws SemanticException {
        if (node instanceof VarDeclarationNode) {
            analyzeVarDeclaration((VarDeclarationNode) node);
        } else if (node instanceof PrintNode) {
            analyzePrint((PrintNode) node);
        }
    }

    private void analyzeVarDeclaration(VarDeclarationNode node) throws SemanticException {
        String varName = node.getName();
        String varType = node.getType();

        if (symbolTable.exists(varName)) {
            throw new SemanticException(
                    String.format("Error: La variable '%s' ya está declarada", varName)
            );
        }

        analyzeExpression(node.getValue());
        symbolTable.declare(varName, varType);

        System.out.println("  ✓ Variable declarada: " + varName + " (" + varType + ")");
    }

    private void analyzePrint(PrintNode node) throws SemanticException {
        analyzeExpression(node.getExpression());
        System.out.println("  ✓ Instrucción print verificada");
    }

    private void analyzeExpression(ASTNode node) throws SemanticException {
        if (node instanceof IdentifierNode) {
            String varName = ((IdentifierNode) node).getName();
            if (!symbolTable.exists(varName)) {
                throw new SemanticException(
                        String.format("Error: La variable '%s' no está declarada", varName)
                );
            }
        } else if (node instanceof BinaryOpNode) {
            BinaryOpNode binOp = (BinaryOpNode) node;
            analyzeExpression(binOp.getLeft());
            analyzeExpression(binOp.getRight());
        }
    }
}