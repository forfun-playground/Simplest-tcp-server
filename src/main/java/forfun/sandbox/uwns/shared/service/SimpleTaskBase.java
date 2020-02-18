package forfun.sandbox.uwns.shared.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SimpleTaskBase implements Runnable {

    @Override
    public void run() {
        try {
            this.runImpl();
        } catch (Exception e) {
            log.debug("task execution failed", e);
        }
    }

    public abstract void runImpl();
}
