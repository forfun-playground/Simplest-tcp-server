package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.node.network.PlayerConnector;
import forfun.sandbox.uwns.node.world.Playable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelOnInactiveHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.debug("On Channel Inactive");
        if (ctx.channel().hasAttr(PlayerConnector.REFERENCE)) {
            Playable playable = ctx.channel().attr(PlayerConnector.REFERENCE).getAndSet(null);
            playable.onDisconnect();
        }
        ctx.fireChannelInactive();
    }

}
