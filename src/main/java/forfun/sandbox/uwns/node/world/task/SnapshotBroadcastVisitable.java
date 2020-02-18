package forfun.sandbox.uwns.node.world.task;

import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;

public interface SnapshotBroadcastVisitable {

    void acceptVisit(SnapshotBroadcastVisitor visitor, MetaDataSnapshot snapshot);

}
