package forfun.sandbox.uwns.node.network;

import org.aeonbits.owner.Config;

public interface ConfigNetworkModule {
    @Config.Key("node.tsp.port")
    @Config.DefaultValue("7777")
    int TcpPort();    
}
