package forfun.sandbox.uwns.node.world.character;

import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.node.world.task.SnapshotBroadcastVisitor;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import org.dyn4j.geometry.Vector2;

public class NonplayerImpl extends AgentBase implements Nonplayer {

    public NonplayerImpl(int uid, WorldApi world, Vector2 position, Vector2 velocity, Target target) {
        super(uid, world, position, velocity, target);
    }

    @Override
    public void acceptVisit(SnapshotBroadcastVisitor visitor, MetaDataSnapshot snapshot) {
        visitor.addAgentMetadata(snapshot, this);
    }
}
