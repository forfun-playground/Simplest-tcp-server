package forfun.sandbox.uwns.node.network;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.NodeConfigFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetworkBootstrap {

    private final NetworkInitializer initializer;
    private final ConfigNetworkModule config;

    @Inject
    public NetworkBootstrap(NetworkInitializer initializer, NodeConfigFactory configFactory) {
        this.initializer = initializer;
        this.config = configFactory.getConfig();
    }

    public void start() throws Exception {
        EventLoopGroup servergroup = new NioEventLoopGroup(1);
        EventLoopGroup workergroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(servergroup, workergroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.TRACE))
                    .childHandler(initializer);
            int nodePort = config.TcpPort();
            bootstrap.bind(nodePort).sync().channel().closeFuture().sync();
        } finally {
            servergroup.shutdownGracefully();
            workergroup.shutdownGracefully();
        }
    }
}
