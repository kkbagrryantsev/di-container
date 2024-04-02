package ru.nsu.di_container.classes;

public class SingletonWithPrototype implements SingletonWithPrototypeInterface {
    private PrototypeInterface obj;
    @Override
    public void someAction(){
        obj.doAction();
    }
    @Override
    public PrototypeInterface getObj(){
        return this.obj;
    }
}
