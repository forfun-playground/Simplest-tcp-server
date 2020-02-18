package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.shared.codec.FlatbufferDecoder;
import forfun.sandbox.uwns.shared.codec.FlatbufferEncoder;
import forfun.sandbox.uwns.shared.pack.Packtype;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlatUpgradeContractor extends ChannelUpgradeContractorBase {

    @Override
    public boolean isAccept(Packtype packtype) {
        return packtype == Packtype.Flatbuffer;
    }

    @Override
    public void process(ChannelPipeline pipeline) {
        log.debug("Upgrade packtype: " + Packtype.Flatbuffer.name());
        pipeline.addBefore("UpgradeHandler", "FlatbufferEncoder", new FlatbufferEncoder());
        pipeline.addBefore("UpgradeHandler", "FlatbufferDecoder", new FlatbufferDecoder());
        pipeline.addAfter("UpgradeHandler", "EnterWorldResponseHandler", new FlatEnterWorldResponseHandler());
        pipeline.addAfter("UpgradeHandler", "SnapshotResponseHandler", new FlatSnapshotResponseHandler());
        pipeline.addAfter("UpgradeHandler", "TargetPositionRequestHandler", new FlatTargetPositionRequestHandler());
    }
}
