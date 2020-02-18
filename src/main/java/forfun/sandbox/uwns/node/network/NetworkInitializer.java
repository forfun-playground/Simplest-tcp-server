package forfun.sandbox.uwns.node.network;

import com.google.inject.Inject;
import forfun.sandbox.uwns.node.network.handler.ChannelOnActiveHandler;
import forfun.sandbox.uwns.node.network.handler.ChannelOnExceptionHandler;
import forfun.sandbox.uwns.node.network.handler.ChannelOnInactiveHandler;
import forfun.sandbox.uwns.node.network.handler.ChannelUpgradeHandler;
import forfun.sandbox.uwns.node.world.World;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetworkInitializer extends ChannelInitializer<SocketChannel> {

    private final World world;
    private final PlayerConnectorFactory playerConnectorFactory;

    @Inject
    public NetworkInitializer(
            World world,
            PlayerConnectorFactory playerConnectorFactory
    ) {
        super();
        this.world = world;
        this.playerConnectorFactory = playerConnectorFactory;
    }

    @Override
    protected void initChannel(SocketChannel sh) throws Exception {
        ChannelPipeline pipeline = sh.pipeline();
        pipeline.addLast("FrameEncoder", new LengthFieldPrepender(2, true));
        pipeline.addLast("FrameDecoder", new LengthFieldBasedFrameDecoder(0x4000, 0, 2, -2, 2));
        pipeline.addLast("UpgradeHandler", new ChannelUpgradeHandler());
        pipeline.addLast("ChannelActiveHandler", new ChannelOnActiveHandler(world, playerConnectorFactory));
        pipeline.addLast("ChannelInactiveHandler", new ChannelOnInactiveHandler());
        pipeline.addLast("ExceptionHandler", new ChannelOnExceptionHandler());
    }
}
