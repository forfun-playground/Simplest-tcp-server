package forfun.sandbox.uwns.bothub.network.handler;

import forfun.sandbox.uwns.bothub.bot.Client;
import forfun.sandbox.uwns.bothub.network.NetworkConnector;
import forfun.sandbox.uwns.shared.meta.MetaData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class ChannelInboundHandlerBase<T> extends SimpleChannelInboundHandler<T> {
    
    protected void sendMetaDataToClient(ChannelHandlerContext chc, MetaData metadata) {
        Client client = chc.channel().attr(NetworkConnector.REFERENCE).get();
        if (client != null) {
            client.acceptMessage(metadata);
        } else {
            throw new NullPointerException("Playable reference is missing");
        }
    }
    
}
