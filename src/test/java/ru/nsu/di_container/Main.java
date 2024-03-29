package ru.nsu.di_container;

import ru.nsu.di_container.snakes.Snake;
import ru.nsu.di_container.snakes.Terrarium;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Context context = new Context("src/test/resources/prop_config.json");
        Snake snake = context.getBean("Rattlesnake");
        System.out.println(snake.getLength());
        System.out.println(snake.getSnakeName());
        System.out.println(snake.getTerrarium().glassThickness);
        Terrarium terrarium = snake.getTerrarium();
        Terrarium newTerrarium1 = context.getBean(Terrarium.class);
        System.out.println(terrarium==newTerrarium1);
    }
}
