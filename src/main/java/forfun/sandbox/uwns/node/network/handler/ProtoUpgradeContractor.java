package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.shared.pack.Packtype;
import forfun.sandbox.uwns.shared.pack.proto.Message;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProtoUpgradeContractor extends ChannelUpgradeContractorBase {

    @Override
    public boolean isAccept(Packtype packtype) {
        return packtype == Packtype.Protobuf;
    }

    @Override
    public void process(ChannelPipeline pipeline) {
        log.debug("Upgrade packtype: " + Packtype.Protobuf.name());
        pipeline.addBefore("UpgradeHandler", "ProtobufEncoder", new ProtobufEncoder());
        pipeline.addBefore("UpgradeHandler", "ProtobufDecoder", new ProtobufDecoder(Message.getDefaultInstance()));
        pipeline.addAfter("UpgradeHandler", "EnterWorldResponseHandler", new ProtoEnterWorldResponseHandler());
        pipeline.addAfter("UpgradeHandler", "SnapshotResponseHandler", new ProtoSnapshotResponseHandler());
        pipeline.addAfter("UpgradeHandler", "TargetPositionRequestHandler", new ProtoTargetPositionRequestHandler());
    }
}
