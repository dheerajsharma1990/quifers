package com.quifers.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlScriptParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlScriptParser.class);

    private final String SEPARATOR = ";";

    public List<String> parseSqlFile(File sqlFile) throws IOException {
        LOGGER.info("Parsing file: [{}]", sqlFile.getName());
        BufferedReader reader = new BufferedReader(new FileReader(sqlFile));
        List<String> sqls = new ArrayList<>();
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("--")) {
                continue;
            } else if (line.endsWith(SEPARATOR)) {
                sb.append(line);
                sqls.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(line).append(" ");
            }
        }
        return Collections.unmodifiableList(sqls);
    }
}
