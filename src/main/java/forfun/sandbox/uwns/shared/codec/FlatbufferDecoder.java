package forfun.sandbox.uwns.shared.codec;

import forfun.sandbox.uwns.shared.pack.flat.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

public class FlatbufferDecoder extends MessageToMessageDecoder<ByteBuf> {
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        Packet packet = Packet.getRootAsPacket(msg.nioBuffer());
        out.add(packet);
    }

}
