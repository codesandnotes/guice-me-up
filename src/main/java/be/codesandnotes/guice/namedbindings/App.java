package be.codesandnotes.guice.namedbindings;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

import javax.inject.Inject;
import javax.inject.Named;

interface MyType {
    String name();
}

class MyTypeImpl1 implements MyType {
    @Override
    public String name() {
        return "MyTypeImpl1";
    }
}

class MyTypeImpl2 implements MyType {
    @Override
    public String name() {
        return "MyTypeImpl2";
    }
}

class MyService {
    @Inject
    public MyService(@Named("impl1") MyType impl1, @Named("impl2") MyType impl2) {
        System.out.println(impl1.name());
        System.out.println(impl2.name());
        System.out.println("Everything is wired as it should!");
    }
}

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(MyType.class).annotatedWith(Names.named("impl1")).to(MyTypeImpl1.class).asEagerSingleton();
        bind(MyType.class).annotatedWith(Names.named("impl2")).to(MyTypeImpl2.class).asEagerSingleton();
    }
}

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module());
        injector.getInstance(MyService.class);
    }
}
