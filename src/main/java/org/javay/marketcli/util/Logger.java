package org.javay.marketcli.util;

public class Logger {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    private static void log(String color, String message) {
        System.out.println(color + message + RESET);
    }

    public static void info(String message) { log(BLUE, message); }
    public static void warn(String message) { log(YELLOW, message); }
    public static void success(String message) { log(GREEN, message); }
    public static void error(String message) { log(RED, message); }
}
