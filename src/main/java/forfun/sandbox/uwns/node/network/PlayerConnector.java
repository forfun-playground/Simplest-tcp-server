package forfun.sandbox.uwns.node.network;

import forfun.sandbox.uwns.node.world.Connector;
import forfun.sandbox.uwns.node.world.Playable;
import io.netty.util.AttributeKey;

public interface PlayerConnector extends Connector {

    public final static AttributeKey<Playable> REFERENCE = AttributeKey.valueOf("Playable");

}
