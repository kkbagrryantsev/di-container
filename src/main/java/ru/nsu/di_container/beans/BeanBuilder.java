package ru.nsu.di_container.beans;

import org.jetbrains.annotations.NotNull;
import ru.nsu.di_container.annotations.BeanScope;

import java.util.ArrayList;
import java.util.List;

public class BeanBuilder {
    private final Bean bean;

    public BeanBuilder() {
        bean = new Bean();
    }

    public BeanBuilder addScope(BeanScope scope) {
        if (scope == null) {
            bean.setScope(BeanScope.SINGLETON);
        }
        bean.setScope(scope);
        return this;
    }

    public BeanBuilder addIsLazy(boolean isLazy) {
        bean.setLazy(isLazy);
        return this;
    }

    public BeanBuilder addBeanClass(@NotNull Class<?> clazz) {
        bean.setBeanClass(clazz);
        return this;
    }

    public BeanBuilder addId(String id) {
        bean.setId(id);
        return this;
    }

    public BeanBuilder addConstructorArgs(List<ConstructorArg> args) {
        if (args == null) {
            bean.setConstructorArgs(new ArrayList<>());
        }
        bean.setConstructorArgs(args);
        return this;
    }

    public Bean build() {
        return bean;
    }
}
