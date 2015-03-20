package com.medicapital.client.log;

import java.util.HashMap;
import java.util.Map;
import com.allen_sauer.gwt.log.client.Log;

public class Tracer {

    private static Map<String, Tracer> instances = new HashMap<String, Tracer>();
    private String name;

    private Tracer(String name) {
        this.name = name;
    }

    public static Tracer tracer(String name) {
        Tracer tracer = instances.get(name);
        if (tracer == null) {
            tracer = new Tracer(name);
            instances.put(name, tracer);
        }
        return tracer;
    }

    public static Tracer tracer(Object o) {
        return tracer(o.getClass().getName());
    }

    public static Tracer tracer(Class<?> clazz) {
        return tracer(clazz.getName());
    }

    public void debug(String message) {
        Log.debug(name + " - " + message);
    }

    public void debug(String message, Throwable throwable) {
        Log.debug(name + " - " + message, throwable);
    }

    public void warn(String message) {
        Log.warn(name + " - " + message);
    }

    public void error(String message) {
        Log.error(name + " - " + message);
    }

    public void error(String message, Throwable throwable) {
        Log.error(name + " - " + message, throwable);
    }

    public void info(String message) {
        Log.info(name + " - " + message);
    }

}
