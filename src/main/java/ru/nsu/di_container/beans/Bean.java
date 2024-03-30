package ru.nsu.di_container.beans;

import ru.nsu.di_container.annotations.BeanScope;

import java.util.Map;

public class Bean {
    private String name;
    private Class<?> interfaceClass;
    private Class<?> implementationClass;
    private Object value;
    private BeanScope scope;
    private Map<String, Object> fieldValues;

    public Bean() {
    }

    @Override
    public String toString() {
        return String.format("Bean@%h of %s", this.hashCode(), this.implementationClass.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public BeanScope getScope() {
        return scope;
    }

    public void setScope(BeanScope scope) {
        this.scope = scope;
    }

    public Map<String, Object> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Map<String, Object> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public Class<?> getImplementationClass() {
        return implementationClass;
    }

    public void setImplementationClass(Class<?> implementationClass) {
        this.implementationClass = implementationClass;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
