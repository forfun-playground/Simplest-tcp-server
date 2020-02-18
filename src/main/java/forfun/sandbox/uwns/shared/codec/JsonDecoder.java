package forfun.sandbox.uwns.shared.codec;

import com.google.gson.Gson;
import forfun.sandbox.uwns.shared.pack.json.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final Gson gson = new Gson();

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf buf, List<Object> out) throws Exception {
        String json = buf.toString(CharsetUtil.UTF_8);
        Packet packet = gson.fromJson(json, Packet.class);
        out.add(packet);
    }
}
