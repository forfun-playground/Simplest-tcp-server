package forfun.sandbox.uwns.node.network.handler;

import com.google.flatbuffers.FlatBufferBuilder;
import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import forfun.sandbox.uwns.shared.pack.flat.Actor;
import forfun.sandbox.uwns.shared.pack.flat.Agent;
import forfun.sandbox.uwns.shared.pack.flat.Message;
import forfun.sandbox.uwns.shared.pack.flat.Packet;
import forfun.sandbox.uwns.shared.pack.flat.SnapshotResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

public class FlatSnapshotResponseHandler extends MessageToMessageEncoder<Object> {

    @Override
    public boolean acceptOutboundMessage(Object obj) {
        return (obj instanceof MetaData) ? ((MetaData) obj) instanceof MetaDataSnapshot : false;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object in, List<Object> out) throws Exception {
        MetaDataSnapshot meta = (MetaDataSnapshot) in;
        FlatBufferBuilder builder = new FlatBufferBuilder(0);

        SnapshotResponse.startTargetsVector(builder, meta.targets.size());
        int targetsOffset = bildActorVector(builder, meta.targets);

        SnapshotResponse.startPlayersVector(builder, meta.players.size());
        int playersOffset = bildAgentVector(builder, meta.players);

        SnapshotResponse.startNonPlayersVector(builder, meta.nonplayers.size());
        int nonPlayersOffset = bildAgentVector(builder, meta.nonplayers);

        int response = SnapshotResponse.createSnapshotResponse(builder, (float) meta.radius, targetsOffset, playersOffset, nonPlayersOffset);
        int packet = Packet.createPacket(builder, Message.SnapshotResponse, response);
        builder.finish(packet);
        chc.writeAndFlush(builder);
    }

    private int bildActorVector(FlatBufferBuilder builder, Iterable<MetaDataSnapshot.Actor> actors) {
        for (MetaDataSnapshot.Actor actor : actors) {
            Actor.createActor(builder, actor.uid, actor.active, (float) actor.position.x, (float) actor.position.y);
        }
        return builder.endVector();
    }

    private int bildAgentVector(FlatBufferBuilder builder, Iterable<MetaDataSnapshot.Agent> agents) {
        for (MetaDataSnapshot.Agent agent : agents) {
            Agent.createAgent(builder, agent.uid, agent.tid, agent.active, (float) agent.position.x, (float) agent.position.y, (float) agent.velocity.x, (float) agent.velocity.y);
        }
        return builder.endVector();
    }
}
