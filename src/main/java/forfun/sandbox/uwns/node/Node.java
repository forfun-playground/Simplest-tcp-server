package forfun.sandbox.uwns.node;

import com.google.inject.Guice;
import com.google.inject.Injector;
import forfun.sandbox.uwns.node.network.NetworkBootstrap;
import forfun.sandbox.uwns.node.world.BootWorldModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Node {

    public static void main(String[] args) {
        try {
            Injector injector = Guice.createInjector(new NodeBind());
            NodeConfigFactory configFactory = injector.getInstance(NodeConfigFactory.class);
            NodeConfig config = configFactory.getConfig();
            log.info(config.Greeting());
            int nubberOfNonPlayerAgents = config.WorldNonPlayerAgents();
            log.info("Number of non-player character: " + nubberOfNonPlayerAgents);
            log.info("Start Forfun sandbox UWNNode");
            BootWorldModule worldBootstrap = injector.getInstance(BootWorldModule.class);
            worldBootstrap.start(nubberOfNonPlayerAgents);
            NetworkBootstrap networkBootstrap = injector.getInstance(NetworkBootstrap.class);
            networkBootstrap.start();
        } catch (Exception e) {
            log.warn(e.getMessage() + e);
            System.exit(1);
        }
    }
}
