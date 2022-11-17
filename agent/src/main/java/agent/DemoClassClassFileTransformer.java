package agent;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * @author kang
 * @date 2022/11/9
 */
public class DemoClassClassFileTransformer implements ClassFileTransformer {

    private String targetClassName;
    private ClassLoader targetClassLoader;

    public DemoClassClassFileTransformer(String targetClassName, ClassLoader targetClassLoader) {
        this.targetClassName = targetClassName;
        this.targetClassLoader = targetClassLoader;
    }


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
        ProtectionDomain protectionDomain, byte[] classFileBuffer) {
        byte[] byteCode = classFileBuffer;
        String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/");
        if (!className.equals(finalTargetClassName)) {
            System.out.println("className not same " + className);
            return byteCode;
        }
        if (targetClassLoader == null || loader.equals(targetClassLoader)) {
            System.out.println("[Agent] Transforming class");
            try {
                ClassPool cp = ClassPool.getDefault();
                CtClass cc = cp.get(targetClassName);
                CtMethod m = cc.getDeclaredMethod("eat");
                m.addLocalVariable(
                    "startTime", CtClass.longType);
                m.insertBefore(
                    "startTime = System.currentTimeMillis();");

                StringBuilder endBlock = new StringBuilder();

                m.addLocalVariable("endTime", CtClass.longType);
                m.addLocalVariable("opTime", CtClass.longType);
                endBlock.append(
                    "endTime = System.currentTimeMillis();");
                endBlock.append(
                    "opTime = (endTime-startTime)/1000;");

                endBlock.append(
                    "System.out.println(\"eat completed in:" +
                        "\" + opTime + \" seconds!\");");

                m.insertAfter(endBlock.toString());

                byteCode = cc.toBytecode();
                cc.detach();
            } catch (CannotCompileException | IOException | NotFoundException e) {
                System.out.println("Exception" + e);
            }
        }
        return byteCode;
    }
}
