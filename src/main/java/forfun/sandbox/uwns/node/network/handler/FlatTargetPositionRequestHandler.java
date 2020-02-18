package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.shared.meta.MetaDataTarget;
import forfun.sandbox.uwns.shared.pack.flat.Message;
import forfun.sandbox.uwns.shared.pack.flat.Packet;
import forfun.sandbox.uwns.shared.pack.flat.TargetPositionRequest;
import forfun.sandbox.uwns.shared.pack.flat.Vector;
import io.netty.channel.ChannelHandlerContext;

public class FlatTargetPositionRequestHandler extends ChannelInboundHandlerBase<Packet> {

    @Override
    public boolean acceptInboundMessage(Object obj) {
        return (obj instanceof Packet) ? ((Packet) obj).dataType() == Message.TargetPositionRequest : false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Packet packet) throws Exception {
        Vector targetPosition = extractTargetPosition(packet);
        MetaDataTarget metatarget = buildMetadataTarget(targetPosition);
        sendMetadataToPlayadleActor(chc, metatarget);
    }

    private Vector extractTargetPosition(Packet packet) {
        TargetPositionRequest targetPositionRequest = (TargetPositionRequest) packet.data(new TargetPositionRequest());
        return targetPositionRequest.position();
    }

    private MetaDataTarget buildMetadataTarget(Vector position) {
        return new MetaDataTarget(
                position.x(),
                position.y()
        );
    }

}
