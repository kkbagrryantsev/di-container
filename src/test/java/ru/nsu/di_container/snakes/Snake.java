package ru.nsu.di_container.snakes;

import javax.inject.Inject;

public class Snake {
    @Inject
    private Terrarium terrarium;
    private String snakeName;
    private Integer length;
}
