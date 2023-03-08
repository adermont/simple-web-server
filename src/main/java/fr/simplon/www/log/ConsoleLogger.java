package fr.simplon.www.log;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ConsoleLogger implements System.Logger
{
    private final String name;

    public ConsoleLogger(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean isLoggable(Level level)
    {
        return level.getSeverity() >= Level.DEBUG.getSeverity();
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown)
    {
        if (isLoggable(level))
        {
            log(level, bundle, msg);
            thrown.printStackTrace();
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params)
    {
        if (isLoggable(level))
        {
            System.out.printf("[%s] %s - %s%n", level, getName(), MessageFormat.format(format, params));
        }
    }
}