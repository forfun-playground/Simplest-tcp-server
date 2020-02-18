package forfun.sandbox.uwns.bothub;

import org.aeonbits.owner.ConfigFactory;

public class BothubConfigFactory {

    private final BothubConfig bootConfig;

    public BothubConfigFactory() {
        bootConfig = ConfigFactory.create(BothubConfig.class);
    }

    public BothubConfig getConfig() {
        return bootConfig;
    }

}
