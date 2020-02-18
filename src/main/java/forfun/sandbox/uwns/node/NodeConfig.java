package forfun.sandbox.uwns.node;

import forfun.sandbox.uwns.node.network.ConfigNetworkModule;
import forfun.sandbox.uwns.node.world.ConfigWorldModule;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources({"file:conf/node.properties"})
public interface NodeConfig extends Config, ConfigNetworkModule, ConfigWorldModule {

    @Key("node.greeting")
    @DefaultValue("Attention: the configuration is not loaded!")
    String Greeting();

}
