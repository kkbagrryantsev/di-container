package ru.nsu.di_container.beans;

public record PlainConstructorArg(Class<?> clazz, Object value) implements ConstructorArg {
}
