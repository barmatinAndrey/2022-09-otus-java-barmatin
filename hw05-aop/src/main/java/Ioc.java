import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class Ioc {

    private Ioc() {
    }

    static TestLogging createTestLoggingImpl() {
        InvocationHandler handler = new CustomInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class CustomInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;

        CustomInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Annotation annotation = testLogging.getClass()
                    .getMethod(method.getName(), method.getParameterTypes())
                    .getAnnotation(Log.class);
            if (annotation != null) {
                System.out.println("executed method: " + method.getName() + ", params: " + Arrays.toString(args));
            }

            return method.invoke(testLogging, args);
        }

    }
}
