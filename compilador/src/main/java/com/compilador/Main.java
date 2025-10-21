package com.compilador;

import com.compilador.lexer.Lexer;
import com.compilador.lexer.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        compilarDesdeString();

        System.out.println("\n" + "=".repeat(60) + "\n");
    }

    //ejemplo de string
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
            Lexer lexer = new Lexer(codigo);
            List<Token> tokens = lexer.tokenize();

            System.out.println("\n--- Tokens generados ---");
            for (Token token : tokens) {
                System.out.println(token);
            }

            // - Parser (análisis sintáctico)
            // - Análisis semántico
            // - Generación de código

        } catch (Exception e) {
            System.err.println("Error durante la compilación:");
            System.err.println(e.getMessage());
        }
    }

    private static void compilarDesdeArchivo(String rutaArchivo) {
        try {
            String codigo = Files.readString(Paths.get(rutaArchivo));

            System.out.println("=== COMPILADOR ===");
            System.out.println("\nArchivo: " + rutaArchivo);
            System.out.println("\nCódigo fuente:");
            System.out.println(codigo);

            Lexer lexer = new Lexer(codigo);
            List<Token> tokens = lexer.tokenize();

            System.out.println("\n--- Tokens generados ---");
            for (Token token : tokens) {
                System.out.println(token);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error durante la compilación: " + e.getMessage());
        }
    }
}