package forfun.sandbox.uwns.node.network;

import io.netty.channel.ChannelHandlerContext;

public class PlayerConnectorFactoryImpl implements PlayerConnectorFactory {

    @Override
    public PlayerConnector getInstance(ChannelHandlerContext channelHandlerContext) {
        return new PlayerConnectorImpl(channelHandlerContext);
    }

}
