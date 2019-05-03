#!/bin/bash
cd ../..
java -cp ./bin:./lib/junit-4.13-beta-2.jar:./lib/hamcrest-core-1.3.jar test.Test
cd scripts/windows
