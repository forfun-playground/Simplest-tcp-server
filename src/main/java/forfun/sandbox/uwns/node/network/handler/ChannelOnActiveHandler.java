package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.node.network.PlayerConnector;
import forfun.sandbox.uwns.node.network.PlayerConnectorFactory;
import forfun.sandbox.uwns.node.world.Playable;
import forfun.sandbox.uwns.node.world.World;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelOnActiveHandler extends ChannelInboundHandlerAdapter {

    private final World world;
    private final PlayerConnectorFactory playerConnectorFactory;

    public ChannelOnActiveHandler(
            World world,
            PlayerConnectorFactory playerConnectorFactory
    ) {
        super();
        this.world = world;
        this.playerConnectorFactory = playerConnectorFactory;
    }

    @Override
    public void channelActive(ChannelHandlerContext context) {
        log.debug("On Channel Active");
        PlayerConnector connector = playerConnectorFactory.getInstance(context);
        Playable playable = world.joinNewPlayer(connector);
        attachPlayableToChannel(context, playable);
        context.fireChannelActive();
    }

    private void attachPlayableToChannel(ChannelHandlerContext context, Playable playable) {
        if (!channelHasPlayer(context)) {
            attachPlayable(context, playable);
        } else {
            log.warn("Client is already attached");
        }
    }

    private boolean channelHasPlayer(ChannelHandlerContext context) {
        return context.channel().hasAttr(PlayerConnector.REFERENCE);
    }

    private void attachPlayable(ChannelHandlerContext context, Playable playable) {
        context.channel().attr(PlayerConnector.REFERENCE).set(playable);
        playable.onConnect();
    }
}
