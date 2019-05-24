package com.hackerrank.model;

public class FileSystem {
    
    private Directory root;
    private Directory current;
    
    public FileSystem(Directory root) {
        this.root = root;
        this.current = root;
    }
    
    public Directory getCurrent() {
        return current;
    }
    
    public Directory getRoot() {
        return root;
    }
    
    public void setCurrent(Directory dir) {
        this.current = dir;
    }
}
