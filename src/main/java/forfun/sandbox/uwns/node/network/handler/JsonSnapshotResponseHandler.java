package forfun.sandbox.uwns.node.network.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataSnapshot;
import forfun.sandbox.uwns.shared.pack.json.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

public class JsonSnapshotResponseHandler extends MessageToMessageEncoder<Object> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public boolean acceptOutboundMessage(Object obj) {
        return (obj instanceof MetaData) ? ((MetaData) obj) instanceof MetaDataSnapshot : false;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object in, List<Object> out) throws Exception {
        MetaDataSnapshot meta = (MetaDataSnapshot) in;
        Packet packet = new Packet();
        packet.type = Packet.MessageType.SnapshotResponse;
        packet.message = gson.toJson(meta);
        chc.writeAndFlush(packet);
    }
}
