package com.compilador.lexer;

public enum TokenType {
    NUMBER,
    IDENTIFIER, //nombre de variables
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    ASSIGN,
    SEMICOLON,
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    KEYWORD,  //tipo de dato, bucle
    EOF
}
