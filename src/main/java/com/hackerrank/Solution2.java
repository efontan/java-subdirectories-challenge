package com.hackerrank;

import com.hackerrank.application.SubdirectoriesApplication;

public class Solution2 {
    
    private static final String INPUT_FILE_NAME = "commands.bat";
    private static final String OUTPUT_FILE_NAME = "output.bat";
    
    public static void main(String[] args) {
    
        SubdirectoriesApplication application = new SubdirectoriesApplication();
    
        application.start(INPUT_FILE_NAME, OUTPUT_FILE_NAME);
    }
    
}
