package forfun.sandbox.uwns.shared.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecuteMasterImpl implements ExecuteMaster {

    private final ExecutorService executor;
    private final ScheduledExecutorService scheduledExecutor;

    public ExecuteMasterImpl() {
        executor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void executeActionTask(Runnable runnable) {
        executor.execute(runnable);
    }

    @Override
    public <T extends Runnable> ScheduledFuture<T> executeScheduledTask(T runnable, long delay, long interval) {
        try {
            delay = delay > 0L ? delay : 0L;
            interval = interval > 1L ? interval : 1L;
            return (ScheduledFuture<T>) scheduledExecutor.scheduleAtFixedRate(runnable, delay, interval, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            log.warn("Rejected execution", e);
        }
        return null;
    }

    public void shutdown() {
        try {
            scheduledExecutor.awaitTermination(1L, TimeUnit.SECONDS);
            scheduledExecutor.shutdown();
            executor.awaitTermination(1L, TimeUnit.SECONDS);
            executor.shutdown();
        } catch (InterruptedException e) {
            log.error("There has been a problem shuting down the thread pool manager!");
            log.error("{0}{1}", new Object[]{getClass().getSimpleName(), e});
        }

    }
}
