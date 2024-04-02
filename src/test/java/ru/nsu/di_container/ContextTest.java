package ru.nsu.di_container;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.di_container.snakes.Reptile;
import ru.nsu.di_container.snakes.ReptileTerrarium;
import ru.nsu.di_container.snakes.Terrarium;

import java.io.IOException;

public class ContextTest {
    @Test
    @DisplayName("Singleton injected and obtained globally")
    void singletonTest() {
    }

    @Test
    @DisplayName("Prototype within singleton")
    void prototypeWithinSingleton() {

    }


    public static void main(String[] args) throws IOException {
        Context context = new Context("src/test/resources/prop_config.json");
        Reptile snake = context.getBean("Rattlesnake");
        Reptile lizard = context.getBean("Iguana");
        System.out.println(snake.getLength());
        System.out.println(snake.getName());
        System.out.println(snake.getTerrarium().getGlassThickness());
        Terrarium terrarium = snake.getTerrarium();
        Terrarium terrarium2 = lizard.getTerrarium();
        Terrarium newTerrarium1 = context.getBean(ReptileTerrarium.class);
        System.out.println(terrarium == newTerrarium1);
        System.out.println(terrarium == terrarium2);
        System.out.println(newTerrarium1 == terrarium2);
    }
}
