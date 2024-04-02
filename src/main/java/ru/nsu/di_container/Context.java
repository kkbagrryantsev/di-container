package ru.nsu.di_container;

import ru.nsu.di_container.beans.Bean;
import ru.nsu.di_container.beans.BeanFactory;
import ru.nsu.di_container.beans.BeanStore;
import ru.nsu.di_container.configuration.JsonConfiguration;

import java.io.IOException;
import java.util.List;

public class Context {
    private final BeanStore beanStore;
    private final BeanFactory beanFactory;

    public Context(String configPath) throws IOException {
        beanStore = new BeanStore();
        beanFactory = new BeanFactory(this);
        List<Bean> beans = JsonConfiguration.readConfiguration(configPath);
        for (Bean bean : beans) {
            if (bean.getName() != null) {
                beanStore.putBean(bean.getName(), bean);
            } else {
                beanStore.putBean(bean.getImplementationClass(), bean);
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        Bean bean = beanStore.getBean(clazz);
        if (bean == null) {
            return null;
        }
        return getInstance(bean);
    }

    public <T> T getBean(String name) {
        Bean bean = beanStore.getBean(name);
        if (bean == null) {
            return null;
        }
        return getInstance(bean);
    }

    private <T> T getPrototypeBeanInstance(Bean bean) {
        //noinspection unchecked
        return (T) beanFactory.createBeanValue(bean);
    }

    private <T> T getSingletonBeanInstance(Bean bean) {
        if (bean.getValue() == null) {
            Object value = beanFactory.createBeanValue(bean);
            bean.setValue(value);
        }
        //noinspection unchecked
        return (T) bean.getValue();
    }

    private <T> T getThreadBeanInstance(Bean bean) {
        if (bean.getValue() == null) {
            Object object = beanFactory.createBeanValue(bean);
            bean.setValue(object);
        }
        //noinspection unchecked
        return (T) bean.getValue();
    }

    public <T> T getInstance(Bean bean) {
        switch (bean.getScope()) {
            case SINGLETON -> {
                return getSingletonBeanInstance(bean);
            }
            case PROTOTYPE -> {
                return getPrototypeBeanInstance(bean);
            }
            case THREAD -> {
                return getThreadBeanInstance(bean);
            }
            default -> throw new IllegalStateException("Unexpected value: " + bean.getScope());
        }
    }
}
