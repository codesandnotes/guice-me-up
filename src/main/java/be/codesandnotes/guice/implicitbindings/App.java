package be.codesandnotes.guice.implicitbindings;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.ImplementedBy;
import com.google.inject.Injector;
import com.google.inject.ProvidedBy;
import com.google.inject.name.Names;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class One { // Has an instance binding injected. Look dad, no interface! Also, I didn't have to explicitly bind this!
    @Inject
    One(@Named("someValue") int someValue) {
        System.out.println(someValue);
    }
}

class Two {} // Has a no-arguments constructor, so it's also considered implicitly eligible for binding. Still no interface.

@ImplementedBy(ThreeImpl.class) // Equivalent of an explicit binding in a module.
interface Three {}

class ThreeImpl implements Three {}

@ProvidedBy(FourProvider.class) // Equivalent of an explicit provider declaration in a module.
class Four {
    Four(String arg1, int arg2) {}
}

class FourProvider implements Provider<Four> {
    @Override
    public Four get() {
        return new Four("some string", 777);
    }
}

class MyService {
    @Inject // The constructor has arguments, so needs to be annotated with @Inject. But MyService is not explicitly bound in a module!
    public MyService(One one, Two two, Three three, Four four) {
        if (one != null && two != null && three != null && four != null) {
            System.out.println("Everything is wired as it should!");
        } else {
            System.err.println("Ouch, something was not wired as expected...");
        }
    }
}

class Module extends AbstractModule {
    @Override
    protected void configure() {
        bind(Integer.class).annotatedWith(Names.named("someValue")).toInstance(123); // Here we define our instance binding
    }
}

public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module());
        injector.getInstance(MyService.class);
    }
}
