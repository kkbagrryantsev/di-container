package ru.nsu.di_container.snakes;

public interface Reptile {
    Terrarium getTerrarium();

    String getName();

    Integer getLength();

    void printString(String suffix);
}
