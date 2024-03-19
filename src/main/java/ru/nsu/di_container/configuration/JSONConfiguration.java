package ru.nsu.di_container.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSONConfiguration {
    public JSONConfiguration(@NotNull String configPath) {
        try {
            File configFile = new File(configPath);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<Map<String, Object>> config = mapper.readValue(configFile, new TypeReference<>() {
            });

            for (Map<String, Object> bean: config) {
                String id = (String) bean.get("id");
                String scope = (String) bean.get("scope");
                String className = (String) bean.get("className");
                Class<?> clazz = Class.forName(className);
                if (bean.get("constructorArgs") != null) {

                }
                List<Map<String, Object>> constructorArgs = (List<Map<String, Object>>) bean.get("constructorArgs");
            }
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
