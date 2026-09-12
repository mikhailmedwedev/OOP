#!/usr/bin/env bash
javac -d build/classes src/main/java/org/example/*.java
javadoc -d heapdoc src/main/java/org/example/*.java
jar cfe build/heap.jar org.example.Main -C build/classes .
java -jar build/heap.jar