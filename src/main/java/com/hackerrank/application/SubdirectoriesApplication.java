package com.hackerrank.application;

import java.util.ArrayList;
import java.util.List;

import com.hackerrank.command.Command;
import com.hackerrank.executor.CommandExcecutor;
import com.hackerrank.factory.CommandFactory;
import com.hackerrank.model.Directory;
import com.hackerrank.model.FileSystem;
import com.hackerrank.utils.FileUtils;

public class SubdirectoriesApplication {
    
    private FileSystem fileSystem;
    private CommandFactory commandFactory;
    private CommandExcecutor commandExcecutor;
    
    public SubdirectoriesApplication() {
        Directory root = new Directory("root", null);
        this.fileSystem = new FileSystem(root);
        this.commandFactory = new CommandFactory();
        this.commandExcecutor = new CommandExcecutor();
    }
    
    public void start(String inputFileName, String outputFileName) {
        List<String> inputLines = FileUtils.readLines(inputFileName);
        
        List<Command> comands = this.commandFactory.buildCommands(inputLines);
        
        List<String> output = new ArrayList<>();
        for (Command command : comands) {
            output.add(commandExcecutor.execute(command, fileSystem));
        }
        
        FileUtils.writeFile(outputFileName, output);
    }
    
}
