package agent;

import java.lang.instrument.Instrumentation;

public class DynamicAgent {

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        // 此处的代码运行在被绑定的应用的JVM进程上，也就是在Application的进程中输出attach JVM success，所以相关的依赖和配置是被绑定应用数据
        System.out.println("attach JVM success:");
        if (instrumentation.isRetransformClassesSupported()) {
            transformClass("application.UserService", instrumentation);
        }
        for (Class<?> clazz : instrumentation.getInitiatedClasses(Thread.currentThread().getContextClassLoader())) {
            if (clazz.getName().startsWith("application")) {
                System.out.println("initiated class " + clazz.getName());
            }
        }
    }


    private static void transformClass(String className, Instrumentation instrumentation) {
        Class<?> targetCls;
        ClassLoader targetClassLoader;
        // see if we can get the class using forName
        try {
            targetCls = Class.forName(className);
            targetClassLoader = targetCls.getClassLoader();
            transform(targetCls, targetClassLoader, instrumentation);
            return;
        } catch (Exception ex) {
            System.out.println("Class [{}] not found with Class.forName");
        }
        // otherwise iterate all loaded classes and find what we want
        for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
            if (clazz.getName().equals(className)) {
                targetCls = clazz;
                targetClassLoader = targetCls.getClassLoader();
                transform(targetCls, targetClassLoader, instrumentation);
                return;
            }
        }
        throw new RuntimeException(
            "Failed to find class [" + className + "]");
    }

    private static void transform(Class<?> clazz, ClassLoader classLoader, Instrumentation instrumentation) {
        DemoClassClassFileTransformer dt = new DemoClassClassFileTransformer(clazz.getName(), classLoader);
        instrumentation.addTransformer(dt, true);
        try {
            instrumentation.retransformClasses(clazz);
        } catch (Exception ex) {
            throw new RuntimeException(
                "Transform failed for: [" + clazz.getName() + "]", ex);
        }
    }
}