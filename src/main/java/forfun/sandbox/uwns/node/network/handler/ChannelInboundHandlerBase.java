package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.node.network.PlayerConnector;
import forfun.sandbox.uwns.node.world.Playable;
import forfun.sandbox.uwns.shared.meta.MetaData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class ChannelInboundHandlerBase<T> extends SimpleChannelInboundHandler<T> {
    
    protected void sendMetadataToPlayadleActor(ChannelHandlerContext chc, MetaData metadata) {
        Playable playable = chc.channel().attr(PlayerConnector.REFERENCE).get();
        if (playable != null) {
            playable.acceptMessage(metadata);
        } else {
            throw new NullPointerException("Playable reference is missing");
        }
    }
    
}
