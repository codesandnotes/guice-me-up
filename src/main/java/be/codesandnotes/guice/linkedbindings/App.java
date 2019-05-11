package be.codesandnotes.guice.linkedbindings;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.inject.Inject;

interface MyType {
    String name();
}

class MyTypeImpl implements MyType {
    @Override
    public String name() {
        return "MyTypeImpl";
    }
}

class MyService {
    @Inject
    public MyService(MyType myType) {
        System.out.println(myType.name());
        System.out.println("Everything is wired as it should!");
    }
}

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(MyType.class).to(MyTypeImpl.class).asEagerSingleton();
    }
}

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module());
        injector.getInstance(MyService.class);
    }
}