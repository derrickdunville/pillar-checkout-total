#!/bin/bash
echo compiling project...
# Go to project root
cd ../..

# Clean bin
rm -rf bin

# Make directories
mkdir bin
mkdir bin/test

# Compile exception package
javac -d bin/ src/pillar/exception/*.java
# Compile item package
javac -cp ./bin -d bin/ src/pillar/item/*.java
# Compile pillar package
javac -cp ./bin -d bin/ src/pillar/*.java
# Compile test package
javac -cp ./bin:./lib/junit-4.13-beta-2.jar:./lib/hamcrest-core-1.3.jar -d bin/ test/test/*.java

cd scripts/unix/
