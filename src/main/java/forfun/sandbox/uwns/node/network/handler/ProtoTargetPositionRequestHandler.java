package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.shared.meta.MetaDataTarget;
import forfun.sandbox.uwns.shared.pack.proto.Message;
import forfun.sandbox.uwns.shared.pack.proto.TargetPositionRequest;
import forfun.sandbox.uwns.shared.pack.proto.Vector;
import io.netty.channel.ChannelHandlerContext;

public class ProtoTargetPositionRequestHandler extends ChannelInboundHandlerBase<Message>{

    @Override
    public boolean acceptInboundMessage(Object obj) {
        return (obj instanceof Message) ? ((Message) obj).hasTargetPositionRequest() : false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Message message) throws Exception {
        Vector targetPosition = extractTargetPosition(message);
        MetaDataTarget metatarget = buildMetadataTarget(targetPosition);
        sendMetadataToPlayadleActor(chc, metatarget);
    }

    private Vector extractTargetPosition(Message message) {
        TargetPositionRequest targetPositionRequest = message.getTargetPositionRequest();
        return targetPositionRequest.getPosition();
    }

    private MetaDataTarget buildMetadataTarget(Vector position) {
       return new MetaDataTarget(
                position.getX(),
                position.getY()
        );        
    }


}
