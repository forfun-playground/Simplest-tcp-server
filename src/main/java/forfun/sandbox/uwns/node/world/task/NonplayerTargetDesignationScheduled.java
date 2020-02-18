package forfun.sandbox.uwns.node.world.task;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.node.world.character.Nonplayer;
import forfun.sandbox.uwns.shared.math.Mathext;
import forfun.sandbox.uwns.shared.service.SimpleTaskBase;
import org.dyn4j.geometry.Vector2;

public class NonplayerTargetDesignationScheduled extends SimpleTaskBase {

    private final WorldApi world;
    public static final double REQUIRED_DISTANCE  = 1.0D;

    @Inject
    public NonplayerTargetDesignationScheduled(WorldApi world) {
        this.world = world;
    }

    @Override
    public void runImpl() {
        world.getActors().stream()
                .filter(e -> e instanceof Nonplayer && e.active())
                .map(e -> (Nonplayer) e)
                .forEach(nonplayer -> targetDesignation(nonplayer));
    }

    public void targetDesignation(Nonplayer nonplayer) {
        Vector2 position = nonplayer.position();
        Vector2 target = nonplayer.target().position();
        double distanceSquared = position.distanceSquared(target);
        double requiredDistanceSquared = requiredDistanceSquared();
        if (distanceSquared < requiredDistanceSquared) {
            double radius = world.getArenaRadius();
            Vector2 newTargetPosition = Mathext.doubleRandomCircle(radius);
            nonplayer.target().position(newTargetPosition);
        }
    }

    private double requiredDistanceSquared() {
        return REQUIRED_DISTANCE * REQUIRED_DISTANCE;
    }

}
