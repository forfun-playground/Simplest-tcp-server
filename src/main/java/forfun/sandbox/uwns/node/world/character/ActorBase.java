package forfun.sandbox.uwns.node.world.character;

import org.dyn4j.geometry.Vector2;

// NOTE: synchronized is more effective if the probability of a race condition is extremely low
public abstract class ActorBase implements Actor {

    private final int uid;
    private volatile boolean enable;
    private final Vector2 position;

    public ActorBase(int uid, Vector2 position) {
        this.uid = uid;
        this.enable = true;
        this.position = position;
    }

    @Override
    public int uid() {
        return uid;
    }

    @Override
    public boolean active() {
        return enable;
    }

    @Override
    public void active(boolean enable) {
        this.enable = enable;
    }

    @Override
    public synchronized Vector2 position() {
        return position.copy();
    }

    @Override
    public synchronized void position(Vector2 position) {
        this.position.set(position);
    }

    @Override
    public synchronized void translate(Vector2 offset) {
        this.position.add(offset);
    }
}
