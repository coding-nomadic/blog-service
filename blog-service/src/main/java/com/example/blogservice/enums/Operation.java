package com.example.blogservice.enums;

public enum Operation {

    CATEGORY("category"), POST("post"), COMMENT("comment"), REGISTRATION("registration"),
    AUTHENTICATION("authentication");
    
    private final String text;

    Operation(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}
