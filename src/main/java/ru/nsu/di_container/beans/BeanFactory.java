package ru.nsu.di_container.beans;

import org.jetbrains.annotations.NotNull;
import ru.nsu.di_container.Context;
import ru.nsu.di_container.annotations.BeanScope;
import ru.nsu.di_container.annotations.Lookup;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.function.Supplier;

public class BeanFactory {
    Context context;

    public BeanFactory(Context context) {
        this.context = context;
    }

    public Object createBeanValue(Bean bean) {
        return createProxy(bean);
    }

    public Object createImplementationClassInstance(@NotNull Bean bean) {
        Class<?> implementationClass = bean.getImplementationClass();
        try {
            Object instance = implementationClass.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, Object> entry : bean.getFieldValues().entrySet()) {
                String fieldName = entry.getKey();
                Field field = implementationClass.getDeclaredField(fieldName);
                Object fieldValue = entry.getValue();

                if (fieldValue instanceof Bean fieldBeanValue) {
                    Object value;
                    if (fieldBeanValue.valueIsEmpty()) {
                        value = createBeanValue(fieldBeanValue);
                        if (fieldBeanValue.getScope() != BeanScope.PROTOTYPE) {
                            fieldBeanValue.setValue(value);
                        }
                    } else {
                        value = fieldBeanValue.getValue();
                    }
                    setField(instance, field, value);
                } else {
                    setField(instance, field, fieldValue);
                }


            }
            return implementationClass.cast(instance);
        } catch (InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException
                 | InvocationTargetException
                 | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private void setField(Object instance, @NotNull Field field, Object value) throws IllegalAccessException {
        boolean canAccess = field.canAccess(instance);
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } finally {
            field.setAccessible(canAccess);
        }
    }

    private Object createProxy(@NotNull Bean bean) {
        Class<?> interfaceClass = bean.getInterfaceClass();
        Supplier<Object> createInstance = () -> createImplementationClassInstance(bean);
        return interfaceClass.cast(Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    final Supplier<Object> createBeanValueInstance = createInstance;
                    Object instance = null;

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (instance == null) {
                            instance = createBeanValueInstance.get();
                        }
                        if (method.getAnnotation(Lookup.class) != null) {
                            instance = createBeanValueInstance.get();
                        }
                        return method.invoke(instance, args);
                    }
                }
        ));
    }
}
