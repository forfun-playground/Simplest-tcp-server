package forfun.sandbox.uwns.bothub.network.handler;

import forfun.sandbox.uwns.bothub.bot.Client;
import forfun.sandbox.uwns.bothub.network.NetworkConnector;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelOnInactiveHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext chc) {
        if (hasClient(chc)) {
            detachClient(chc);
        }
        chc.fireChannelInactive();
    }

    private boolean hasClient(ChannelHandlerContext chc) {
        return chc.channel().hasAttr(NetworkConnector.REFERENCE);
    }

    private void detachClient(ChannelHandlerContext chc) {
        Client Client = chc.channel().attr(NetworkConnector.REFERENCE).getAndSet(null);
        Client.onDisconnect();
    }
}
