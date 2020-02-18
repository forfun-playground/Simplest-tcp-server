package forfun.sandbox.uwns.bothub.network.handler;

import forfun.sandbox.uwns.bothub.bot.BotFactory;
import forfun.sandbox.uwns.bothub.bot.Client;
import forfun.sandbox.uwns.bothub.network.NetworkConnector;
import forfun.sandbox.uwns.bothub.network.NetworkConnectorImpl;
import forfun.sandbox.uwns.shared.pack.Packtype;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelOnActiveHandler extends ChannelInboundHandlerAdapter {

    private final BotFactory botFactory;
    private final Packtype packtype;

    public ChannelOnActiveHandler(
            Packtype packtype,
            BotFactory botFactory
    ) {
        super();
        this.packtype = packtype;
        this.botFactory = botFactory;
    }

    @Override
    public void channelActive(ChannelHandlerContext chc) {
        NetworkConnector connector = new NetworkConnectorImpl(chc);
        Client client = botFactory.getInstance(connector);
        attachClientToChannelIsAbsent(chc, client);
        sendPacktypeSelectMessage(chc);
        chc.fireChannelActive();
    }

    private void attachClientToChannelIsAbsent(ChannelHandlerContext chc, Client client) {
        if (channelHasClient(chc)) {
            log.warn("Client is already attached");
        } else {
            attachClient(chc, client);
            client.onConnect();
        }
    }

    private boolean channelHasClient(ChannelHandlerContext chc) {
        return chc.channel().hasAttr(NetworkConnector.REFERENCE);
    }

    private void attachClient(ChannelHandlerContext chc, Client client) {
        chc.channel().attr(NetworkConnector.REFERENCE).set(client);
    }

    private void sendPacktypeSelectMessage(ChannelHandlerContext chc) {
        String packtypeName = packtype.toString();
        byte[] bytearay = packtypeName.getBytes(Charset.forName("UTF-8"));
        final ByteBuf buffer = chc.alloc().heapBuffer(bytearay.length, bytearay.length);
        buffer.writeBytes(bytearay);
        chc.writeAndFlush(buffer);
    }

}
