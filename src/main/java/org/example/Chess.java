package org.example;

public class Chess {

    String name;
    String side;
    int weight;
    boolean open = false;

    public Chess(String name, String side, int weight) {
        this.name = name;
        this.side = side;
        this.weight = weight;
    }

    public String toString() {

        if (!open)
            return "X";

        return name;
    }
}