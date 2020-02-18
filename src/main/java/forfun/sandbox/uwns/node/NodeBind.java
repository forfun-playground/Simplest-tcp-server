package forfun.sandbox.uwns.node;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import forfun.sandbox.uwns.node.network.BindNetworkModule;
import forfun.sandbox.uwns.node.world.BindWorldModule;

public class NodeBind extends AbstractModule {

    @Override
    protected void configure() {
        install(new BindWorldModule());
        install(new BindNetworkModule());
        bind(NodeConfigFactory.class).in(Singleton.class);
    }

}
