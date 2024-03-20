package ru.nsu.di_container;

public class Main {
    public static void main(String[] args) {
        String configPath = "/resources/config.json";
        Context context = new Context(configPath);
        System.out.println("Hello world!");
    }
}