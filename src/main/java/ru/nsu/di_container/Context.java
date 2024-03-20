package ru.nsu.di_container;

import ru.nsu.di_container.beans.BeanStore;
import ru.nsu.di_container.scanner.Scanner;

public class Context {
    private final BeanStore beanStore;
    private final Scanner scanner;

    public Context(String configPath) {
        this.scanner = new Scanner("ru.nsu.di_container");
        beanStore = new BeanStore();

    }

    public <T> T getBean(Class<T> clazz) {
        throw new UnsupportedOperationException();
    }
}
