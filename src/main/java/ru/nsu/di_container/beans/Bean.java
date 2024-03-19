package ru.nsu.di_container.beans;

import ru.nsu.di_container.annotations.BeanScope;

import java.util.List;

public class Bean {
    private String id;
    private Class<?> clazz;
    private Object instance;
    private BeanScope scope;
    private List<?> constructorArgs;
    private boolean isLazy;

    public BeanScope getScope() {
        return scope;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getId() {
        return id;
    }
}
