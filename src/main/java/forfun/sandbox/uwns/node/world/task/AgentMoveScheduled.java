package forfun.sandbox.uwns.node.world.task;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.NodeConfig;
import forfun.sandbox.uwns.node.NodeConfigFactory;
import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.node.world.character.Agent;
import forfun.sandbox.uwns.shared.service.SimpleTaskBase;

public class AgentMoveScheduled extends SimpleTaskBase {

    private final WorldApi world;
    private final NodeConfig config;
    private final double deltatime;

    @Inject
    public AgentMoveScheduled(
            WorldApi world,
            NodeConfigFactory configFactory
    ) {
        this.world = world;
        this.config = configFactory.getConfig();
        this.deltatime = 1D / config.WorldMovingAgentPerSecond();
    }

    @Override
    public void runImpl() {
        world.getActors().stream()
                .filter(a -> a instanceof Agent && a.active())
                .map(e -> (Agent) e)
                .forEach(m -> m.move(deltatime));
    }

}
