package ru.nsu.di_container.beans;

import ru.nsu.di_container.Context;

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

    public Object createAndSetBeanValue(Bean bean) {
        Object value = createProxy(bean);
        setBeanValue(bean, value);
        return bean.getValue();
    }

    public Object createImplementationClassInstance(Bean bean) {
        Class<?> clazz = bean.getImplementationClass();
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, Object> entry : bean.getFieldValues().entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof Bean) {
                    value = createAndSetBeanValue((Bean) value);
                }

                Field field = clazz.getDeclaredField(fieldName);

                boolean canAccess = field.canAccess(instance);
                try {
                    field.setAccessible(true);
                    field.set(instance, value);
                } finally {
                    field.setAccessible(canAccess);
                }
            }
            return clazz.cast(instance);
        } catch (InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException
                 | InvocationTargetException
                 | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Object createProxy(Bean bean) {
        Class<?> interfaceClass = bean.getInterfaceClass();
        return interfaceClass.cast(Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    final Supplier<Object> createBeanValueInstance = () -> createImplementationClassInstance(bean);
                    Object instance = null;

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (instance == null) {
                            instance = createBeanValueInstance.get();
                        }
                        return method.invoke(instance, args);
                    }
                }
        ));
    }

    private void setBeanValue(Bean bean, Object value) {
        switch (bean.getScope()) {
            case SINGLETON -> bean.setValue(value);
            case THREAD -> {
                //noinspection unchecked
                ThreadLocal<Object> threadLocalBeanValue = (ThreadLocal<Object>) bean.getValue();
                threadLocalBeanValue.set(value);
            }
            case PROTOTYPE -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + bean.getScope());
        }
    }
}
