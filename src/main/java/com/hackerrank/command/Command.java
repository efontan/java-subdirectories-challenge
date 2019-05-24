package com.hackerrank.command;

import com.hackerrank.model.FileSystem;

public interface Command {
    
    String COMMAND_PREFIX = "Command: ";
    
    default String execute(FileSystem fileSystem){
        return getOutputName() + "\n" + this.executeInternal(fileSystem);
    }
    
    String executeInternal(FileSystem fileSystem);
    String getName();
    String getArgument();
    
    default String getOutputName() {
        return COMMAND_PREFIX + getName() + "    " + getArgument();
    }
}
