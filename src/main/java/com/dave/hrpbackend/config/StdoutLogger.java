package com.dave.hrpbackend.config;

public class StdoutLogger extends com.p6spy.engine.spy.appender.StdoutLogger {
    public void logText(String text) {
        System.err.println(text);
    }
}