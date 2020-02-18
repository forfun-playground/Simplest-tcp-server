package forfun.sandbox.uwns.node.network.handler;

import forfun.sandbox.uwns.shared.pack.Packtype;
import io.netty.channel.ChannelPipeline;

public abstract class ChannelUpgradeContractorBase implements ChannelUpgradeContractor {

    private ChannelUpgradeContractor previousContractor;
    private ChannelUpgradeContractor nextContractor;

    @Override
    public void setPrev(ChannelUpgradeContractor contractor) {
        this.previousContractor = contractor;
    }

    @Override
    public ChannelUpgradeContractor setNext(ChannelUpgradeContractor contractor) {
        contractor.setPrev(this);
        this.nextContractor = contractor;
        return contractor;
    }

    @Override
    public ChannelUpgradeContractor end() {
        return previousContractor != null ? previousContractor.end() : this;
    }

    @Override
    public void handle(ChannelPipeline pipeline, Packtype packtype) {
        if (isAccept(packtype)) {
            process(pipeline);
            return;
        } else if (hasNext()) {
            nextContractor.handle(pipeline, packtype);
            return;
        }
        throw new UnsupportedOperationException("No suitable contractor found for the packtype");
    }

    private boolean hasNext() {
        return nextContractor != null;
    }

    @Override
    public abstract boolean isAccept(Packtype packtype);

    @Override
    public abstract void process(ChannelPipeline pipeline);
}
