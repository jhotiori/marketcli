package org.javay.marketcli.util;

import java.util.Scanner;
import java.util.function.Function;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public <T> T read(String promptMessage, String errorMessage, Function<String, T> parser) {
        while (true) {
            System.out.print(promptMessage);
            String input = scanner.nextLine();

            try {
                return parser.apply(input);
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }

    public String readString(String prompt) {
        return read(prompt, "Entrada inválida!", String::valueOf);
    }

    public int readInt(String prompt) {
        return read(prompt, "Digite um número inteiro válido!", Integer::parseInt);
    }

    public float readFloat(String prompt) {
        return read(prompt, "Digite um número float válido!", Float::parseFloat);
    }

    public double readDouble(String prompt) {
        return read(prompt, "Digite um número válido!", Double::parseDouble);
    }
}
