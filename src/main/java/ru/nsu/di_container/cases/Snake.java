package ru.nsu.di_container.cases;

import ru.nsu.di_container.annotations.BeanScope;
import ru.nsu.di_container.annotations.Component;
import ru.nsu.di_container.annotations.Scope;

@Component
@Scope(BeanScope.SINGLETON)
public class Snake {
    private String name;
    private Integer length;

    public Snake(String name, Integer length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public static <T> T printData(T data) {
        System.out.println(data);
        return data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}

