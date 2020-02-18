package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.shared.pack.Packtype;
import io.netty.channel.ChannelPipeline;

public interface ChannelUpgradeContractor {

    void setPrev(ChannelUpgradeContractor contractor);

    ChannelUpgradeContractor setNext(ChannelUpgradeContractor contractor);

    ChannelUpgradeContractor end();

    void handle(ChannelPipeline pipeline, Packtype packtype);

    boolean isAccept(Packtype packtype);

    void process(ChannelPipeline pipeline);
}
