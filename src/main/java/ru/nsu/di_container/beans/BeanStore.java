package ru.nsu.di_container.beans;

import java.util.HashMap;

public class BeanStore {
    private final HashMap<Class<?>, Bean> beans;

    public BeanStore() {
        beans = new HashMap<>();
    }

    public HashMap<Class<?>, Bean> getBeanStore() {
        return beans;
    }
}
