package forfun.sandbox.uwns.node.world.factory;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.world.Connector;
import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.node.world.character.Player;
import forfun.sandbox.uwns.node.world.character.PlayerImpl;
import forfun.sandbox.uwns.node.world.character.Target;
import org.dyn4j.geometry.Vector2;

public class PlayerFactoryImpl implements PlayerFactory {

    private final WorldApi world;

    @Inject
    public PlayerFactoryImpl(WorldApi world) {
        this.world = world;
    }

    @Override
    public Player getInctance(int uid, Target target, Connector connector) {
        Vector2 position = new Vector2();
        Vector2 velocity = new Vector2();
        return new PlayerImpl(uid, world, position, velocity, target, connector);
    }
}
