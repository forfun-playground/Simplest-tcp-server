package forfun.sandbox.uwns.node.world;

import org.aeonbits.owner.Config;

public interface ConfigWorldModule {

    @Config.Key("node.world.population.density")
    @Config.DefaultValue("5")
    double WorldPopulationDensity();

    @Config.Key("node.world.agent.collider.radius")
    @Config.DefaultValue("0.5")
    double WorldAgentColliderRadius();

    @Config.Key("node.world.moving.agents.persecond")
    @Config.DefaultValue("60")
    int WorldMovingAgentPerSecond();

    @Config.Key("node.world.broadcast.snapshot.persecond")
    @Config.DefaultValue("20")
    int WorldBroadcastSnapshotPerSecond();

    @Config.Key("node.world.non.player.agents")
    @Config.DefaultValue("10")
    int WorldNonPlayerAgents();
}
