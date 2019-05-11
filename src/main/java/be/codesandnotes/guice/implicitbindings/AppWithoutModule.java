package be.codesandnotes.guice.implicitbindings;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.inject.Inject;

class Impl {
    Impl() {
        System.out.println("One is eligible without explicit definition.");
    }
}

class AService {
    @Inject
    public AService(Impl impl) {
        System.out.println(impl != null
                ? "Everything is wired as it should!"
                : "Ouch, something was not wired as expected...");
    }
}

public class AppWithoutModule {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector();
        injector.getInstance(AService.class);
    }
}
