package agent;

import java.lang.instrument.Instrumentation;

public class DynamicAgent {


    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        // 此处的代码运行在被绑定的JVM进程上，也就是在Application的进程中输出attach JVM success
        System.out.println("attach JVM success");
    }
}