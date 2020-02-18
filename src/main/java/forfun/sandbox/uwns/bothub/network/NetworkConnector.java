package forfun.sandbox.uwns.bothub.network;

import forfun.sandbox.uwns.bothub.bot.Client;
import forfun.sandbox.uwns.bothub.bot.Connector;
import io.netty.util.AttributeKey;

public interface NetworkConnector extends Connector{
    public final static AttributeKey<Client> REFERENCE = AttributeKey.valueOf("Client");
}
