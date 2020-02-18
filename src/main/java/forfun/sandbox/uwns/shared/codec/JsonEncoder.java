package forfun.sandbox.uwns.shared.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import forfun.sandbox.uwns.shared.pack.json.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonEncoder extends MessageToMessageEncoder<Packet> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void encode(ChannelHandlerContext chc, Packet packet, List<Object> out) throws Exception {
        String json = gson.toJson(packet);
        ByteBuf buffer = Unpooled.copiedBuffer(json, CharsetUtil.UTF_8);
        out.add(buffer);
    }
}
