# AOP

## 1.编译阶段增强-AJC

使用AspectJ编译器可以增强静态方法, 直接修改字节码文件

## 2.类加载阶段增强-agent

JVM启动时配置参数: -javaagent

## 3.程序运行阶段增强-proxy

### 3.1 JDK动态代理

代理对象实现了目标对象的所有接口的方法, 和目标对象是兄弟关系

```java
public class JdkProxyDemo {

    public static void main(String[] args) {
        Target target = new Target();
        Foo targetProxy = (Foo) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args1) -> {
                    System.out.println("before");
                    Object res = method.invoke(target, args1);
                    System.out.println("after");
                    return res;
                });
        System.out.println(targetProxy.getClass()); // $Proxy0
        targetProxy.foo();
    }

    interface Foo {
        void foo();
    }

    static class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("foo");
        }
    }

}
```

### 3.2 CGLIB动态代理

代理对象继承了目标对象, 和目标对象是父子关系, 目标对象类也不能是final修饰的

```java
public class Demo {

    public static void main(String[] args) throws Throwable {
        Target target = new Target();

        Target targetProxy = (Target) Enhancer.create(target.getClass(),
                (MethodInterceptor) (proxy, method, arguments, methodProxy) -> {
                    System.out.println("before");
                    Object res = null;
                    // 1.通过方法反射调用
                    res = method.invoke(target, arguments);
                    // 2.通过FastClass直接调用目标对象的方法
                    res = methodProxy.invoke(target, arguments);
                    // 3.通过FastClass调用目标代理类中生成的方法
                    res = methodProxy.invokeSuper(proxy, arguments);
                    System.out.println("after");
                    return res;
                });

        System.out.println(Target.class);
        System.out.println(targetProxy.getClass());
        targetProxy.foo();
        System.out.println("--------");
        targetProxy.foo(66);
        System.out.println("--------");
        targetProxy.foo("nice!");
        System.in.read();
    }

}
```

## 4 Advisor

切面=切点Pointcut+通知Advice

切点表达式类AspectJExpressionPointcut
