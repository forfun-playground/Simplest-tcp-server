package forfun.sandbox.uwns.node.world.task;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.node.world.character.Agent;
import forfun.sandbox.uwns.shared.service.SimpleTaskBase;
import java.util.List;
import java.util.stream.Collectors;
import org.dyn4j.geometry.Vector2;

public class AgentAiBehaviorScheduled extends SimpleTaskBase {

    private final WorldApi world;
    private static final double LIMIT_VELOCITY = 1.0D;
    private static final double DISTANCE_OF_INTERACTION = 3.0D;

    @Inject
    public AgentAiBehaviorScheduled(WorldApi world) {
        this.world = world;
    }

    @Override
    public void runImpl() {
        List<Agent> onlyAgents = filterWorldMembers();
        updateAgentsBehavior(onlyAgents);
    }

    private List<Agent> filterWorldMembers() {
        return world.getActors().stream()
                .filter(a -> a instanceof Agent && a.active())
                .map(a -> (Agent) a)
                .collect(Collectors.toList());
    }

    private void updateAgentsBehavior(List<Agent> anothers) {
        anothers.stream().forEach(self -> updateBehavior(self, anothers));
    }

    public void updateBehavior(Agent self, Iterable<Agent> anothers) {
        Vector2 selfPosition = self.position();
        Vector2 selfVelocity = calculateDefaultVelocity(selfPosition, self.target().position());
        for (Agent another : anothers) {
            if (self.uid() != another.uid()) {
                if (isDistanceOfInteraction(selfPosition, another.position())) {
                    Vector2 pushImpulse = calculatePushImpulce(self, another);
                    selfVelocity.add(pushImpulse);
                }
            }
        }
        Vector2 selfLimitedVelocity = limitMaximumVelocity(selfVelocity);
        self.velocity(selfLimitedVelocity);
    }

    private Vector2 calculateDefaultVelocity(Vector2 selfPosition, Vector2 selfTargetPosition) {
        Vector2 velocity = selfPosition.to(selfTargetPosition).getNormalized().multiply(1);
        Double distance = selfPosition.to(selfTargetPosition).getMagnitude();
        return distance < 1.0D ? velocity.multiply(distance) : velocity;
    }

    private boolean isDistanceOfInteraction(Vector2 selfPosition, Vector2 anotherPosition) {
        Vector2 anotherToSelf = anotherPosition.to(selfPosition);
        double distanceAnotherToSelf = anotherToSelf.getMagnitude();
        return distanceAnotherToSelf < DISTANCE_OF_INTERACTION;
    }

    private Vector2 calculatePushImpulce(Agent self, Agent another) {
        Vector2 anotherPosition = another.position();
        Vector2 anotherToSelfDirection = anotherPosition.to(self.position());
        double anotherToSelfDistance = anotherToSelfDirection.getMagnitudeSquared();
        Vector2 pushDirection = anotherToSelfDirection.getNormalized();
        return pushDirection.multiply(1.0D / anotherToSelfDistance);
    }

    private Vector2 limitMaximumVelocity(Vector2 velocity) {
        return velocity.getMagnitude() > LIMIT_VELOCITY ? velocity.getNormalized().multiply(LIMIT_VELOCITY) : velocity;
    }
}
