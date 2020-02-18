package forfun.sandbox.uwns.node.network.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataEnterWorld;
import forfun.sandbox.uwns.shared.pack.json.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

public class JsonEnterWorldResponseHandler extends MessageToMessageEncoder<Object> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public boolean acceptOutboundMessage(Object obj) {
        return (obj instanceof MetaData) ? ((MetaData) obj) instanceof MetaDataEnterWorld : false;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object in, List<Object> out) throws Exception {
        MetaDataEnterWorld meta = (MetaDataEnterWorld) in;
        Packet packet = new Packet();
        packet.type = Packet.MessageType.EnterWorldResponse;
        packet.message = gson.toJson(meta);
        chc.writeAndFlush(packet);
    }
}
