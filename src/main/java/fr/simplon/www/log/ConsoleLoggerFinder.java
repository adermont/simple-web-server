package fr.simplon.www.log;

import java.util.HashMap;
import java.util.Map;

public class ConsoleLoggerFinder extends System.LoggerFinder {

    private static final Map<String, ConsoleLogger> loggers = new HashMap<>();

    @Override
    public System.Logger getLogger(String name, Module module) {
        return loggers.computeIfAbsent(name, ConsoleLogger::new);
    }
}