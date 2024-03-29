package ru.nsu.di_container.snakes;

import javax.inject.Inject;

public class Snake {
    @Inject
    private Terrarium terrarium;
    private String snakeName;
    private Integer length;
    public Terrarium getTerrarium(){
        return terrarium;
    }
    public String getSnakeName() {
        return snakeName;
    }
    public Integer getLength(){
        return length;
    }
}
