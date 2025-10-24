package com.compilador.parser;

import com.compilador.lexer.Token;
import com.compilador.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int position;
    private Token currentToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.currentToken = tokens.get(0);
    }

    private void advance() {
        position++;
        if (position < tokens.size()) {
            currentToken = tokens.get(position);
        }
    }

    private void expect(TokenType type) {
        if (currentToken.getType() != type) {
            throw new RuntimeException(
                    String.format("Error de sintaxis: Se esperaba %s pero se encontró %s en línea %d, columna %d",
                            type, currentToken.getType(), currentToken.getLine(), currentToken.getColumn())
            );
        }
        advance();
    }

    public ProgramNode parse() {
        List<ASTNode> statements = new ArrayList<>();

        while (currentToken.getType() != TokenType.EOF) {
            statements.add(parseStatement());
        }

        return new ProgramNode(statements);
    }

    private ASTNode parseStatement() {
        if (currentToken.getType() == TokenType.KEYWORD) {
            String keyword = currentToken.getValue();

            if (keyword.equals("int") || keyword.equals("float")) {
                return parseVarDeclaration();
            } else if (keyword.equals("print")) {
                return parsePrint();
            } else {
                throw new RuntimeException("Palabra reservada no soportada: " + keyword);
            }
        }

        throw new RuntimeException(
                String.format("Instrucción no reconocida en línea %d, columna %d",
                        currentToken.getLine(), currentToken.getColumn())
        );
    }

    private VarDeclarationNode parseVarDeclaration() {
        String type = currentToken.getValue();
        expect(TokenType.KEYWORD);

        String name = currentToken.getValue();
        expect(TokenType.IDENTIFIER);

        expect(TokenType.ASSIGN);

        ASTNode value = parseExpression();

        expect(TokenType.SEMICOLON);

        return new VarDeclarationNode(type, name, value);
    }

    private PrintNode parsePrint() {
        expect(TokenType.KEYWORD); // 'print'

        ASTNode expression = parseExpression();

        expect(TokenType.SEMICOLON);

        return new PrintNode(expression);
    }

    private ASTNode parseExpression() {
        ASTNode node = parseTerm();

        while (currentToken.getType() == TokenType.PLUS ||
                currentToken.getType() == TokenType.MINUS) {
            String operator = currentToken.getValue();
            advance();
            ASTNode right = parseTerm();
            node = new BinaryOpNode(node, operator, right);
        }

        return node;
    }

    private ASTNode parseTerm() {
        ASTNode node = parseFactor();

        while (currentToken.getType() == TokenType.MULTIPLY ||
                currentToken.getType() == TokenType.DIVIDE) {
            String operator = currentToken.getValue();
            advance();
            ASTNode right = parseFactor();
            node = new BinaryOpNode(node, operator, right);
        }

        return node;
    }

    private ASTNode parseFactor() {
        Token token = currentToken;

        if (token.getType() == TokenType.NUMBER) {
            advance();
            return new NumberNode(Double.parseDouble(token.getValue()));
        } else if (token.getType() == TokenType.IDENTIFIER) {
            advance();
            return new IdentifierNode(token.getValue());
        } else if (token.getType() == TokenType.LPAREN) {
            advance();
            ASTNode node = parseExpression();
            expect(TokenType.RPAREN);
            return node;
        }

        throw new RuntimeException(
                String.format("Factor no válido en línea %d, columna %d",
                        token.getLine(), token.getColumn())
        );
    }
}