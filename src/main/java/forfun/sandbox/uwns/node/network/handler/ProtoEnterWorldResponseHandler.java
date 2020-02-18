package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataEnterWorld;
import forfun.sandbox.uwns.shared.pack.proto.EnterWorldResponse;
import forfun.sandbox.uwns.shared.pack.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtoEnterWorldResponseHandler extends MessageToMessageEncoder<Object> {

    @Override
    public boolean acceptOutboundMessage(Object obj) {
        return (obj instanceof MetaData) ? ((MetaData) obj) instanceof MetaDataEnterWorld : false;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object in, List<Object> out) throws Exception {
        log.debug("Proto Enter World Response Handler UID: " + ((MetaDataEnterWorld) in).uid);
        MetaDataEnterWorld meta = (MetaDataEnterWorld) in;
        EnterWorldResponse.Builder response = EnterWorldResponse.newBuilder();
        response.setUid(meta.uid);
        Message.Builder message = Message.newBuilder().setEnterWorldResponse(response.build());
        out.add(message.build());
    }
}
