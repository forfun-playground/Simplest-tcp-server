package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.shared.codec.JsonDecoder;
import forfun.sandbox.uwns.shared.codec.JsonEncoder;
import forfun.sandbox.uwns.shared.pack.Packtype;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUpgradeContractor extends ChannelUpgradeContractorBase {

    @Override
    public boolean isAccept(Packtype packtype) {
        return packtype == Packtype.Json;
    }

    @Override
    public void process(ChannelPipeline pipeline) {
        log.debug("Upgrade packtype: " + Packtype.Json.name());
        pipeline.addBefore("UpgradeHandler", "JsonEncoder", new JsonEncoder());
        pipeline.addBefore("UpgradeHandler", "JsonDecoder", new JsonDecoder());
        pipeline.addAfter("UpgradeHandler", "EnterWorldResponseHandler", new JsonEnterWorldResponseHandler());
        pipeline.addAfter("UpgradeHandler", "SnapshotResponseHandler", new JsonSnapshotResponseHandler());
        pipeline.addAfter("UpgradeHandler", "TargetPositionRequestHandler", new JsonTargetPositionRequestHandler());
    }
    
}
