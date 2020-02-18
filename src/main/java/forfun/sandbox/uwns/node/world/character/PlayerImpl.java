package forfun.sandbox.uwns.node.world.character;

import forfun.sandbox.uwns.node.world.Connector;
import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataEnterWorld;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import forfun.sandbox.uwns.shared.meta.MetaDataTarget;
import lombok.extern.slf4j.Slf4j;
import org.dyn4j.geometry.Vector2;
import forfun.sandbox.uwns.node.world.task.SnapshotBroadcastVisitor;

@Slf4j
public class PlayerImpl extends AgentBase implements Player {

    private final Connector connector;

    public PlayerImpl(int uid, WorldApi world, Vector2 position, Vector2 velocity, Target target, Connector connector) {
        super(uid, world, position, velocity, target);
        this.connector = connector;
    }

    @Override
    public void onConnect() {
    }

    @Override
    public void onReady() {
        active(true);
        reportAssignedUID(uid());
    }

    private void reportAssignedUID(int uid) {
        MetaDataEnterWorld meta = new MetaDataEnterWorld();
        meta.uid = uid;
        sendMessage(meta);
    }

    @Override
    public void acceptMessage(MetaData metadata) {
        if (metadata instanceof MetaDataTarget) {
            double x = ((MetaDataTarget) metadata).position.x;
            double y = ((MetaDataTarget) metadata).position.y;
            Vector2 target = new Vector2(x, y);
            target().position(target);
        } else {
            log.warn("Not supporting the operation");
        }
    }

    @Override
    public void sendMessage(MetaData metadata) {
        connector.sendMessage(metadata);
    }

    @Override
    public void onDisconnect() {
        active(false);
        world.leaveWorld(this);
    }

    @Override
    public void acceptVisit(SnapshotBroadcastVisitor visitor, MetaDataSnapshot snapshot) {
        visitor.addAgentMetadata(snapshot, this);
    }
}
