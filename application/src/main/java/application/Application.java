package application;

public class Application {

    // java -javaagent:D:\study\java-instrumentation\agent\target\agent-1.0-SNAPSHOT.jar -jar D:\study\java-instrumentation\age
    // nt\target\application-1.0-SNAPSHOT.jar
    public static void main(String[] args) {
        System.out.println("main start");
        try {
            while (true) {
                new UserService().eat();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }
}