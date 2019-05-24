package com.hackerrank.command;

import com.hackerrank.constant.CommandTypes;
import com.hackerrank.model.FileSystem;

public class DirCommand implements Command {
    
    private static final String DIRECTORY_FORMAT = "Directory of %s: \n";
    
    @Override
    public String executeInternal(FileSystem fileSystem) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(DIRECTORY_FORMAT, fileSystem.getCurrent().getAbsolutePath()));
    
        sb.append(fileSystem.getCurrent().printSubdirectories());
        return sb.toString();
    }
    
    @Override
    public String getName() {
        return CommandTypes.DIR.getName();
    }
    
    @Override
    public String getArgument() {
        return "";
    }
}
