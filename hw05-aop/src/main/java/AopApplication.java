public class AopApplication {
    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createTestLoggingImpl();
        testLogging.calculation(6);
    }
}



