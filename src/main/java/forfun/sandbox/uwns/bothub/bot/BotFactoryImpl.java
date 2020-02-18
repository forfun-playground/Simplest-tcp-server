package forfun.sandbox.uwns.bothub.bot;

import forfun.sandbox.uwns.shared.service.ExecuteMaster;

public class BotFactoryImpl implements BotFactory {

    private final ExecuteMaster executeMaster;

    public BotFactoryImpl(
            ExecuteMaster executeMaster
    ) {
        this.executeMaster = executeMaster;
    }

    @Override
    public Bot getInstance(Connector connetor) {
        return new BotImpl(connetor, executeMaster);

    }

}
