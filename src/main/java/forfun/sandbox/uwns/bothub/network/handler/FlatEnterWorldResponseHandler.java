package forfun.sandbox.uwns.bothub.network.handler;

import forfun.sandbox.uwns.shared.meta.MetaDataEnterWorld;
import forfun.sandbox.uwns.shared.pack.flat.EnterWorldResponse;
import forfun.sandbox.uwns.shared.pack.flat.Message;
import forfun.sandbox.uwns.shared.pack.flat.Packet;
import io.netty.channel.ChannelHandlerContext;

public class FlatEnterWorldResponseHandler extends ChannelInboundHandlerBase<Packet> {

    @Override
    public boolean acceptInboundMessage(Object obj) {
        return (obj instanceof Packet) ? ((Packet) obj).dataType() == Message.EnterWorldResponse : false;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Packet packet) throws Exception {
        int uid = extractActorUid(packet);
        MetaDataEnterWorld metadata = buildMetaDataEnterWorld(uid);
        sendMetaDataToClient(chc, metadata);
    }

    private int extractActorUid(Packet packet) {
        EnterWorldResponse response = (EnterWorldResponse) packet.data(new EnterWorldResponse());
        return response.uid();
    }

    private MetaDataEnterWorld buildMetaDataEnterWorld(int uid) {
        MetaDataEnterWorld metadata = new MetaDataEnterWorld();
        metadata.uid = uid;
        return metadata;
    }


}
