import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

class Ioc {

    private Ioc() {
    }

    static TestLogging createTestLoggingImpl(TestLogging testLogging) {
        InvocationHandler handler = new CustomInvocationHandler(testLogging);
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class CustomInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;
        private final List<Method> annotatedMethodList;

        CustomInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            List<Method> annotatedMethodList = new ArrayList<>();
            for (Method method: testLogging.getClass().getDeclaredMethods()) {
                for (Annotation annotation: method.getDeclaredAnnotations()) {
                    if (annotation.annotationType() == Log.class) {
                        annotatedMethodList.add(method);
                    }
                }
            }
            this.annotatedMethodList = annotatedMethodList;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (Method annotatedMethod: annotatedMethodList) {
                if (annotatedMethod.getName().equals(method.getName()) &&
                        Arrays.toString(annotatedMethod.getParameterTypes()).equals(Arrays.toString(method.getParameterTypes()))) {
                    System.out.println("executed method: " + method.getName() + ", params: " + Arrays.toString(args));
                    break;
                }
            }
            return method.invoke(testLogging, args);
        }

    }
}
