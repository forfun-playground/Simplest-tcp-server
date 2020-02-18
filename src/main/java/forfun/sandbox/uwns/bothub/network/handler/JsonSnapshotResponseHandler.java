package forfun.sandbox.uwns.bothub.network.handler;

import com.google.gson.Gson;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import forfun.sandbox.uwns.shared.pack.json.Packet;
import io.netty.channel.ChannelHandlerContext;

public class JsonSnapshotResponseHandler extends ChannelInboundHandlerBase<Packet> {

    private final Gson gson = new Gson();

    @Override
    public boolean acceptInboundMessage(Object obj) {
        return (obj instanceof Packet) ? ((Packet) obj).type == Packet.MessageType.SnapshotResponse : false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Packet packet) throws Exception {
        MetaDataSnapshot metadata =  gson.fromJson(packet.message, MetaDataSnapshot.class);
        sendMetaDataToClient(chc, metadata);
    }
}
