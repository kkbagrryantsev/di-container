package ru.nsu.di_container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.di_container.classes.*;
import ru.nsu.di_container.snakes.Reptile;
import ru.nsu.di_container.snakes.ReptileTerrarium;
import ru.nsu.di_container.snakes.Snake;
import ru.nsu.di_container.snakes.Terrarium;
import ru.nsu.di_container.threads.ThreadExample;

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
        ClassWithInjectInterface o = context.getBean(ClassWithInject.class);
        assert o.getDependency()!=null;
    }
    @Test
    @DisplayName("Test named annotation")
    void namedTest() throws IOException {
        Context context = new Context("src/test/resources/prop_config.json");
        ClassWithNamedInterface o = context.getBean(ClassWithNamed.class);
        assert o.getRattlesnake()!=null;
    }
    @Test
    @DisplayName("Test thread scope")
    void threadTest() throws IOException, InterruptedException {
        Context context = new Context("src/test/resources/threadscope_config.json");
        context.getBean("sharedObject");
        ThreadExample threadExample1 = new ThreadExample(context);
        ThreadExample threadExample2 = new ThreadExample(context);
        Thread t1 = new Thread(threadExample1);
        Thread t2 = new Thread(threadExample2);
        t1.start();
        t2.start();
        t2.join();
        t1.join();
        assert (threadExample1.getSharedObject() != threadExample2.getSharedObject());
        assert (threadExample1.getSharedObject() == threadExample1.getSharedObject());
    }
}
