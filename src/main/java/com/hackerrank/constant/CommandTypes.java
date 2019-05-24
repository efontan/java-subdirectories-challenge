package com.hackerrank.constant;

public enum CommandTypes {
    
    DIR("dir"), CD("cd"), MKDIR("mkdir"), UP("up");
    
    private String name; 
    CommandTypes(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
