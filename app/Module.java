import com.google.inject.AbstractModule;
import services.ApplicationStart;

public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(ApplicationStart.class).asEagerSingleton();
    }
}
