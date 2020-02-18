package forfun.sandbox.uwns.node;

import org.aeonbits.owner.ConfigFactory;

public class NodeConfigFactory {

    private final NodeConfig nodeConfig;

    public NodeConfigFactory() {
        nodeConfig = ConfigFactory.create(NodeConfig.class);
    }

    public NodeConfig getConfig() {
        return nodeConfig;
    }
}
