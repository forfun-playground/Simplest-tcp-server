package forfun.sandbox.uwns.node.network.handler;

import com.google.flatbuffers.FlatBufferBuilder;
import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataEnterWorld;
import forfun.sandbox.uwns.shared.pack.flat.EnterWorldResponse;
import forfun.sandbox.uwns.shared.pack.flat.Message;
import forfun.sandbox.uwns.shared.pack.flat.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlatEnterWorldResponseHandler extends MessageToMessageEncoder<Object> {

    @Override
    public boolean acceptOutboundMessage(Object obj) {
        return (obj instanceof MetaData) ? ((MetaData) obj) instanceof MetaDataEnterWorld : false;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object in, List<Object> out) throws Exception {
        MetaDataEnterWorld meta = (MetaDataEnterWorld) in;
        log.debug("Proto Enter World Response Handler UID: " + meta.uid);
        FlatBufferBuilder builder = new FlatBufferBuilder(0);
        int response = EnterWorldResponse.createEnterWorldResponse(builder, meta.uid);
        int packet = Packet.createPacket(builder, Message.EnterWorldResponse, response);
        builder.finish(packet);
        chc.writeAndFlush(builder);
    }
}
