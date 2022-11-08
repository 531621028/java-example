package agent;

import java.lang.instrument.Instrumentation;

public class StaticAgent {


    //  java -javaagent:D:\study\java-instrumentation\agent\target\agent-1.0-SNAPSHOT.jar -jar D:\study\java-instrumentation\application
    // \target\application-1.0-SNAPSHOT.jar
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain agent start");
    }
}