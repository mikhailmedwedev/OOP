package org.example;

public abstract class Expression {

    @Override
    public abstract String toString();

    public void print() {
        System.out.println(this.toString());
    }

}
