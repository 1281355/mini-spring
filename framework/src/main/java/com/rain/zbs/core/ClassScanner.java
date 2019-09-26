package com.rain.zbs.core;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 扫描获取类路径
 * @author huangyu
 * @version 1.0
 * @date 2019/9/12 17:24
 */
public class ClassScanner {
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        String path = packageName.replace(".","/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()){

            URL resource = resources.nextElement();
            System.out.println("++++"+resource);
            if(resource.getProtocol().contains("jar")){
                System.out.println("----"+resource.getProtocol());
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classList.addAll(getClassesFromJar(jarFilePath, path));
            }else if (resource.getProtocol().contains("file")){
                String filePath = resource.getPath();
                List<Class<?>> classes = new ArrayList<>();
                getClassesFromDirectory(classes,filePath,packageName);
                classList.addAll(classes);
            }
        }
        return classList;
    }

    private static void getClassesFromDirectory( List<Class<?>> classes ,String filePath, String packageName) {
        File file = new File(filePath);
        List<File> fileList = Arrays.asList(file.listFiles());
        fileList.forEach(it->{
            if(it.isDirectory()){
                getClassesFromDirectory(classes,it.getPath(),packageName);
            }else{
                String tmp = it.getPath().replace("\\",".");
                String className = tmp.substring(tmp.indexOf(packageName),tmp.length()-6);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()){
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName(); // com/mooc/zbs/test/Test.class
            if (entryName.startsWith(path) && entryName.endsWith(".class")){
                String classFullName = entryName.replace("/",".").substring(0,entryName.length() - 6);
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}
