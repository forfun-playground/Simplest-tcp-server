package forfun.sandbox.uwns.node.world.character;

import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.shared.math.Mathext;
import org.dyn4j.geometry.Vector2;

// NOTE: synchronization is more effective if the probability of a race condition is extremely low
public abstract class AgentBase extends ActorBase implements Agent {

    private final Vector2 velocity;
    private volatile Target target;
    protected final WorldApi world;

    public AgentBase(int uid, WorldApi world, Vector2 position, Vector2 velocity, Target target) {
        super(uid, position);
        this.velocity = velocity;
        this.target = target;
        this.world = world;
    }

    @Override
    public void initialize() {
        double radius = world.getArenaRadius();
        Vector2 targetPosition = Mathext.doubleRandomCircle(radius);
        target.position(targetPosition);
        Vector2 agentPosition = Mathext.doubleRandomCircle(radius);
        position(agentPosition);
    }

    @Override
    public synchronized Vector2 velocity() {
        return velocity.copy();
    }

    @Override
    public synchronized void velocity(Vector2 velocity) {
        this.velocity.set(velocity);
    }

    @Override
    public synchronized void move(double deltaTime) {
        Vector2 impulse = velocity().multiply(deltaTime);
        translate(impulse);
    }

    @Override
    public Target target() {
        return target;
    }

    @Override
    public void target(Target target) {
        if (target != null) {
            this.target = target;
        } else {
            throw new NullPointerException("Exception: target cannot be null");
        }
    }
}
