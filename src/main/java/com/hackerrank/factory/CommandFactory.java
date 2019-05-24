package com.hackerrank.factory;

import java.util.ArrayList;
import java.util.List;

import com.hackerrank.command.CdCommand;
import com.hackerrank.command.Command;
import com.hackerrank.command.DirCommand;
import com.hackerrank.command.MkdirCommand;
import com.hackerrank.command.UpCommand;

public class CommandFactory {
    
    
    public List<Command> buildCommands(List<String> inputLines) {
        List<Command> commands = new ArrayList<>();
        
        for (String inputLine : inputLines) {
            String[] splittedCommand = inputLine.split(" ");
            String commandName = splittedCommand[0];
            String argument = (splittedCommand.length > 1)? splittedCommand[1] : "";
    
            Command command = this.buildCommand(commandName, argument);
            if (command != null) commands.add(command);
        }
        
        return commands;
    }
    
    // TODO: use a Builder pattern to resolve this
    private Command buildCommand(String commandName, String argument) {
        switch (commandName) {
            case "dir":
                return new DirCommand();
            case "cd":
                return new CdCommand(argument);
            case "mkdir":
                return new MkdirCommand(argument);
            case "up":
                return new UpCommand();
            default:
                return null;
        }
        
    }
}
