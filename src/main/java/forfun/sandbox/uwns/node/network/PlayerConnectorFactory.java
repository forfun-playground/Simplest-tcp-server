package forfun.sandbox.uwns.node.network;

import io.netty.channel.ChannelHandlerContext;

public interface PlayerConnectorFactory {
    
    PlayerConnector getInstance(ChannelHandlerContext channelHandlerContext);
    
}
