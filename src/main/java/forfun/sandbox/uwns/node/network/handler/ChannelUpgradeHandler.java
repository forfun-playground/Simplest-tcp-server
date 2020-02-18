package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.node.network.PlayerConnector;
import forfun.sandbox.uwns.shared.pack.Packtype;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelUpgradeHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final ChannelUpgradeContractor contract;

    public ChannelUpgradeHandler() {
        super();
        contract = new ProtoUpgradeContractor()
                .setNext(new FlatUpgradeContractor())
                .setNext(new JsonUpgradeContractor())
                .end();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, ByteBuf in) throws Exception {
        String packtype = in.toString(Charset.forName("UTF-8"));
        log.debug("Upgrade packtype request: " + packtype);
        ChannelPipeline pipeline = chc.pipeline();
        Packtype pack = Packtype.valueOf(packtype);
        contract.handle(pipeline, pack);
        pipeline.remove("UpgradeHandler");
        chc.channel().attr(PlayerConnector.REFERENCE).get().onReady();
    }

}
