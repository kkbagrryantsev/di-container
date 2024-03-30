package ru.nsu.di_container.snakes;

public interface Reptile {
    Terrarium getTerrarium();

    String getSnakeName();

    Integer getLength();

    void printString(String suffix);
}
