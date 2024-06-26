package ru.nsu.di_container.snakes;

import javax.inject.Inject;

public class Snake implements Reptile {
    @Inject
    private Terrarium terrarium;
    private String name;
    private Integer length;

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
