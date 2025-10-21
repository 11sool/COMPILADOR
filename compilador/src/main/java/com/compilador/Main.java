package com.compilador;

import com.compilador.lexer.Lexer;
import com.compilador.lexer.Token;
import com.compilador.parser.Parser;
import com.compilador.parser.ProgramNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        compilarDesdeString();

        System.out.println("\n" + "=".repeat(60) + "\n");

    }

    private static void compilarDesdeString() {
        String codigo = """
            int x = 10 + 5;
            float y = 3.14 * 2;
            print x;
            """;

        System.out.println("=== COMPILADOR ===");
        System.out.println("\nCódigo fuente:");
        System.out.println(codigo);

        try {
            // Análisis Léxico
            System.out.println("\n--- FASE 1: ANÁLISIS LÉXICO ---");
            Lexer lexer = new Lexer(codigo);
            List<Token> tokens = lexer.tokenize();

            System.out.println("Tokens generados:");
            for (Token token : tokens) {
                System.out.println("  " + token);
            }

            // Análisis Sintáctico (Parser)
            System.out.println("\n--- FASE 2: ANÁLISIS SINTÁCTICO ---");
            Parser parser = new Parser(tokens);
            ProgramNode ast = parser.parse();

            System.out.println("Árbol de Sintaxis Abstracta (AST):");
            System.out.println(ast);

            //.
            System.out.println("\n✓ Compilación exitosa");

        } catch (Exception e) {
            System.err.println("\n✗ Error durante la compilación:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void compilarDesdeArchivo(String rutaArchivo) {
        try {
            String codigo = Files.readString(Paths.get(rutaArchivo));

            System.out.println("=== COMPILADOR ===");
            System.out.println("\nArchivo: " + rutaArchivo);
            System.out.println("\nCódigo fuente:");
            System.out.println(codigo);

            System.out.println("\n--- FASE 1: ANÁLISIS LÉXICO ---");
            Lexer lexer = new Lexer(codigo);
            List<Token> tokens = lexer.tokenize();

            System.out.println("Tokens generados:");
            for (Token token : tokens) {
                System.out.println("  " + token);
            }

            System.out.println("\n--- FASE 2: ANÁLISIS SINTÁCTICO ---");
            Parser parser = new Parser(tokens);
            ProgramNode ast = parser.parse();

            System.out.println("Árbol de Sintaxis Abstracta (AST):");
            System.out.println(ast);

            System.out.println("\n✓ Compilación exitosa");

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n✗ Error durante la compilación:");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}