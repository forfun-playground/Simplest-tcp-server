package forfun.sandbox.uwns.bothub.network.handler;

import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import forfun.sandbox.uwns.shared.pack.flat.Actor;
import forfun.sandbox.uwns.shared.pack.flat.Agent;
import forfun.sandbox.uwns.shared.pack.flat.Message;
import forfun.sandbox.uwns.shared.pack.flat.Packet;
import forfun.sandbox.uwns.shared.pack.flat.SnapshotResponse;
import forfun.sandbox.uwns.shared.pack.flat.Vector;
import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import java.util.List;

public class FlatSnapshotResponseHandler extends ChannelInboundHandlerBase<Packet> {

    @Override
    public boolean acceptInboundMessage(Object obj) {
        return (obj instanceof Packet) ? ((Packet) obj).dataType() == Message.SnapshotResponse : false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Packet packet) throws Exception {
        SnapshotResponse response = (SnapshotResponse) packet.data(new SnapshotResponse());
        MetaDataSnapshot metadata = builMetaDataSnapshot(response);
        sendMetaDataToClient(chc, metadata);
    }

    private MetaDataSnapshot builMetaDataSnapshot(SnapshotResponse snapshot) {
        MetaDataSnapshot metadata = new MetaDataSnapshot();
        return new MetaDataSnapshot(
                snapshot.radius(),
                targetListBuilder(metadata, snapshot),
                playerListBuilder(metadata, snapshot),
                nonplayerListBuilder(metadata, snapshot)
        );
    }

    private List<MetaDataSnapshot.Actor> targetListBuilder(MetaDataSnapshot metadata, SnapshotResponse snapshot) {
        List<MetaDataSnapshot.Actor> metaactors = new ArrayList<>();
        for (int i = 0; i < snapshot.targetsLength(); i++) {
            MetaDataSnapshot.Actor metaactor = actorBuilder(metadata, snapshot.targets(i));
            metaactors.add(metaactor);
        }
        return metaactors;
    }

    private MetaDataSnapshot.Actor actorBuilder(MetaDataSnapshot metadata, Actor actor) {
        return metadata.new Actor(
                actor.uid(),
                actor.active(),
                toMetaVector(metadata, actor.position())
        );
    }

    private List<MetaDataSnapshot.Agent> playerListBuilder(MetaDataSnapshot metadata, SnapshotResponse snapshot) {
        List<MetaDataSnapshot.Agent> metaagents = new ArrayList<>();
        for (int i = 0; i < snapshot.playersLength(); i++) {
            MetaDataSnapshot.Agent metaagent = agentBuilder(metadata, snapshot.players(i));
            metaagents.add(metaagent);
        }
        return metaagents;
    }

    private List<MetaDataSnapshot.Agent> nonplayerListBuilder(MetaDataSnapshot metadata, SnapshotResponse snapshot) {
        List<MetaDataSnapshot.Agent> metaagents = new ArrayList<>();
        for (int i = 0; i < snapshot.nonPlayersLength(); i++) {
            MetaDataSnapshot.Agent metaagent = agentBuilder(metadata, snapshot.nonPlayers(i));
            metaagents.add(metaagent);
        }
        return metaagents;
    }

    private MetaDataSnapshot.Agent agentBuilder(MetaDataSnapshot metadata, Agent agent) {
        return metadata.new Agent(
                agent.uid(),
                agent.tid(),
                agent.active(),
                toMetaVector(metadata, agent.position()),
                toMetaVector(metadata, agent.velocity())
        );
    }

    private MetaData.Vector toMetaVector(MetaData meta, Vector vector) {
        return meta.new Vector(vector.x(), vector.y());
    }
}
