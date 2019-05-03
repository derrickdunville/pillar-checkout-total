# Pillar Technology - Checkout Order Total Kata - Solution 

[Pillar Technology](https://pillartechnology.com/) - Information about the company can be found on their web-site. 

## Description
This project is the solution for an interview coding challenge for Pillar Technologies written in Java. The project requirements can be found here [Checkout Order Total Kata](https://github.com/PillarTechnology/kata-checkout-order-total).

## Environment

This program was build using Java 8 SE and also utilized Ant, their version are listed below. 

`jdk1.8.0_211`

`apache-ant-1.10.5`

It has been tested and run on both Windows 10 and Ubuntu 14.04 LTS.

## Running

### Windows

#### Javac/Java
This project includes batch files to build, test, and run the program on Windows. These batch files simply make use of `javac` and `java` to compile and run the program. First you will want to cd to the scripts/windows directory from the project root directory. 

```
$ cd scripts\windows
```

##### Build
To build/compile the project execute build.bat.

```
$ build
```

##### Test

To run the test suite execute test.bat.

```
$ test
```

##### Run

To run the program execute run.bat.

```
$ run
```

#### Ant

Ant can also be used to build, test, and run the program on both Windows and Unix. From the project root directory the following ant commands can be used, and they do not necessarily need to be ran in order. The test and run commands will automatically perform the build step. 

##### Build
To build/compile the project

```
$ ant build
```

##### Test

To run the test suite

```
$ ant test
```

##### Run

To run the program

```
$ ant run
```

