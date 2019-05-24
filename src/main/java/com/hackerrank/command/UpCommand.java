package com.hackerrank.command;

import com.hackerrank.constant.CommandTypes;
import com.hackerrank.model.Directory;
import com.hackerrank.model.FileSystem;

public class UpCommand
    implements Command {
    
    public String executeInternal(FileSystem fileSystem) {
        Directory current = fileSystem.getCurrent();
        
        if (current.getParent() == null) {
            return "Cannot move up from root directory";
        }
        
        return "";
    }
    
    public String getName() {
        return CommandTypes.UP.getName();
    }
    
    @Override
    public String getArgument() {
        return "";
    }
    
}
