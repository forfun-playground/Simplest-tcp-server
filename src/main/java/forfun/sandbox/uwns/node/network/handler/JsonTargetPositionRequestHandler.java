package forfun.sandbox.uwns.node.network.handler;

import com.google.gson.Gson;
import forfun.sandbox.uwns.shared.meta.MetaDataTarget;
import forfun.sandbox.uwns.shared.pack.json.Packet;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonTargetPositionRequestHandler extends ChannelInboundHandlerBase<Packet> {

    private final Gson gson = new Gson();

    @Override
    public boolean acceptInboundMessage(Object obj) {
        return (obj instanceof Packet) ? ((Packet) obj).type == Packet.MessageType.TargetPositionRequest : false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Packet packet) throws Exception {
        MetaDataTarget metatarget = gson.fromJson(packet.message, MetaDataTarget.class);
        sendMetadataToPlayadleActor(chc, metatarget);
    }

}
