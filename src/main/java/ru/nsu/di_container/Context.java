package ru.nsu.di_container;

import ru.nsu.di_container.annotations.BeanScope;
import ru.nsu.di_container.beans.Bean;
import ru.nsu.di_container.beans.BeanFactory;
import ru.nsu.di_container.beans.BeanStore;
import ru.nsu.di_container.configuration.JsonConfiguration;
import ru.nsu.di_container.scanner.Scanner;

import java.io.IOException;
import java.util.List;

public class Context {
    private final BeanStore beanStore;
    //private final Scanner scanner;
    private final BeanFactory beanFactory;

    public Context(String configPath) throws IOException {
        //this.scanner = new Scanner("ru.nsu.di_container");
        this.beanStore = new BeanStore();
        this.beanFactory = new BeanFactory(this.beanStore);
        List<Bean> beans = JsonConfiguration.readConfiguration(configPath);
        for (Bean bean: beans){
            if (bean.getScope() == BeanScope.THREAD){
                bean.setValue(new ThreadLocal<>());
            }
            if (bean.getName() != null){
                this.beanStore.putBean(bean.getName(), bean);
            }
            else {
                this.beanStore.putBean(bean.getValueClass(), bean);
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        Bean bean = this.beanStore.getBean(clazz);
        return this.getInstance(bean);
    }
    public <T> T getBean(String name) {
        Bean bean = this.beanStore.getBean(name);
        return this.getInstance(bean);
    }
    public <T> T getInstance(Bean bean){
        if (bean.getValue() != null){
            if (bean.getScope() == BeanScope.THREAD){
                if (((ThreadLocal<?>) bean.getValue()).get() == null){
                    return (T) this.beanFactory.createInstance(bean);
                }
                return (T) ((ThreadLocal<?>) bean.getValue()).get();
            }
            return (T) bean.getValue();
        }

        return (T) this.beanFactory.createInstance(bean);
    }
}
