package ru.nsu.di_container.threads;

import ru.nsu.di_container.Context;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Context context = new Context("src/test/resources/threadscope_config.json");
        SharedObject sharedObject = context.getBean("sharedObject");
        ThreadExample threadExample1 = new ThreadExample(context);
        ThreadExample threadExample2 = new ThreadExample(context);
        Thread t1 = new Thread(threadExample1);
        Thread t2 = new Thread(threadExample2);
        t1.start();
        t2.start();
        t2.join();
        t1.join();
        System.out.println(threadExample1.getSharedObject()==threadExample2.getSharedObject());
        System.out.println(threadExample1.getSharedObject()==threadExample1.getSharedObject());
    }
}
