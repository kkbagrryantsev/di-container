package ru.nsu.di_container.beans;

import java.util.HashMap;

public class BeanStore {
    private final HashMap<String, Bean> namedBeans;
    private final HashMap<Class<?>, Bean> beans;

    public BeanStore() {
        namedBeans = new HashMap<>();
        beans = new HashMap<>();
    }

    public Bean getBean(String beanName) {
        return namedBeans.get(beanName);
    }

    public Bean getBean(Class<?> beanClass) {
        return beans.get(beanClass);
    }

    public void putBean(String beanName, Bean bean) {
        namedBeans.put(beanName, bean);
    }

    public void putBean(Class<?> beanClass, Bean bean) {
        beans.put(beanClass, bean);
    }
}
