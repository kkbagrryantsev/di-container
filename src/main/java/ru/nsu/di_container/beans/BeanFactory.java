package ru.nsu.di_container.beans;

import ru.nsu.di_container.annotations.BeanScope;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class BeanFactory {
    private BeanStore beanStore;
    public BeanFactory(BeanStore beanStore){
        this.beanStore = beanStore;
    }
    public Object createInstance(Bean bean){
        Object instance = null;
        Class<?> clazz = bean.getValueClass();
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, Object> entry : bean.getFieldValues().entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                if (value instanceof Bean){
                    value = this.createInstance((Bean) value);
                }
                field.set(instance, value);
            }
        } catch (InstantiationException | IllegalAccessException  |
                 NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        if (bean.getScope() == BeanScope.SINGLETON){
            bean.setValue(instance);
        }
        if (bean.getScope() == BeanScope.THREAD){
            ((ThreadLocal) bean.getValue()).set(instance);
        }
        return clazz.cast(instance);
    }
}
