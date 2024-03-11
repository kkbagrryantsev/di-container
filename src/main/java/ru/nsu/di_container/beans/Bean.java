package ru.nsu.di_container.beans;

import ru.nsu.di_container.annotations.BeanScope;

public class Bean {
    private String name;
    private Class<?> clazz;
    private BeanScope scope;
}
