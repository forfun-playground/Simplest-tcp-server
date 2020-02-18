package forfun.sandbox.uwns.node.world;

import forfun.sandbox.uwns.node.world.character.Actor;
import forfun.sandbox.uwns.node.world.character.Nonplayer;
import forfun.sandbox.uwns.node.world.character.Target;
import java.util.List;

public interface WorldApi {

    double getArenaRadius();
    
    List<Actor> getActors();

    Nonplayer joinNewNonplayer();

    Target joinNewTarget();

    void leaveWorld(Actor actor);

}
