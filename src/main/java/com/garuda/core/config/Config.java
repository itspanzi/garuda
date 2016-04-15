package com.garuda.core.config;

public class Config {

    public String hostname() {
        return "localhost";
    }

    public String port() {
        return "8000";
    }

    public String timeout() {
        return "100";
    }

    public String getClassFilter() {
        return "com.*";
    }
}
