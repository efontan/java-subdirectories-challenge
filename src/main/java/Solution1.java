import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Solution1 {
    
    public static void main(String args[]) throws Exception {
        DirectoriesApplication application = new DirectoriesApplication();
        
        application.start();
    }
}


class DirectoriesApplication {
    
    private static final String INITIAL_STATE_FILENAME = "fileSytemState.ser";
    
    private CommandFactory commandFactory;
    private CommandExecutor commandExecutor;
    private FileSystem fs;
    
    public DirectoriesApplication() {
        this.commandFactory = new CommandFactory();
        this.commandExecutor = new CommandExecutor();
        this.restoreOrCreateFileSystem();
    }
    
    private void restoreOrCreateFileSystem() {
        Optional<Object> maybeFile = FileUtils.readObjectFromDisk(INITIAL_STATE_FILENAME);
        
        if (maybeFile.isPresent()) {
            this.fs = (FileSystem) maybeFile.get();
        } else {
            Directory root = new Directory("root", null);
            this.fs = new FileSystem(root);
        }
    }
    
    public void start() {
        Scanner sc = new Scanner(System.in);
        String commandAsText = sc.nextLine();
        
        while (!commandAsText.equalsIgnoreCase("quit")) {
            try {
                Command command = this.commandFactory.buildCommand(commandAsText);
                String output = this.commandExecutor.execute(command, this.fs);
                System.out.print(output);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            commandAsText = sc.nextLine();
        }
        
        try {
            FileUtils.saveObjectToDisk(INITIAL_STATE_FILENAME, this.fs);
        } catch (Exception e) {
            // TODO log
            System.out.println(e.getMessage());
        }
    }
}


class CommandFactory {
    
    public Command buildCommand(String commandAsText) {
        String[] splittedCommand = commandAsText.split(" ");
        String commandName = splittedCommand[0];
        String argument = (splittedCommand.length > 1) ? splittedCommand[1] : null;
        
        switch (commandName) {
            case "pwd":
                return new PwdCommand();
            case "ls":
                return new LsCommand(argument);
            case "cd":
                return new CdCommand(argument);
            case "mkdir":
                return new MkdirCommand(argument);
            case "touch":
                return new TouchCommand(argument);
            default:
                throw new RuntimeException("Unrecognized command");
        }
    }
}

interface Command {
    String execute(FileSystem fs);
}

class PwdCommand implements Command {
    
    public PwdCommand() {
    }
    
    public String execute(FileSystem fs) {
        return fs.getCurrent().getFullPath();
    }
}


class LsCommand implements Command {
    private Optional<String> flag;
    
    public LsCommand(String flag) {
        this.flag = Optional.ofNullable(flag);
    }
    
    public String execute(FileSystem fs) {
        if (this.flag.isPresent()) {
            // print recursively
            if (!"-r".equals(this.flag.get())) {
                throw new RuntimeException("Invalid Command");
            }
            return fs.getCurrent().printChilds(true);
        } else {
            // print only childs names
            return fs.getCurrent().printChilds(false);
        }
    }
}


class CdCommand implements Command {
    private String[] dirnames;
    
    public CdCommand(String argument) {
        this.dirnames = argument.split("/");
    }
    
    public String execute(FileSystem fs) {
        if ("..".equals(this.dirnames[0])) {
            if (!"root".equals(fs.getCurrent().getName()) && fs.getCurrent().getParent() != null) {
                fs.setCurrent(fs.getCurrent().getParent());
            }
        } else {
            Directory dir = (Directory) fs.getCurrent().getSubDir(dirnames);
            if (dir != null) {
                fs.setCurrent(dir);
            } else {
                return "Directory not found";
            }
        }
        return "";
    }
}

class MkdirCommand implements Command {
    private String argument;
    
    public MkdirCommand(String argument) {
        if (argument.length() > 100) { throw new RuntimeException("Invalid File or Folder Name"); }
        this.argument = argument;
    }
    
    public String execute(FileSystem fs) {
        Boolean success = fs.getCurrent().createSubdir(argument);
        return (success) ? "" : "Directory already exists";
    }
}


class TouchCommand implements Command {
    private String argument;
    
    public TouchCommand(String argument) {
        if (argument.length() > 100) { throw new RuntimeException("Invalid File or Folder Name"); }
        this.argument = argument;
    }
    
