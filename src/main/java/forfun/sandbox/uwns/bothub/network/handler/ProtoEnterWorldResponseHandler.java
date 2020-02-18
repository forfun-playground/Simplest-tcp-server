package forfun.sandbox.uwns.bothub.network.handler;

import forfun.sandbox.uwns.shared.meta.MetaDataEnterWorld;
import forfun.sandbox.uwns.shared.pack.proto.Message;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtoEnterWorldResponseHandler extends ChannelInboundHandlerBase<Object> {

    @Override
    public boolean acceptInboundMessage(Object obj) {
        return (obj instanceof Message) ? ((Message) obj).hasEnterWorldResponse() : false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Object obj) throws Exception {
        int uid = extractActorUid((Message) obj);
        MetaDataEnterWorld metadata = buildMetaDataEnterWorld(uid);
        sendMetaDataToClient(chc, metadata);
    }

    private int extractActorUid(Message message) {
        return message.getEnterWorldResponse().getUid();
    }

    private MetaDataEnterWorld buildMetaDataEnterWorld(int uid) {
        MetaDataEnterWorld metadata = new MetaDataEnterWorld();
        metadata.uid = uid;
        return metadata;
    }

}
