package ru.nsu.di_container.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.di_container.beans.Bean;

import java.io.IOException;
import java.util.List;

public class JsonConfigurationTest {
    @Test
    @DisplayName("Basic test")
    void readSimpleConfig() {
        List<Bean> beans;
        try {
            beans = JsonConfiguration.readConfiguration("src/test/resources/prop_config.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(beans);
        beans.forEach(bean -> bean.getFieldValues().values().forEach(System.out::println));
    }
}
