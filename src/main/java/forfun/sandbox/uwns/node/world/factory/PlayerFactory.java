package forfun.sandbox.uwns.node.world.factory;

import forfun.sandbox.uwns.node.world.Connector;
import forfun.sandbox.uwns.node.world.character.Player;
import forfun.sandbox.uwns.node.world.character.Target;

public interface PlayerFactory {
    
    Player getInctance(int uid, Target target, Connector connector);
    
}
