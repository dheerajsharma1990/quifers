package com.quifers.servlet;

public class CommandNotFoundException extends Exception {

    public CommandNotFoundException(String command) {
        super(command);
    }
}
