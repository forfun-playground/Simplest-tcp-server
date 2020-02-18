package forfun.sandbox.uwns.node.world.factory;

import forfun.sandbox.uwns.node.world.character.Nonplayer;
import forfun.sandbox.uwns.node.world.character.Target;

public interface NonplayerFactory {
    
    Nonplayer getInstance(int uid, Target target);
    
}
