package ru.nsu.di_container.threads;

import ru.nsu.di_container.Context;

public class ThreadExample implements Runnable {
    private Context context;
    public SharedObject sharedObject;
    public ThreadExample(Context context) {
        this.context = context;
    }
    @Override
    public void run() {
        this.sharedObject = context.getBean("sharedObject");
    }
    public SharedObject getSharedObject(){
        return this.sharedObject;
    }
}
