package forfun.sandbox.uwns.node.world.character;

import org.dyn4j.geometry.Vector2;

public interface Agent extends Actor {

    void initialize();

    Vector2 velocity();

    void velocity(Vector2 velocity);

    void move(double deltaTime);

    Target target();

    void target(Target target);
    
}
