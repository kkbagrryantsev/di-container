package ru.nsu.di_container.classes;

import ru.nsu.di_container.snakes.Reptile;

import javax.inject.Named;

public class ClassWithNamed implements ClassWithNamedInterface{
    @Named("Rattlesnake")
    private Reptile rattlesnake;
    @Override
    public void method() {
    }
    @Override
    public Reptile getRattlesnake(){
        return rattlesnake;
    }
}
