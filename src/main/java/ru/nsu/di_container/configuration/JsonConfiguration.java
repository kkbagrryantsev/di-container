package ru.nsu.di_container.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import ru.nsu.di_container.annotations.BeanScope;
import ru.nsu.di_container.beans.Bean;
import ru.nsu.di_container.beans.BeanBuilder;
import ru.nsu.di_container.beans.ConstructorArg;
import ru.nsu.di_container.beans.InjectableConstructorArg;
import ru.nsu.di_container.beans.NamedConstructorArg;
import ru.nsu.di_container.beans.PlainConstructorArg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonConfiguration implements Configuration {
    private JsonConfiguration() {
    }

    public static List<Bean> readConfiguration(@NotNull String configPath) {
        try {
            File configFile = new File(configPath);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<Map<String, Object>> config = mapper.readValue(configFile, new TypeReference<>() {
            });

            List<Bean> beans = new ArrayList<>();
            //FIXME
            for (Map<String, Object> beanConfig : config) {
                BeanBuilder beanBuilder = new BeanBuilder();

                String beanId = (String) beanConfig.get("id");
                beanBuilder.addId(beanId);

                String beanScope = (String) beanConfig.get("scope");
                switch (beanScope) {
                    case "Singleton":
                        beanBuilder.addScope(BeanScope.SINGLETON);
                        break;
                    case "Prototype":
                        beanBuilder.addScope(BeanScope.PROTOTYPE);
                        break;
                    case "Thread":
                        beanBuilder.addScope(BeanScope.THREAD);
                    default:
                        beanBuilder.addScope(BeanScope.SINGLETON);
                }

                String className = (String) beanConfig.get("className");
                Class<?> beanClass = Class.forName(className);
                beanBuilder.addBeanClass(beanClass);

                //noinspection unchecked
                List<Map<String, Object>> serializedConstructorArgs = (List<Map<String, Object>>) beanConfig.get("constructorArgs");
                List<ConstructorArg> constructorArgs = new ArrayList<>();
                for (Map<String, Object> serializedConstructorArg: serializedConstructorArgs) {
                    String constructorArgClassName = (String) serializedConstructorArg.get("className");
                    if (constructorArgClassName == null) {
                        throw new IllegalArgumentException("Constructor arg doesn't have a classname");
                    }
                    Class<?> constructorArgClass = Class.forName(constructorArgClassName);

                    Object value = serializedConstructorArg.get("value");
                    if (value != null) {
                        ConstructorArg arg = new PlainConstructorArg(constructorArgClass, value);
                        constructorArgs.add(arg);
                        continue;
                    }

                    String id = (String) serializedConstructorArg.get("id");
                    if (id != null) {
                        ConstructorArg arg = new NamedConstructorArg(id);
                        constructorArgs.add(arg);
                        continue;
                    }

                    ConstructorArg arg = new InjectableConstructorArg(constructorArgClass);
                    constructorArgs.add(arg);
                }
                beanBuilder.addConstructorArgs(constructorArgs);
                Bean bean = beanBuilder.build();
                beans.add(bean);
            }
            return beans;
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            throw new IllegalStateException("Failed to create a container");
        }
    }
}
