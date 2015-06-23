package com.quifers.servlet;

public class CommandComparator {
    public static boolean isEqual(String value, String command) {
        return value.equalsIgnoreCase(command);
    }
}
