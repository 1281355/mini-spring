package com.rain.zbs.web.handler;

import com.rain.zbs.web.mvc.Controller;
import com.rain.zbs.web.mvc.RequestMapping;
import com.rain.zbs.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 扫描类路径下的注解
 * @author huangyu
 * @version 1.0
 * @date 2019/9/12 18:04
 */
public class HandlerManager {
    public static List<MappingHandler> mappingHandlerList = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList){
        for (Class<?> cls : classList){
            if(cls.isAnnotationPresent(Controller.class)){
                parseHandlerFromController(cls);
            }
        }
    }

    private static void parseHandlerFromController(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }

            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
            List<String> paramNameList = new ArrayList<>();
            for(Parameter parameter : method.getParameters()){
                if(parameter.isAnnotationPresent(RequestParam.class)){
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }

            String[] params = paramNameList.toArray(new String[paramNameList.size()]);
            MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);
            HandlerManager.mappingHandlerList.add(mappingHandler);
        }
    }
}