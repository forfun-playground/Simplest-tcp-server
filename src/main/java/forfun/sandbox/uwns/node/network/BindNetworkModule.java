package forfun.sandbox.uwns.node.network;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class BindNetworkModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(NetworkBootstrap.class);
        bind(NetworkInitializer.class);
        bind(PlayerConnectorFactory.class).to(PlayerConnectorFactoryImpl.class).in(Singleton.class);
    }
}
