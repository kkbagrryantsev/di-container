package ru.nsu.di_container.classes;

import javax.inject.Inject;

public class ClassWithInject implements ClassWithInjectInterface{
    @Inject
    private SomeInterface someClass;
    @Override
    public SomeInterface getDependency() {
        return someClass;
    }
}
