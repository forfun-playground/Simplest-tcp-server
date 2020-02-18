package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import forfun.sandbox.uwns.shared.pack.proto.Actor;
import forfun.sandbox.uwns.shared.pack.proto.Agent;
import forfun.sandbox.uwns.shared.pack.proto.Message;
import forfun.sandbox.uwns.shared.pack.proto.SnapshotResponse;
import forfun.sandbox.uwns.shared.pack.proto.Vector;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.ArrayList;
import java.util.List;

public class ProtoSnapshotResponseHandler extends MessageToMessageEncoder<Object>{

    @Override
    public boolean acceptOutboundMessage(Object obj) {
        return (obj instanceof MetaData) ? ((MetaData) obj) instanceof MetaDataSnapshot : false;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object in, List<Object> out) throws Exception {
        MetaDataSnapshot meta = (MetaDataSnapshot) in;
        SnapshotResponse.Builder response = SnapshotResponse.newBuilder();
        response.setRadius((float) meta.radius);
        Iterable<Actor> targets = ActorsListBuilder(meta.targets);
        response.addAllTargets(targets);
        Iterable<Agent> players = AgentsListBuilder(meta.players);
        response.addAllPlayers(players);
        Iterable<Agent> nonplayers = AgentsListBuilder(meta.nonplayers);
        response.addAllNonPlayers(nonplayers);
        Message.Builder message = Message.newBuilder().setSnapshotResponse(response.build());
        out.add(message.build());
    }

    private Iterable<Actor> ActorsListBuilder(Iterable<MetaDataSnapshot.Actor> mataactors) {
        List<Actor> actors = new ArrayList<>();
        for (MetaDataSnapshot.Actor metaactor : mataactors) {
            Actor.Builder actor = Actor.newBuilder();
            actor.setUid(metaactor.uid);
            actor.setActive(metaactor.active);
            Vector position = buildVector(metaactor.position.x, metaactor.position.y);
            actor.setPosition(position);
            actors.add(actor.build());
        }
        return actors;
    }
    
    private Iterable<Agent> AgentsListBuilder(Iterable<MetaDataSnapshot.Agent> metaagents){
        List<Agent> agents = new ArrayList<>();
        for (MetaDataSnapshot.Agent metaagent : metaagents){
            Agent.Builder agent = Agent.newBuilder();
            agent.setUid(metaagent.uid);
            agent.setTargetId(metaagent.tid);
            agent.setActive(metaagent.active);
            Vector position = buildVector(metaagent.position.x, metaagent.position.y);
            agent.setPosition(position);
            Vector velocity = buildVector(metaagent.velocity.x, metaagent.velocity.y);
            agent.setVelocity(velocity);
            agents.add(agent.build());
        }
        return agents;
    }
    
    private Vector buildVector(double x, double y) {
        Vector.Builder vector = Vector.newBuilder();
        vector.setX((float) x);
        vector.setY((float) y);
        return vector.build();
    }
}
