package forfun.sandbox.uwns.bothub;

import forfun.sandbox.uwns.bothub.bot.BotFactory;
import forfun.sandbox.uwns.bothub.bot.BotFactoryImpl;
import forfun.sandbox.uwns.bothub.network.NetworkBootstrap;
import forfun.sandbox.uwns.bothub.network.NetworkInitializer;
import forfun.sandbox.uwns.shared.pack.Packtype;
import forfun.sandbox.uwns.shared.service.ExecuteMaster;
import forfun.sandbox.uwns.shared.service.ExecuteMasterImpl;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bothub {

    public static void main(String[] args) throws Exception {
        try {
            BothubConfigFactory configFactory = new BothubConfigFactory();
            BothubConfig bothubConfig = configFactory.getConfig();
            log.info(bothubConfig.Greeting());

            int numberOfBots = bothubConfig.BotsCount();
            numberOfBots = numberOfBots > 0 ? numberOfBots : 0;
            log.info("Number of Bots agents: " + numberOfBots);

            String stringPacktype = bothubConfig.ProtocolType();
            Packtype packtype = Packtype.valueOf(stringPacktype);
            log.info("Packtype: " + packtype.name());

            String nodeHost = bothubConfig.Host();
            log.info("Node host: " + nodeHost);

            int nodePort = bothubConfig.TcpPort();
            log.info("Uwnn tcp port: " + nodePort);

            ExecuteMaster executeMaster = new ExecuteMasterImpl();
            BotFactory botFactory = new BotFactoryImpl(executeMaster);

            NetworkInitializer networkInitializer = new NetworkInitializer(packtype, botFactory);
            NetworkBootstrap networkBootstrap = new NetworkBootstrap(networkInitializer, configFactory);
            networkBootstrap.init();

            for (int number = 0; number < numberOfBots; number++) {
                ChannelFuture channel = networkBootstrap.connnect(nodeHost, nodePort);
                if (channel.isSuccess()) {
                } else {
                    log.error(channel.cause().getMessage());
                    break;
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            System.exit(1);
        }
        log.info("All Bots created");
    }
}
