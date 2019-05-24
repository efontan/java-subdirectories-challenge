package com.hackerrank.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Directory {
    
    private String name;
    private Directory parent;
    private Map<String, Directory> childrens = new HashMap<>();
    
    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Directory getParent() {
        return this.parent;
    }
    
    public String printSubdirectories() {
        if (this.childrens.isEmpty()) return "No subdirectories";
        
        StringBuilder sb = new StringBuilder();
        
        Map<String, Directory> sortedChildrens = new TreeMap<>(childrens);
        for (Map.Entry<String, Directory> dir : sortedChildrens.entrySet()) {
            sb.append(dir.getKey() + this.getTabs(dir.getKey().length()));
        }
        return sb.toString();
    }
    
    private String getTabs(int wordLength) {
        int tabs = 8 - wordLength;
        return String.format("%" + tabs + "s", " ");
    }
    
    public boolean createSubdirectory(String dirName) {
        if (childrens.containsKey(dirName)) return false;
        
        Directory newDirectory = new Directory(dirName, this);
        return childrens.put(dirName, newDirectory) == null;
    }
    
    public Directory getSubdirectory(String dirName) {
        return this.childrens.getOrDefault(dirName, null);
    }
    
    public String getAbsolutePath() {
        Set<String> dirNames = new TreeSet<>();
        Directory current = this;
        while (current != null) {
            dirNames.add(current.getName());
            current = current.getParent();
        }
        
        return String.join("\\", dirNames);
    }
    
}
