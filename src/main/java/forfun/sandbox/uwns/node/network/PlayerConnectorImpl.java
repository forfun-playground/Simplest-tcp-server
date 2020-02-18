package forfun.sandbox.uwns.node.network;

import forfun.sandbox.uwns.shared.meta.MetaData;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerConnectorImpl implements PlayerConnector {

    private final ChannelHandlerContext channelHandlerContext;

    public PlayerConnectorImpl(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void sendMessage(MetaData msg) {
        channelHandlerContext.writeAndFlush(msg);
    }
}
