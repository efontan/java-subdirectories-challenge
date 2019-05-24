package com.hackerrank.command;

import com.hackerrank.constant.CommandTypes;
import com.hackerrank.model.Directory;
import com.hackerrank.model.FileSystem;

public class CdCommand implements Command {
    
    private String argument;
    
    public CdCommand(String argument) {
        this.argument = argument;
    }
    
    public String executeInternal(FileSystem fileSystem) {
        Directory subdirectory = fileSystem.getCurrent().getSubdirectory(argument);
        
        if (subdirectory != null) {
            fileSystem.setCurrent(subdirectory);
            return "";
        }
        
        return "Subdirectory does not exist";
    }
    
    public String getName() {
        return CommandTypes.CD.getName();
    }
    
    @Override
    public String getArgument() {
        return argument;
    }
    
}
