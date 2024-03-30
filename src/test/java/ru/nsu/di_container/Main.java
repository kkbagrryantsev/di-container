package ru.nsu.di_container;

import ru.nsu.di_container.snakes.Reptile;
import ru.nsu.di_container.snakes.ReptileTerrarium;
import ru.nsu.di_container.snakes.Terrarium;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Context context = new Context("src/test/resources/prop_config.json");
        Reptile snake = context.getBean("Rattlesnake");
        System.out.println(snake.getLength());
        System.out.println(snake.getSnakeName());
        System.out.println(snake.getTerrarium().getGlassThickness());
        Terrarium terrarium = snake.getTerrarium();
        Terrarium newTerrarium1 = context.getBean(ReptileTerrarium.class);
        System.out.println(terrarium == newTerrarium1);
    }
}
