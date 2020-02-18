package forfun.sandbox.uwns.bothub.network;

import forfun.sandbox.uwns.bothub.BothubConfig;
import forfun.sandbox.uwns.bothub.BothubConfigFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetworkBootstrap {

    private final NetworkInitializer initializer;
    private final BothubConfig config;
    private Bootstrap bootstrap;

    public NetworkBootstrap(NetworkInitializer initializer, BothubConfigFactory configFactory) {
        super();
        this.initializer = initializer;
        this.config = configFactory.getConfig();
    }

    public void init() throws Exception {
        EventLoopGroup loopgroup = new NioEventLoopGroup(4);
        bootstrap = new Bootstrap();
        bootstrap.group(loopgroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .handler(initializer);
    }

    public ChannelFuture connnect(String host, int port) throws Exception {
        return bootstrap.connect(host, port).sync();
    }
}
