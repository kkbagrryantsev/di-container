package ru.nsu.di_container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.di_container.classes.*;
import ru.nsu.di_container.snakes.Reptile;
import ru.nsu.di_container.snakes.ReptileTerrarium;
import ru.nsu.di_container.snakes.Snake;
import ru.nsu.di_container.snakes.Terrarium;

import java.io.IOException;

public class ContextTest {
    @Test
    @DisplayName("Singleton injected and obtained globally")
    void singletonTest() throws IOException {
        Context context = new Context("src/test/resources/prop_config.json");
        Reptile Rattlesnake1 = context.getBean("Rattlesnake");
        Reptile Rattlesnake2 = context.getBean("Rattlesnake");
        assert Rattlesnake1==Rattlesnake2;
    }
    @Test
    @DisplayName("Prototype injected")
    void prototypeTest() throws IOException {
        Context context = new Context("src/test/resources/prop_config.json");
        SomeInterface someClass1 = context.getBean(SomeClass.class);
        SomeInterface someClass2 = context.getBean(SomeClass.class);
        assert someClass1 != someClass2;
    }

    @Test
    @DisplayName("Prototype within singleton")
    void prototypeWithinSingletonTest() throws IOException {
        Context context = new Context("src/test/resources/prop_config.json");
        SingletonWithPrototypeInterface sngltn = context.getBean(SingletonWithPrototype.class);
        sngltn.someAction();
        Integer prot1hash = sngltn.getObj().hashCode();
        sngltn.someAction();
        Integer prot2hash = sngltn.getObj().hashCode();
        sngltn.otherAction();
        Integer prot3hash = sngltn.getObj().hashCode();

        assert !prot1hash.equals(prot2hash);
        assert prot2hash.equals(prot3hash);
    }

    @Test
    @DisplayName("Test inject annotation")
    void injectTest() throws IOException {
        Context context = new Context("src/test/resources/prop_config.json");
        context.getBean(ClassWithInject.class).getDependency();
        //assert context.getBean(ClassWithInject.class).getDependency()==null;
    }


    /*public static void main(String[] args) throws IOException {
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
    }*/
}
