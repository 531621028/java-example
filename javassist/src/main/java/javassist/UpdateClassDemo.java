package javassist;

/**
 * @author kang
 * @date 2022/11/8
 */
public class UpdateClassDemo {

    public static void main(String[] args)
        throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        ClassPool classPool = ClassPool.getDefault();
        // 如果使用这种方式，再次调用userClass.toClass() 的时候会报错 duplicate class definition；因为同一个类只能加载一次
        // final CtClass userClass = classPool.get(UserService.class.getName());
        final CtClass userClass = classPool.get("javassist.UserService");
        final CtMethod eatMethod = userClass.getDeclaredMethod("eat");
        eatMethod.insertBefore("System.out.println(\"washing hands\");");
        eatMethod.insertAfter("System.out.println(\"wipe mouth\");");

        final UserService userService = (UserService) userClass.toClass().newInstance();
        userService.eat();
    }
}
