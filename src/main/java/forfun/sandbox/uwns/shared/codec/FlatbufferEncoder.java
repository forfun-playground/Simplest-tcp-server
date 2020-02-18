package forfun.sandbox.uwns.shared.codec;

import com.google.flatbuffers.FlatBufferBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

public class FlatbufferEncoder extends MessageToMessageEncoder<FlatBufferBuilder> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FlatBufferBuilder builder, List<Object> out) throws Exception {
        byte[] buf = builder.sizedByteArray();
        final ByteBuf buffer = ctx.alloc().heapBuffer(buf.length, buf.length);
        buffer.writeBytes(buf);
        out.add(buffer);
    }

}
