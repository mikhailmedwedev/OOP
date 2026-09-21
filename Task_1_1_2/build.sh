#!/usr/bin/env bash
javac -d build/classes src/main/java/org/example/*.java
javadoc -d blackjackdoc src/main/java/org/example/*.java
jar cfe build/blackjack.jar org.example.Main -C build/classes .
java -jar build/blackjack.jar