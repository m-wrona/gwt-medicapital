package com.medicapital.server.log;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

/**
 * Tracer manages Logger instances. It wraps also Logger so only the most import
 * methods are visible for tracing.
 * 
 * @author michal
 * 
 */
public class Tracer {

    private static Map<String, Tracer> instances = new HashMap<String, Tracer>();
    private Logger log;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        //TODO: fix loading
        try {
            URL url = Loader.getResource("/com/medicapital/server/log/log4j.properties");
            PropertyConfigurator.configure(url);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } catch (Error err) {
            err.printStackTrace(System.out);
        }
    }

    private Tracer(String name) {
        log = Logger.getLogger(name);
    }

    synchronized public static Tracer tracer(String name) {
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

    public void debug(String msg, Collection<?> data) {
        // if (data == null || log.getLevel().toInt() > Level.DEBUG.toInt()) {
        // return;
        // }
        log.debug(msg);
        log.debug(data);
    }

    public void debug(String message) {
        log.debug(message);
    }

    public void debug(String message, Throwable throwable) {
        log.debug(message, throwable);
    }

    public void debug(Throwable throwable) {
        log.debug(throwable);
    }

    public void error(String message) {
        log.error(message);
    }

    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }

    public void error(Throwable throwable) {
        log.error(throwable);
    }

    public void info(String message) {
        log.info(message);
    }

    public void info(String message, Throwable throwable) {
        log.info(message, throwable);
    }

    public void info(Throwable throwable) {
        log.info(throwable);
    }

}
