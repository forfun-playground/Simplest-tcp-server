package forfun.sandbox.uwns.bothub;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources({"file:conf/bothub.properties"})
public interface BothubConfig extends Config {

    @Key("boot.greeting")
    @DefaultValue("Attention: the configuration is not loaded!")
    String Greeting();

    @Key("bothub.bots.count")
    @DefaultValue("10")
    int BotsCount();

    @Key("bothub.protocol.type")
    @DefaultValue("Protobuf")
    String ProtocolType();

    @Key("bothub.node.host")
    @DefaultValue("127.0.0.1")
    String Host();

    @Key("bothub.node.tcp.port")
    @DefaultValue("7777")
    int TcpPort();

}
