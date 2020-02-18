package forfun.sandbox.uwns.node.world.character;

import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import org.dyn4j.geometry.Vector2;
import forfun.sandbox.uwns.node.world.task.SnapshotBroadcastVisitor;

public class TargetImpl extends ActorBase implements Target {

    public TargetImpl(int uid, WorldApi world, Vector2 position) {
        super(uid, position);
    }

    @Override
    public void acceptVisit(SnapshotBroadcastVisitor visitor, MetaDataSnapshot snapshot) {
        visitor.addAgentMetadata(snapshot, this);
    }
}
