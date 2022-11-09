package agent;

import java.lang.instrument.Instrumentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicAgent {

    // 日志的依赖也必须被绑定的JVM进程的应用的classpath中，打印的何时
    private static final Logger log = LoggerFactory.getLogger(DynamicAgent.class);

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        // 此处的代码运行在被绑定的应用的JVM进程上，也就是在Application的进程中输出attach JVM success，所以相关的依赖和配置是被绑定应用数据
        log.info("attach JVM success");
    }
}