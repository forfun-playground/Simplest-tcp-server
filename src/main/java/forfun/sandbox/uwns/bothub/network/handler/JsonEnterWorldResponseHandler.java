package forfun.sandbox.uwns.bothub.network.handler;

import com.google.gson.Gson;
import forfun.sandbox.uwns.shared.meta.MetaDataEnterWorld;
import forfun.sandbox.uwns.shared.pack.json.Packet;
import io.netty.channel.ChannelHandlerContext;

public class JsonEnterWorldResponseHandler extends ChannelInboundHandlerBase<Packet> {

    private final Gson gson = new Gson();

    @Override
    public boolean acceptInboundMessage(Object obj) {
        return (obj instanceof Packet) ? ((Packet) obj).type == Packet.MessageType.EnterWorldResponse : false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Packet packet) throws Exception {
        MetaDataEnterWorld metadata = gson.fromJson(packet.message, MetaDataEnterWorld.class);
        sendMetaDataToClient(chc, metadata);
    }
}