    public String execute(FileSystem fs) {
        Boolean success = fs.getCurrent().createFile(argument);
        return (success) ? "" : "File already exists";
    }
}


class CommandExecutor {
    public String execute(Command command, FileSystem fs) {
        return command.execute(fs);
    }
}


class FileSystem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Directory root;
    private Directory current;
    
    public FileSystem(Directory root) {
        this.root = root;
        this.current = root;
    }
    
    public Directory getCurrent() {
        return current;
    }
    
    public Directory getRoot() {
        return root;
    }
    
    public void setCurrent(Directory dir) {
        this.current = dir;
    }
}


class Directory implements FileEntity, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private Directory parent;
    private Map<String, FileEntity> childs = new HashMap<>();
    
    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }
    
    public String printChilds(Boolean recursive) {
        StringBuilder sb = new StringBuilder();
        return this.printRecursive(recursive, sb);
    }
    
    private String printRecursive(Boolean recursive, StringBuilder sb) {
        List<Directory> childDirsToPrint = new ArrayList<>();
        if (recursive && !childs.isEmpty()) {
            sb.append(this.getFullPath() + "\n");
        }
        // Print this directory
        for (Map.Entry<String, FileEntity> child : childs.entrySet()) {
            sb.append(child.getKey() + "\n");
            
            if (recursive && child.getValue().getEntityType() == FileEntityType.Directory) {
                childDirsToPrint.add((Directory) child.getValue());
            }
        }
        
        for (Directory dir : childDirsToPrint) {
            dir.printRecursive(true, sb);
        }
        
        return sb.toString();
    }
    
    // TODO: (if I have time) createSubdir and createFile can be the same method using a "type" extra parameter
    public Boolean createSubdir(String dirName) {
        if (this.containsChild(dirName)) { return false; }
        
        Directory newDir = new Directory(dirName, this);
        return childs.put(dirName, newDir) == null;
    }
    
    public Boolean createFile(String fileName) {
        if (this.containsChild(fileName)) { return false; }
        
        File newDir = new File(fileName, this);
        return childs.put(fileName, newDir) == null;
    }
    
    public Boolean containsChild(String childName) {
        return childs.containsKey(childName);
    }
    
    public String getFullPath() {
        if (this.getParent() == null) { return "/" + this.name; }
        List<String> dirs = new ArrayList<>();
        Directory current = this;
        while (current != null) {
            dirs.add(current.getName());
            current = current.getParent();
        }
        
        Collections.reverse(dirs);
        return "/" + String.join("/", dirs);
    }
    
    public FileEntity getSubDir(String[] dirNames) {
        FileEntity current = this;
        
        for (String dirName : dirNames) {
            FileEntity child = ((Directory) current).getSubDir(dirName);
            
            if (child == null || child.getEntityType() == FileEntityType.File) {
                throw new RuntimeException("Invalid path");
            } else {
                current = child;
            }
        }
        return current;
    }
    
    public FileEntity getSubDir(String dirName) {
        return this.childs.getOrDefault(dirName, null);
    }
    
    @Override
    public Directory getParent() {
        return this.parent;
    }
    
    @Override
    public FileEntityType getEntityType() {
        return FileEntityType.Directory;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}

class File implements FileEntity, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private Directory parent;
    
    public File(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }
    
    @Override
    public Directory getParent() {
        return this.parent;
    }
    
    @Override
    public FileEntityType getEntityType() {
        return FileEntityType.File;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}

enum FileEntityType {
    Directory, File
}

interface FileEntity {
    String getName();
    
    Directory getParent();
    
    FileEntityType getEntityType();
}

class FileUtils {
    
    private static final String homeUserDir = System.getProperty("user.home");
    
    public static boolean saveObjectToDisk(String fileName, Serializable object) {
        try(FileOutputStream fileOut = new FileOutputStream(getFilePath(fileName));
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(object);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public static Optional<Object> readObjectFromDisk(String fileName) {
        Optional<Object> maybeFile = Optional.empty();
        java.io.File f = new java.io.File(getFilePath(fileName));
        
        if (f.isFile() && f.canRead()) {
            try(FileInputStream fileIn = new FileInputStream(f);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
                maybeFile = Optional.of(in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return maybeFile;
    }
    
    private static String getFilePath(String fileName) {
        return homeUserDir + java.io.File.separator + fileName;
    }
    
}

