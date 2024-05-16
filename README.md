# Data-Structures & Algorithms

This repository contains some of the most commonly used data-structures and algorithms implemented in the Java programming language.

The types are *generic* meaning they can work with multiple data-types as long as they have the necessary characteristics, like being comparable.


# Compile it yourself (command-line)

The `sources` file stored in the root directory of the project, contains a list of all *compilation-units*.

You can generate this file yourself in **UNIX** by using the following command (assuming you are in the root directory of the project):

`find ./algorithms ./dataStructures -type f -iname "*.java" >sources`

After that, you can compile the program as follows:

`javac @sources -d ./bin`

This makes the compilation process easier by not explicitly stating all file paths.


# JAR File

This repository already includes the entire project's pre-compiled classes on a JAR archive file named `structures_and_algorithms.jar`.

If you want to create this file yourself, you can go to the directory you compiled the program and execute this command:

`jar -cf foo.jar ./algorithms ./dataStructures`

Then ensure all *.class* files are included:

`jar -tf foo.jar`


# Including these packages to your other projects

You need to add the path to the JAR file in the CLASSPATH *environment variable* (the place where java searches for classfiles).

Below is a simple solution to this:

- UNIX:
  + Compilation: `javac -cp ".:path_to_jar/structures_and_algorithms.jar" Main.java your_other_source_files.java`
  + Execution: `java -cp ".:path_to_jar/structures_and_algorithms.jar" Main`

- Windows:
  + Compilation: `javac -cp ".;path_to_jar/structures_and_algorithms.jar" Main.java your_other_source_files.java`
  + Execution: `java -cp ".;path_to_jar/structures_and_algorithms.jar" Main`

