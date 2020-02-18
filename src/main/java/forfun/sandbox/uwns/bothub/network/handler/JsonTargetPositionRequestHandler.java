package forfun.sandbox.uwns.bothub.network.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forfun.sandbox.uwns.shared.meta.MetaData;
import forfun.sandbox.uwns.shared.meta.MetaDataTarget;
import forfun.sandbox.uwns.shared.pack.json.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonTargetPositionRequestHandler extends MessageToMessageEncoder<Object> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public boolean acceptOutboundMessage(Object obj) {
        return (obj instanceof MetaData) ? ((MetaData) obj) instanceof MetaDataTarget : false;
    }

    @Override
    protected void encode(ChannelHandlerContext chc, Object in, List<Object> out) throws Exception {
        MetaDataTarget meta = (MetaDataTarget) in;
        Packet packet = new Packet();
        packet.type = Packet.MessageType.TargetPositionRequest;
        packet.message = gson.toJson(meta);
        chc.writeAndFlush(packet);
    }

}
