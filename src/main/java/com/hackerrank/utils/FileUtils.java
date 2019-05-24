package com.hackerrank.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {
    
    private static final String BASE_PATH = "src/main/resources/";
    
    public static List<String> readLines(String fileName) {
        String filePath = BASE_PATH + fileName;
        
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + filePath, e);
        }
    }
    
    public static void writeFile(String fileName, List<String> lines) {
        String filePath = BASE_PATH + fileName;
        
        try {
            Files.write(Paths.get(filePath), lines);
        } catch (IOException e) {
            throw new RuntimeException("Error writing file " + filePath + " with lines " + lines.toString(), e);
        }
    }
    
}
