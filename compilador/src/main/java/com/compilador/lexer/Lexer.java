package com.compilador.lexer;

import java.util.*;

public class Lexer {
    private final String input;
    private int pos = 0;
    private int line = 1;
    private int column = 1;
    private char currentChar;

    // Palabras reservadas del lenguaje
    private static final Set<String> KEYWORDS = new HashSet<>(
            Arrays.asList("int", "float", "print", "if", "else", "while", "return")
    );

    public Lexer(String input) {
        this.input = input;
        this.currentChar = input.length() > 0 ? input.charAt(0) : '\0';
    }

   //avanza al siguiente caracter
    private void advance() {
        if (currentChar == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }

        pos++;
        currentChar = pos < input.length() ? input.charAt(pos) : '\0';
    }

    private void skipWhitespace() {
        while (currentChar != '\0' && Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private Token number() {
        StringBuilder result = new StringBuilder();
        int startColumn = column;

        while (currentChar != '\0' && (Character.isDigit(currentChar) || currentChar == '.')) {
            result.append(currentChar);
            advance();
        }

        return new Token(TokenType.NUMBER, result.toString(), line, startColumn);
    }

    private Token identifier() {
        StringBuilder result = new StringBuilder();
        int startColumn = column;

        while (currentChar != '\0' &&
                (Character.isLetterOrDigit(currentChar) || currentChar == '_')) {
            result.append(currentChar);
            advance();
        }

        String value = result.toString();
        TokenType type = KEYWORDS.contains(value) ? TokenType.KEYWORD : TokenType.IDENTIFIER;

        return new Token(type, value, line, startColumn);
    }


    public Token getNextToken() {
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }

            if (Character.isDigit(currentChar)) {
                return number();
            }

            if (Character.isLetter(currentChar) || currentChar == '_') {
                return identifier();
            }

            int startColumn = column;

            switch (currentChar) {
                case '+':
                    advance();
                    return new Token(TokenType.PLUS, "+", line, startColumn);
                case '-':
                    advance();
                    return new Token(TokenType.MINUS, "-", line, startColumn);
                case '*':
                    advance();
                    return new Token(TokenType.MULTIPLY, "*", line, startColumn);
                case '/':
                    advance();
                    return new Token(TokenType.DIVIDE, "/", line, startColumn);
                case '=':
                    advance();
                    return new Token(TokenType.ASSIGN, "=", line, startColumn);
                case ';':
                    advance();
                    return new Token(TokenType.SEMICOLON, ";", line, startColumn);
                case '(':
                    advance();
                    return new Token(TokenType.LPAREN, "(", line, startColumn);
                case ')':
                    advance();
                    return new Token(TokenType.RPAREN, ")", line, startColumn);
                case '{':
                    advance();
                    return new Token(TokenType.LBRACE, "{", line, startColumn);
                case '}':
                    advance();
                    return new Token(TokenType.RBRACE, "}", line, startColumn);
                default:
                    throw new RuntimeException(
                            String.format("Carácter inválido '%c' en línea %d, columna %d",
                                    currentChar, line, column)
                    );
            }
        }

        return new Token(TokenType.EOF, "", line, column);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Token token;

        do {
            token = getNextToken();
            tokens.add(token);
        } while (token.getType() != TokenType.EOF);

        return tokens;
    }
}
