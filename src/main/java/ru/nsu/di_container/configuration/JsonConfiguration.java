package ru.nsu.di_container.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import ru.nsu.di_container.annotations.BeanScope;
import ru.nsu.di_container.beans.Bean;

import javax.inject.Inject;
import javax.inject.Named;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class JsonConfiguration implements Configuration {
    private JsonConfiguration() {
    }

    private static BeanScope deserializeScope(Object scopeName) {
        return BeanScope.fromString((String) scopeName);
    }

    private static Class<?> deserializeImplementationClass(Object implementationClassPath) {
        try {
            return Class.forName((String) implementationClassPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> deserializeInterfaceClass(Object interfaceClassPath) {
        try {
            return Class.forName((String) interfaceClassPath);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String deserializeName(Object name) {
        return (String) name;
    }

    private static Map<String, Object> deserializeFieldValues(Object serializedFieldValues) {
        //noinspection unchecked
        List<Map<String, Object>> fieldValuesList = (List<Map<String, Object>>) serializedFieldValues;
        Map<String, Object> fieldsValues = new HashMap<>();
        for (Map<String, Object> serializedFieldValue : fieldValuesList) {
            String fieldName = (String) serializedFieldValue.get("name");
            if (fieldName == null) {
                throw new IllegalArgumentException("Missing some field name from configuration");
            }

            // Config injection by value
            Object value = serializedFieldValue.get("value");
            if (value != null) {
                PrimitiveFieldValue primitiveFieldValue = new PrimitiveFieldValue(value);
                fieldsValues.put(fieldName, primitiveFieldValue);
                continue;
            }

            // Config injection by bean name
            String beanName = (String) serializedFieldValue.get("refName");
            if (beanName != null) {
                NamedFieldValue namedFieldValue = new NamedFieldValue(beanName);
                fieldsValues.put(fieldName, namedFieldValue);
                continue;
            }

            // Config injection by bean class
            try {
                Class<?> beanClass = Class.forName((String) serializedFieldValue.get("refClass"));
                InjectableFieldValue injectableFieldValue = new InjectableFieldValue(beanClass);
                fieldsValues.put(fieldName, injectableFieldValue);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return fieldsValues;
    }

    private static boolean validateFields(Map<String, Object> fields, Class<?> valueClass) {
        return fields
                .keySet()
                .stream()
                .allMatch(
                        key -> Arrays
                                .stream(valueClass.getDeclaredFields())
                                .map(Field::getName)
                                .anyMatch(
                                        field -> field.equals(key)));
    }

    private static Bean parseBean(Map<String, Object> serializedBean) {
        Bean bean = new Bean();

        bean.setScope(deserializeScope(serializedBean.get("scope")));

        bean.setName(deserializeName(serializedBean.get("name")));

        Class<?> implementationClass = deserializeImplementationClass(serializedBean.get("implementationClass"));
        bean.setImplementationClass(implementationClass);

        Class<?> interfaceClass = deserializeInterfaceClass(serializedBean.get("interfaceClass"));
        bean.setInterfaceClass(interfaceClass);

        Map<String, Object> fieldValues = deserializeFieldValues(serializedBean.get("fields"));
        if (!validateFields(fieldValues, implementationClass)) {
            throw new IllegalStateException(String.format("Provided fields does not exist on class %s", implementationClass.getName()));
        }
        bean.setFieldValues(fieldValues);
        return bean;
    }

    private static void connectBeans(List<Bean> beans) {
        for (Bean bean : beans) {
            Class<?> valueClass = bean.getImplementationClass();

            // Replace bean references with their instances
            Map<String, Object> fieldValues = bean.getFieldValues();
            for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
                Object fieldValue = entry.getValue();
                if (fieldValue instanceof NamedFieldValue) {
                    Bean namedBean = findNamedBean(((NamedFieldValue) fieldValue).beanName, beans);
                    entry.setValue(namedBean);
                    continue;
                }
                if (fieldValue instanceof InjectableFieldValue) {
                    Bean injectableBean = findInjectableBean(((InjectableFieldValue) fieldValue).beanValueClass, beans);
                    entry.setValue(injectableBean);
                    continue;
                }
                if (fieldValue instanceof PrimitiveFieldValue) {
                    entry.setValue(((PrimitiveFieldValue) fieldValue).value());
                }
            }

            // Add beans injected by annotation
            Field[] fields = valueClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Bean injectableBean = findInjectableBean(field.getType(), beans);
                    fieldValues.put(field.getName(), injectableBean);
                    continue;
                }
                if (field.isAnnotationPresent(Named.class)) {
                    Named annotation = field.getAnnotation(Named.class);
                    Bean namedBean = findNamedBean(annotation.value(), beans);
                    fieldValues.put(field.getName(), namedBean);
                }
            }
        }
    }

    private static Bean findNamedBean(String beanName, List<Bean> beans) {
        for (Bean bean : beans) {
            if (bean.getName().equals(beanName)) {
                return bean;
            }
        }
        throw new IllegalStateException(String.format("Bean named %s doesn't exist", beanName));
    }

    private static Bean findInjectableBean(Class<?> valueClass, List<Bean> beans) {
        for (Bean bean : beans) {
            if (valueClass.isAssignableFrom(bean.getImplementationClass())) {
                return bean;
            }
        }
        throw new IllegalStateException(String.format("Bean with value of class %s doesn't exist", valueClass.getName()));
    }

    public static List<Bean> readConfiguration(@NotNull String configPath) throws IOException {
        File configFile = new File(configPath);

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<Map<String, Object>> serializedBeans = mapper.readValue(configFile, new TypeReference<>() {
        });
        List<Bean> beans = serializedBeans.stream().map(JsonConfiguration::parseBean).toList();
        connectBeans(beans);
        return beans;
    }

    private record NamedFieldValue(String beanName) {
    }

    private record InjectableFieldValue(Class<?> beanValueClass) {
    }

    private record PrimitiveFieldValue(Object value) {
    }
}
