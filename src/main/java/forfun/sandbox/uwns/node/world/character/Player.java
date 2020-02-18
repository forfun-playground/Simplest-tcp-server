package forfun.sandbox.uwns.node.world.character;

import forfun.sandbox.uwns.node.world.Playable;
import forfun.sandbox.uwns.shared.meta.MetaData;

public interface Player extends Agent, Playable {

    void sendMessage(MetaData msg);
}
