package be.codesandnotes.guice.providerbindings;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

interface MyType {
    String name();
}

class MyTypeImpl implements MyType {
    private String name;

    @Override
    public String name() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}

@BindingAnnotation
@Target({ FIELD, PARAMETER, METHOD }) @Retention(RUNTIME)
@interface Impl1 {}

class MyService {
    @Inject
    public MyService(@Impl1 MyType impl1, @Named("impl2") MyType impl2) {
        System.out.println(impl1.name());
        System.out.println(impl2.name());
        System.out.println("Everything is wired as it should!");
    }
}

class Module extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Impl1
    MyType provideImpl1() {
        MyTypeImpl impl = new MyTypeImpl();
        impl.setName("Impl1 name");
        return impl;
    }

    @Provides
    @Named("impl2")
    MyType provideImpl2() {
        MyTypeImpl impl = new MyTypeImpl();
        impl.setName("Impl2 name");
        return impl;
    }
}

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module());
        injector.getInstance(MyService.class);
    }
}
