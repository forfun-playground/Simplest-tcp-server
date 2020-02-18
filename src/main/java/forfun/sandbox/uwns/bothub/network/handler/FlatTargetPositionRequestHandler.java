package forfun.sandbox.uwns.bothub.network.handler;

import com.google.flatbuffers.FlatBufferBuilder;
import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataTarget;
import forfun.sandbox.uwns.shared.pack.flat.Message;
import forfun.sandbox.uwns.shared.pack.flat.Packet;
import forfun.sandbox.uwns.shared.pack.flat.TargetPositionRequest;
import forfun.sandbox.uwns.shared.pack.flat.Vector;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

public class FlatTargetPositionRequestHandler extends MessageToMessageEncoder<Object> {

    @Override
    public boolean acceptOutboundMessage(Object obj) {
        return (obj instanceof MetaData) ? ((MetaData) obj) instanceof MetaDataTarget : false;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object in, List<Object> out) throws Exception {
        MetaDataTarget meta = (MetaDataTarget) in;
        FlatBufferBuilder builder = new FlatBufferBuilder(0);
        int positionOffset = Vector.createVector(builder, (float) meta.position.x, (float) meta.position.y);
        TargetPositionRequest.startTargetPositionRequest(builder);
        TargetPositionRequest.addPosition(builder, positionOffset);
        int response = TargetPositionRequest.endTargetPositionRequest(builder);
        int packet = Packet.createPacket(builder, Message.TargetPositionRequest, response);
        builder.finish(packet);
        chc.writeAndFlush(builder);
    }
}
