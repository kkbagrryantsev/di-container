package ru.nsu.di_container.annotations;

import java.util.Arrays;

public enum BeanScope {
    SINGLETON,
    PROTOTYPE,
    THREAD;

    public static BeanScope fromString(String scopeName) {
        return switch (scopeName) {
            case "Singleton" -> SINGLETON;
            case "Prototype" -> PROTOTYPE;
            case "Thread" -> THREAD;
            default ->
                    throw new IllegalArgumentException("No matching scope. Available scope options are" + Arrays.stream(BeanScope.values()).map(Enum::toString));
        };
    }
}
