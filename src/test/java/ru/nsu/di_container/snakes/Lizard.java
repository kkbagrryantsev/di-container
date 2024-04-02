package ru.nsu.di_container.snakes;

import javax.inject.Inject;

public class Lizard implements Reptile {
    private String name;
    private Integer length;
    @Inject
    private Terrarium terrarium;

    @Override
    public Terrarium getTerrarium() {
        return terrarium;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getLength() {
        return length;
    }

    @Override
    public void printString(String suffix) {
        System.out.printf("Hello, %s%n", suffix);
    }
}
