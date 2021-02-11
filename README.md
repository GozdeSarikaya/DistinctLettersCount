# Distinct Letters Count

## Description
Count the minimum number of letters that must be deleted from a word to create a word in which no two letters occur the same number of times.



## Constraints

1. The minimum character number of the input line is 1.

2. The maximum character number of the input line is 300000.

3. The string value consists of only lowercase letters (a-z).



## Program Specification


This java program can be run on the command line in order to solve this problem. The program will take one command line argument, which contains the path to a text file. This text file can contain several lines, each line describing one test case for the problem.


Each line contains a word S consisting of N lowercase letters. The minimum number of letters will be returned that must be deleted to obtain a word in which every letter occurs a unique number of times. The occurences of letters that appear at least one in result are the only considered ones.


In case of a constraint violation, the program indicates this fact to the user, for example by throwing an exception with a descriptive message, allowing the user to address this problem.


## Project Hierarchy

The following files and folder are part of the project:

- README.md : current file
- pom.xml : Maven project main file
- src/main/java :  Source files of the code.
- src/main/resources :  Sample input json file
- src/test/java :  Source files for the JUNIT5 unit tests
- src/test/resources :  input json files used by the unit tests

### Prerequisites

You will need maven version 3.6.3+ and jdk11 to be able to compile and run this application.

### Installing

After cloning the project, open a commandline at the main folder that contains project pom.xml file and build the application by using following commands:

```
mvn clean
mvn compile
mvn package
```

### Logger Initialization

For logger type, logback implementation is used and set "DEBUG" log level by default.
If one wants to change the log level, one can find _"src/main/resources/logback.xml"_ file and change the following part as _"debug, trace or info"_:

```
    <root level="debug">
        <appender-ref ref="DistinctLetterCount" />
    </root>
```

## Executing the application

The execution of the application requires an input file that can be passed as argument of the execution.

```
java -jar target/DistinctLetterCount-1.0-SNAPSHOT.jar "[<absolute_path_to_input_file>] [<algorithm_type>]"
```

Examples of valid commands:

```
java -jar target/DistinctLetterCount-1.0-SNAPSHOT.jar C:\Workspace\DistinctLetterCount\logfile.txt
java -jar target/DistinctLetterCount-1.0-SNAPSHOT.jar C:\Workspace\DistinctLetterCount\logfile.txt 1
java -jar target/DistinctLetterCount-1.0-SNAPSHOT.jar C:\Workspace\DistinctLetterCount\logfile.txt 2
```

Note:
The input file must exist



### Sample Input



A sample input file looks like this:



```

aaaabbbb

ccaaffddecee

eeee

example

```



### Sample Output



The solution for each test case will be printed out on a single line. On this line you can print the line number and the solution value. If there is no need to delete any character then print 0.



The sample output for the sample input file above look like this:



```

1- 1

2- 6

3- 0

4- 4

```

