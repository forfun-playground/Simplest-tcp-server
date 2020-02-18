package forfun.sandbox.uwns.node.world;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.NodeConfig;
import forfun.sandbox.uwns.node.NodeConfigFactory;
import forfun.sandbox.uwns.node.world.character.Actor;
import forfun.sandbox.uwns.node.world.character.Agent;
import forfun.sandbox.uwns.node.world.character.Nonplayer;
import forfun.sandbox.uwns.node.world.character.Player;
import forfun.sandbox.uwns.node.world.character.Target;
import forfun.sandbox.uwns.node.world.factory.NonplayerFactory;
import forfun.sandbox.uwns.node.world.factory.PlayerFactory;
import forfun.sandbox.uwns.node.world.factory.TargetFactory;
import forfun.sandbox.uwns.node.world.task.AgentAiBehaviorScheduled;
import forfun.sandbox.uwns.node.world.task.AgentMoveScheduled;
import forfun.sandbox.uwns.node.world.task.SnapshotBroadcastScheduled;
import forfun.sandbox.uwns.shared.service.ExecuteMaster;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorldImpl implements World, WorldApi {

    private final Map<Integer, Actor> actors;
    private final NodeConfig nodeConfig;
    private final IdentityProvider uidProvider;
    private final PlayerFactory playerFactory;
    private final NonplayerFactory nonplayerFactory;
    private final TargetFactory targetFactory;
    private final ExecuteMaster executeMaster;
    private volatile int population;

    @Inject
    public WorldImpl(
            NodeConfigFactory nodeConfigFactory,
            IdentityProvider uidProvider,
            PlayerFactory playerFactory,
            NonplayerFactory nonplayerFactory,
            TargetFactory targetFactory,
            // --
            ExecuteMaster executeMaster,
            SnapshotBroadcastScheduled snapshotBroadcastScheduled,
            AgentMoveScheduled movingAgentsScheduled,
            AgentAiBehaviorScheduled updateAgentsBehaviorScheduled
    ) {
        this.actors = new ConcurrentHashMap<>();
        this.nodeConfig = nodeConfigFactory.getConfig();
        this.uidProvider = uidProvider;
        this.playerFactory = playerFactory;
        this.nonplayerFactory = nonplayerFactory;
        this.targetFactory = targetFactory;
        this.executeMaster = executeMaster;
        this.executeMaster.executeScheduledTask(snapshotBroadcastScheduled, 1000, 1000L / 10);
        this.executeMaster.executeScheduledTask(movingAgentsScheduled, 1000, 1000L / nodeConfig.WorldMovingAgentPerSecond());
        this.executeMaster.executeScheduledTask(updateAgentsBehaviorScheduled, 1000, 1000L / nodeConfig.WorldMovingAgentPerSecond());
    }

    @Override
    public double getArenaRadius() {
        double worldPopulationDensity = nodeConfig.WorldPopulationDensity();
        double arenaArea = population * worldPopulationDensity;
        double arenaRadius = Math.sqrt(arenaArea / Math.PI);
        arenaRadius = arenaRadius < 5 ? 5D : arenaRadius;
        return arenaRadius;
    }

    @Override
    public List<Actor> getActors() {
        return actors.entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
    }

    @Override
    public Playable joinNewPlayer(Connector connector) {
        Player player = createNewPlayer(connector);
        actorJoinInWorld(player);
        return player;
    }

    private Player createNewPlayer(Connector connector) {
        Target target = joinNewTarget();
        int uid = uidProvider.getNext();
        Player player = playerFactory.getInctance(uid, target, connector);
        player.initialize();
        return player;
    }

    @Override
    public Nonplayer joinNewNonplayer() {
        Nonplayer nonplayer = createNewNonplayer();
        actorJoinInWorld(nonplayer);
        return nonplayer;
    }

    private Nonplayer createNewNonplayer() {
        Target target = joinNewTarget();
        int uid = uidProvider.getNext();
        Nonplayer nonplayer = nonplayerFactory.getInstance(uid, target);
        nonplayer.initialize();
        return nonplayer;
    }

    @Override
    public Target joinNewTarget() {
        Target target = createNewTarget();
        actorJoinInWorld(target);
        return target;
    }

    private Target createNewTarget() {
        int uid = uidProvider.getNext();
        Target target = targetFactory.getInctance(uid);
        return target;
    }

    private void actorJoinInWorld(Actor actor) {
        actors.put(actor.uid(), actor);
        recalculatePopulation();
    }

    @Override
    public void leaveWorld(Actor actor) {
        if (actor instanceof Agent) {
            Actor target = ((Agent) actor).target();
            if (target instanceof Target) {
                actorLeaveWorld(target);
            }
        }
        actorLeaveWorld(actor);
        recalculatePopulation();
    }

    private void actorLeaveWorld(Actor actor) {
        int uid = actor.uid();
        if (actors.containsKey(uid)) {
            actors.remove(uid);
            uidProvider.release(uid);
        }
    }

    private void recalculatePopulation() {
        int count = 0;
        for (Map.Entry<Integer, Actor> entry : actors.entrySet()) {
            if (entry.getValue() instanceof Agent) {
                count++;
            }
        }
        population = count;
    }

}
