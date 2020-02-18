package forfun.sandbox.uwns.node.world;

import forfun.sandbox.uwns.shared.meta.MetaData;

public interface Playable extends Identity {
    
    void onConnect();
    
    void onReady();

    void acceptMessage(MetaData metadata);
    
    void onDisconnect();
}
