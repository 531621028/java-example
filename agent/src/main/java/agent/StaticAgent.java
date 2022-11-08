package agent;

import java.lang.instrument.Instrumentation;

public class StaticAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain agent start");
    }
}