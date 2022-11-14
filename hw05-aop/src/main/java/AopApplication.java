public class AopApplication {
    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createTestLoggingImpl(new TestLoggingImpl());
        testLogging.calculation(6);
    }
}



