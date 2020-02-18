package forfun.sandbox.uwns.node.world.task;

import forfun.sandbox.uwns.node.world.character.Nonplayer;
import forfun.sandbox.uwns.node.world.character.Player;
import forfun.sandbox.uwns.node.world.character.Target;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;

public interface SnapshotBroadcastVisitor {

    void addAgentMetadata(MetaDataSnapshot snapshot, Target target);

    void addAgentMetadata(MetaDataSnapshot snapshot, Player player);
    
    void addAgentMetadata(MetaDataSnapshot snapshot, Nonplayer nonplayer);

}
