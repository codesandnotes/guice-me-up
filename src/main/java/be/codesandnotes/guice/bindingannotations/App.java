package be.codesandnotes.guice.bindingannotations;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.inject.Inject;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

interface MyType {
    String name();
}

class MyTypeImpl1 implements MyType {
    @Override
    public String name() {
        return "MyTypeImpl1";
    }
}

@BindingAnnotation
@Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
@interface Impl1 {}

class MyTypeImpl2 implements MyType {
    @Override
    public String name() {
        return "MyTypeImpl2";
    }
}

@BindingAnnotation
@Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
@interface Impl2 {}

class MyService {
    @Inject
    public MyService(@Impl1 MyType impl1, @Impl2 MyType impl2) {
        System.out.println(impl1.name());
        System.out.println(impl2.name());
        System.out.println("Everything is wired as it should!");
    }
}

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(MyType.class).annotatedWith(Impl1.class).to(MyTypeImpl1.class).asEagerSingleton();
        bind(MyType.class).annotatedWith(Impl2.class).to(MyTypeImpl2.class).asEagerSingleton();
    }
}

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module());
        injector.getInstance(MyService.class);
    }
}
