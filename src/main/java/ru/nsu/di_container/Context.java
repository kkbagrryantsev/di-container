package ru.nsu.di_container;

import ru.nsu.di_container.beans.BeanStore;

public class Context {
    private final BeanStore beanStore;

    public Context() {
        beanStore = new BeanStore();
    }

    public <T> T getBean(Class<T> clazz) {
        throw new UnsupportedOperationException();
    }
}
