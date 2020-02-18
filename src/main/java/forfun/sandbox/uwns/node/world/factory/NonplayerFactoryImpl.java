package forfun.sandbox.uwns.node.world.factory;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.node.world.character.Nonplayer;
import forfun.sandbox.uwns.node.world.character.NonplayerImpl;
import forfun.sandbox.uwns.node.world.character.Target;
import org.dyn4j.geometry.Vector2;

public class NonplayerFactoryImpl implements NonplayerFactory {

    private final WorldApi world;

    @Inject
    public NonplayerFactoryImpl(WorldApi world) {
        this.world = world;

    }

    @Override
    public Nonplayer getInstance(int uid, Target target) {
        Vector2 position = new Vector2();
        Vector2 velocity = new Vector2();
        return new NonplayerImpl(uid, world, position, velocity, target);
    }
}
