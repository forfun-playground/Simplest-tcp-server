package forfun.sandbox.uwns.bothub.network.handler;

import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import forfun.sandbox.uwns.shared.pack.proto.Actor;
import forfun.sandbox.uwns.shared.pack.proto.Agent;
import forfun.sandbox.uwns.shared.pack.proto.Message;
import forfun.sandbox.uwns.shared.pack.proto.SnapshotResponse;
import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtoSnapshotResponseHandler extends ChannelInboundHandlerBase<Message> {

    @Override
    public boolean acceptInboundMessage(Object obj) {
        return (obj instanceof Message) ? ((Message) obj).hasSnapshotResponse() : false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Message message) throws Exception {
        MetaDataSnapshot metadata = builMetaDataSnapshot(message.getSnapshotResponse());
        sendMetaDataToClient(chc, metadata);
    }

    private MetaDataSnapshot builMetaDataSnapshot(SnapshotResponse snapshot) {
        MetaDataSnapshot metadata = new MetaDataSnapshot();
        metadata.radius = snapshot.getRadius();
        metadata.targets = actorsListBuilder(metadata, snapshot.getTargetsList());
        metadata.players = agentsListBuilder(metadata, snapshot.getPlayersList());
        metadata.nonplayers = agentsListBuilder(metadata, snapshot.getNonPlayersList());
        return metadata;
    }

    private List<MetaDataSnapshot.Actor> actorsListBuilder(MetaDataSnapshot metadata, List<Actor> actors) {
        List<MetaDataSnapshot.Actor> metaactors = new ArrayList<>();
        for (Actor actor : actors) {
            MetaDataSnapshot.Actor metaactor = metadata.new Actor(
                    actor.getUid(),
                    actor.getActive(),
                    metadata.new Vector(actor.getPosition().getX(), actor.getPosition().getY())
            );
            metaactors.add(metaactor);
        }
        return metaactors;
    }

    private List<MetaDataSnapshot.Agent> agentsListBuilder(MetaDataSnapshot metadata, List<Agent> agents) {
        List<MetaDataSnapshot.Agent> metaagents = new ArrayList<>();
        for (Agent agent : agents) {
            MetaDataSnapshot.Agent metaagent = metadata.new Agent(
                    agent.getUid(),
                    agent.getTargetId(),
                    agent.getActive(),
                    metadata.new Vector(agent.getPosition().getX(), agent.getPosition().getY()),
                    metadata.new Vector(agent.getVelocity().getX(), agent.getVelocity().getY())
            );
            metaagents.add(metaagent);
        }
        return metaagents;
    }
}
