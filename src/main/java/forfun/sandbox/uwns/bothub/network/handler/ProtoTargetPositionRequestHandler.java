package forfun.sandbox.uwns.bothub.network.handler;

import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataTarget;
import forfun.sandbox.uwns.shared.pack.proto.Message;
import forfun.sandbox.uwns.shared.pack.proto.TargetPositionRequest;
import forfun.sandbox.uwns.shared.pack.proto.Vector;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

public class ProtoTargetPositionRequestHandler extends MessageToMessageEncoder<Object> {

    @Override
    public boolean acceptOutboundMessage(Object obj) {
        return (obj instanceof MetaData) ? ((MetaData) obj) instanceof MetaDataTarget : false;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object in, List<Object> out) throws Exception {
        MetaDataTarget metatarget = (MetaDataTarget) in;
        TargetPositionRequest.Builder request = TargetPositionRequest.newBuilder();
        Vector targetPosition = buildVector(metatarget.position.x, metatarget.position.y);
        request.setPosition(targetPosition);
        Message.Builder message = Message.newBuilder().setTargetPositionRequest(request.build());
        out.add(message.build());
    }

    private Vector buildVector(double x, double y) {
        Vector.Builder vector = Vector.newBuilder();
        vector.setX((float) x);
        vector.setY((float) y);
        return vector.build();
    }

}
