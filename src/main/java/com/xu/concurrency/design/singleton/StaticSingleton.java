package com.xu.concurrency.design.singleton;


public class StaticSingleton {

    private StaticSingleton() {

    }

    private static class SingletonFactory {
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonFactory.instance;
    }
}
