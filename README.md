# Java Interview Coding Challenge: Subdirectories

### Solution 1: Hackerrank challenge
Estimated time: 2 hours 30 minutes

### Solution 2: Extended exercise
Estimated time: 3 hours 50 minutes

**Problem:** Your program will simulate the creation of subdirectories (folders) on one of the disks of a computer. The input file to your program, prog5.dat, will contain a sequence of commands that a user might enter from a command line, and the output file prog5.out will contain the operating system’s responses to these commands.
    
Below is an example of an ```input``` file, and on the right is the listing of the corresponding ```output``` file.

```
dir  
mkdir sub6 
mkdir sub3 
mkdir sub4 
dir 
mkdir sub4 
cd sub3 
cd sub3 
mkdir sub3 
mkdir sub6 
mkdir sub4 
dir 
cd sub6 
mkdir sub666 
dir 
up 
up 
dir 
up 
```

Output: 
```
Problem 5 by team X 
Command: dir 
Directory of root: 
No subdirectories 
Command: mkdir sub6 
Command: mkdir sub3 
Command: mkdir sub4 
Command: dir 
Directory of root: 
sub3 sub4 sub6 
Command: mkdir sub4 
Subdirectory already exists 
Command: cd sub3 
Command: cd sub3 
Subdirectory does not exist 
Command: mkdir sub3 
Command: mkdir sub6 
Command: mkdir sub4 
Command: dir 
Directory of root\sub3: 
sub3 sub4 sub6 
Command: cd sub6 
Command: mkdir sub666 
Command: dir 
Directory of root\sub3\sub6: 
sub666 
Command: up 
Command: up 
Command: dir 
Directory of root: 
sub3 sub4 sub6 
Command: up 
Cannot move up from root directory 
End of problem 5 by team X
```
