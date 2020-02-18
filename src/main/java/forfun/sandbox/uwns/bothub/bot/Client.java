package forfun.sandbox.uwns.bothub.bot;

import forfun.sandbox.uwns.shared.meta.MetaData;

public interface Client {

    void onConnect();

    void acceptMessage(MetaData metadata);

    void onDisconnect();
}
