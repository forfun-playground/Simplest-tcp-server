package forfun.sandbox.uwns.node.world;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import forfun.sandbox.uwns.node.world.factory.NonplayerFactory;
import forfun.sandbox.uwns.node.world.factory.NonplayerFactoryImpl;
import forfun.sandbox.uwns.node.world.factory.PlayerFactory;
import forfun.sandbox.uwns.node.world.factory.PlayerFactoryImpl;
import forfun.sandbox.uwns.node.world.factory.TargetFactory;
import forfun.sandbox.uwns.node.world.factory.TargetFactoryImpl;
import forfun.sandbox.uwns.shared.service.ExecuteMaster;
import forfun.sandbox.uwns.shared.service.ExecuteMasterImpl;
import forfun.sandbox.uwns.node.world.task.AgentMoveScheduled;
import forfun.sandbox.uwns.node.world.task.SnapshotBroadcastScheduled;
import forfun.sandbox.uwns.node.world.task.AgentAiBehaviorScheduled;
import forfun.sandbox.uwns.node.world.task.NonplayerTargetDesignationScheduled;

public class BindWorldModule extends AbstractModule {
    
    @Override
    protected void configure() {
        bind(BootWorldModule.class).in(Singleton.class);
        bind(WorldImpl.class).in(Singleton.class);
        bind(World.class).to(WorldImpl.class).in(Singleton.class);
        bind(WorldApi.class).to(WorldImpl.class).in(Singleton.class);
        
        bind(IdentityProvider.class).to(IdentityProviderImpl.class).in(Singleton.class);
        bind(PlayerFactory.class).to(PlayerFactoryImpl.class).in(Singleton.class);
        bind(NonplayerFactory.class).to(NonplayerFactoryImpl.class).in(Singleton.class);
        bind(TargetFactory.class).to(TargetFactoryImpl.class).in(Singleton.class);
     
        bind(ExecuteMaster.class).to(ExecuteMasterImpl.class).in(Singleton.class);
        bind(AgentMoveScheduled.class);
        bind(SnapshotBroadcastScheduled.class);
        bind(AgentAiBehaviorScheduled.class);
        bind(NonplayerTargetDesignationScheduled.class);
        
    }
}
