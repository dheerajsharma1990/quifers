package com.quifers;

public class JettyTestRunner {
    public static void main(String[] args) throws Exception {
        JettyRunner.runJettyServer(Environment.LOCAL, 9111, 0);
    }
}
