package agent;

import java.lang.instrument.Instrumentation;

public class StaticAgent {


    // java -javaagent:D:\study\java-example\agent\target\agent-1.0-SNAPSHOT.jar -cp "C:\Users\KANG\.m2\repository\org\java
    // ssist\javassist\3.25.0-GA\javassist-3.25.0-GA.jar;D:\study\java-example\application\target\application-1.0-SNAPSHOT.jar" application.Application
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain agent start");
        inst.addTransformer(new DemoClassClassFileTransformer("application.UserService",
            null));
    }
}