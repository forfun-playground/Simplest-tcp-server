package forfun.sandbox.uwns.node.world.character;

import forfun.sandbox.uwns.node.world.Identity;
import forfun.sandbox.uwns.node.world.task.SnapshotBroadcastVisitable;
import org.dyn4j.geometry.Vector2;

public interface Actor extends Identity, SnapshotBroadcastVisitable {

    boolean active();

    void active(boolean state);

    Vector2 position();

    void position(Vector2 position);

    void translate(Vector2 offset);
}
