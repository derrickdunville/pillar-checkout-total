# Pillar Technology - Checkout Order Total Kata - Solution 

[Pillar Technology](https://pillartechnology.com/) - Information about the company can be found on their web-site. 

## Description

This project is the solution for an interview coding challenge for Pillar Technologies written in Java. The project requirements can be found here [Checkout Order Total Kata](https://github.com/PillarTechnology/kata-checkout-order-total).

## Environment

This program was build using Java 8 SE and also utilizes Ant, their versions are listed below. 

`jdk1.8.0_211`

`apache-ant-1.10.5`

It has been tested and runs on both Windows 10 and Ubuntu 14.04 LTS.

## Running

This project can be built, tested and ran on either Windows or Unix using the methods described below. 

### Ant

Ant can be used to build, test, and run the program on both Windows and Unix. From the project root directory the following ant tasks can be used, and they do not necessarily need to be ran in order. The test and run commands will automatically perform the build task. 

#### Build
To build/compile the project

```
$ ant build
```

#### Test

To run the test suite

```
$ ant test
```

#### Run

To run the program

```
$ ant run
```

### Javac/Java 

This project includes batch and shell scripts to build, test, and run the program on Windows or Unix. These scripts simply make use of `javac` and `java` cli commands to compile and run the program.

#### Windows

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

#### Unix
 First you will want to cd to the scripts/unix directory from the project root directory. 

```
$ cd scripts/unix
```

##### Build
To build/compile the project execute build.sh.

```
$ ./build.sh
```

##### Test

To run the test suite execute test.sh.

```
$ ./test.sh
```

##### Run

To run the program execute run.sh.

```
$ ./run.sh
```

## Author 

This solution was created by [Derrick Dunville](http://derrickdunville.com) 
