package forfun.sandbox.uwns.node.world.task;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.node.world.character.Actor;
import forfun.sandbox.uwns.node.world.character.Agent;
import forfun.sandbox.uwns.node.world.character.Nonplayer;
import forfun.sandbox.uwns.node.world.character.Player;
import forfun.sandbox.uwns.node.world.character.Target;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import forfun.sandbox.uwns.shared.service.SimpleTaskBase;

public class SnapshotBroadcastScheduled extends SimpleTaskBase implements SnapshotBroadcastVisitor {

    private final WorldApi world;

    @Inject
    public SnapshotBroadcastScheduled(WorldApi world) {
        this.world = world;
    }

    @Override
    public void runImpl() {
        MetaDataSnapshot metaSnapshot = buildMetaSnapshot(world);
        sendBroatcastMessage(metaSnapshot);
    }

    private MetaDataSnapshot buildMetaSnapshot(WorldApi world) {
        MetaDataSnapshot snapshot = new MetaDataSnapshot();
        snapshot.radius = world.getArenaRadius();
        visitActors(snapshot);
        return snapshot;
    }

    private void visitActors(MetaDataSnapshot snapshot) {
        world.getActors().stream()
                .forEach((actor) -> visitActorIfActive(snapshot, actor));
    }

    private void visitActorIfActive(MetaDataSnapshot snapshot, Actor actor) {
        if (actor.active()) {
            actor.acceptVisit(this, snapshot);
        }
    }

    @Override
    public void addAgentMetadata(MetaDataSnapshot snapshot, Target target) {
        if (target.active()) {
            MetaDataSnapshot.Actor metaActor = buildMetaActor(snapshot, target);
            snapshot.targets.add(metaActor);
        }
    }

    @Override
    public void addAgentMetadata(MetaDataSnapshot snapshot, Player player) {
        if (player.active()) {
            MetaDataSnapshot.Agent metaAgent = buildMetaAgent(snapshot, player);
            snapshot.players.add(metaAgent);
        }
    }

    @Override
    public void addAgentMetadata(MetaDataSnapshot snapshot, Nonplayer nonplayer) {
        if (nonplayer.active()) {
            MetaDataSnapshot.Agent metaAgent = buildMetaAgent(snapshot, nonplayer);
            snapshot.nonplayers.add(metaAgent);
        }
    }

    private MetaDataSnapshot.Actor buildMetaActor(MetaDataSnapshot snapshot, Actor actor) {
        return snapshot.new Actor(
                actor.uid(),
                actor.active(),
                snapshot.new Vector(actor.position().x, actor.position().y)
        );
    }

    private MetaDataSnapshot.Agent buildMetaAgent(MetaDataSnapshot snapshot, Agent agent) {
        return snapshot.new Agent(
                agent.uid(),
                agent.target().uid(),
                agent.active(),
                snapshot.new Vector(agent.position().x, agent.position().y),
                snapshot.new Vector(agent.velocity().x, agent.velocity().y)
        );
    }

    private void sendBroatcastMessage(MetaDataSnapshot metaSnapshot) {
        world.getActors().stream()
                .filter(actor -> actor instanceof Player && actor.active())
                .map(actor -> (Player) actor)
                .forEach(player -> player.sendMessage(metaSnapshot));
    }
}
