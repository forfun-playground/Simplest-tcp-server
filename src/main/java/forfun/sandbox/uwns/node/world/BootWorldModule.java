package forfun.sandbox.uwns.node.world;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.NodeConfigFactory;
import forfun.sandbox.uwns.shared.service.ExecuteMaster;
import forfun.sandbox.uwns.node.world.task.AgentMoveScheduled;
import forfun.sandbox.uwns.node.world.task.SnapshotBroadcastScheduled;
import forfun.sandbox.uwns.node.world.task.AgentAiBehaviorScheduled;
import forfun.sandbox.uwns.node.world.task.NonplayerTargetDesignationScheduled;

public class BootWorldModule {

    private final ConfigWorldModule config;
    private final WorldApi world;
    private final ExecuteMaster executeMaster;
    SnapshotBroadcastScheduled snapshotBroadcastScheduled;
    AgentMoveScheduled movingAgentsScheduled;
    AgentAiBehaviorScheduled updateAgentsBehaviorScheduled;
    NonplayerTargetDesignationScheduled updateTargetDesignationScheduled;

    @Inject
    public BootWorldModule(
            NodeConfigFactory nodeConfigFactory,
            WorldApi world,
            ExecuteMaster executeMaster,
            SnapshotBroadcastScheduled snapshotBroadcastScheduled,
            AgentMoveScheduled movingAgentsScheduled,
            AgentAiBehaviorScheduled updateAgentsBehaviorScheduled,
            NonplayerTargetDesignationScheduled updateTargetDesignationScheduled
    ) {
        this.config = nodeConfigFactory.getConfig();
        this.world = world;
        this.executeMaster = executeMaster;
        this.snapshotBroadcastScheduled = snapshotBroadcastScheduled;
        this.movingAgentsScheduled = movingAgentsScheduled;
        this.updateAgentsBehaviorScheduled = updateAgentsBehaviorScheduled;
        this.updateTargetDesignationScheduled = updateTargetDesignationScheduled;
    }

    public void start(int nonPlayerPopulation) {
        executeServiceTasks();
        spawnNonPlayerCharacter(nonPlayerPopulation);
    }

    private void executeServiceTasks() {
        executeMaster.executeScheduledTask(snapshotBroadcastScheduled, 100, 1000L / config.WorldBroadcastSnapshotPerSecond());
        executeMaster.executeScheduledTask(movingAgentsScheduled, 100, 1000L / config.WorldMovingAgentPerSecond());
        executeMaster.executeScheduledTask(updateAgentsBehaviorScheduled, 100, 1000L / config.WorldMovingAgentPerSecond());
        executeMaster.executeScheduledTask(updateTargetDesignationScheduled, 100, 1000L / config.WorldMovingAgentPerSecond());
    }

    private void spawnNonPlayerCharacter(int count) {
        for (int i = 0; i < count; i++) {
            world.joinNewNonplayer();
        }
    }

}
