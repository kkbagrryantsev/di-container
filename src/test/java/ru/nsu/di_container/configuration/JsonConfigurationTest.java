package ru.nsu.di_container.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.di_container.Context;
import ru.nsu.di_container.beans.Bean;

import java.util.List;

public class JsonConfigurationTest {
    @Test
    @DisplayName("Basic test")
    void readSimpleConfig() {
        List<Bean> beans = JsonConfiguration.readConfiguration("src/test/resources/config.json");
        System.out.println(beans);
    }
}
