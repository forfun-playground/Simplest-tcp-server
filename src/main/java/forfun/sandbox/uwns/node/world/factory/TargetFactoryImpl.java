package forfun.sandbox.uwns.node.world.factory;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.world.WorldApi;
import forfun.sandbox.uwns.node.world.character.Target;
import forfun.sandbox.uwns.node.world.character.TargetImpl;
import org.dyn4j.geometry.Vector2;

public class TargetFactoryImpl implements TargetFactory {

    private final WorldApi world;

    @Inject
    public TargetFactoryImpl(WorldApi world) {
        this.world = world;
    }

    @Override
    public Target getInctance(int uid) {
        Vector2 position = new Vector2();
        return new TargetImpl(uid, world, position);
    }

}
