package forfun.sandbox.uwns.bothub.network;

import forfun.sandbox.uwns.shared.meta.MetaData;
import io.netty.channel.ChannelHandlerContext;

public class NetworkConnectorImpl implements NetworkConnector {

    private final ChannelHandlerContext channelHandlerContext;

    public NetworkConnectorImpl(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void sendMessage(MetaData metadata) {
        channelHandlerContext.writeAndFlush(metadata);
    }

}
