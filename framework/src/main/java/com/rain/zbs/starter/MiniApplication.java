package com.rain.zbs.starter;

import com.rain.zbs.beans.BeanFactory;
import com.rain.zbs.core.ClassScanner;
import com.rain.zbs.web.handler.HandlerManager;
import com.rain.zbs.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.io.IOException;
import java.util.List;

public class MiniApplication {
    public static void run(Class<?> cls,String[] args) {
        System.out.println("Hello mini-spring");
        TomcatServer tomcatServer = new TomcatServer(args);

        try {
            tomcatServer.startServer();

            List<Class<?>> classList = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.intiBean(classList);
            HandlerManager.resolveMappingHandler(classList);

            classList.forEach(it -> System.out.println(it.getName()));
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
