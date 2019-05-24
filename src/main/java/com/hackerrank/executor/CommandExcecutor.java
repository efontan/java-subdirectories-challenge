package com.hackerrank.executor;

import com.hackerrank.model.FileSystem;
import com.hackerrank.command.Command;

public class CommandExcecutor {
    
    public String execute(Command command, FileSystem fs) {
        return command.execute(fs);
    }
}
