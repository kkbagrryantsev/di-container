package ru.nsu.di_container.beans;

import ru.nsu.di_container.annotations.BeanScope;

import java.util.List;

public class Bean {
    private String id;
    private Class<?> clazz;
    private Object instance;
    private BeanScope scope;
    private List<ConstructorArg> constructorArgs;
    private boolean isLazy;

    public Bean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<?> getBeanClass() {
        return clazz;
    }

    public void setBeanClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public BeanScope getScope() {
        return scope;
    }

    public void setScope(BeanScope scope) {
        this.scope = scope;
    }

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }

    public List<ConstructorArg> getConstructorArgs() {
        return constructorArgs;
    }

    public void setConstructorArgs(List<ConstructorArg> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    @Override
    public String toString() {
        return String.format("Bean@%h of %s", this.hashCode(), this.clazz.getName());
    }
}
