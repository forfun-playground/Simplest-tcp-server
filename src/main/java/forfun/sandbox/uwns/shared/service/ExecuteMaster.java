package forfun.sandbox.uwns.shared.service;

import java.util.concurrent.ScheduledFuture;

public interface ExecuteMaster {

    void executeActionTask(Runnable runnable);

    <T extends Runnable> ScheduledFuture<T> executeScheduledTask(T runnable, long delay, long interval);
}
