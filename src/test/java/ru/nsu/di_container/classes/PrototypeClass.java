package ru.nsu.di_container.classes;

import ru.nsu.di_container.annotations.Lookup;

public class PrototypeClass implements PrototypeInterface{
    @Lookup
    public void doAction(){

    }
}
