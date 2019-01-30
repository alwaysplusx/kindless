package com.harmony.kindless.apis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wuxii
 */
public class WorkRunner implements Runnable {

    private Runnable runnable;

    private ExecutorService executorService;

    private int poolSize;

    public WorkRunner(int poolSize, Runnable runnable) {
        this.poolSize = poolSize;
        this.runnable = runnable;
        this.executorService = Executors.newFixedThreadPool(poolSize);
    }

    private boolean started = true;

    @Override
    public void run() {
        while (started) {
            runnable.run();
        }
    }

    public void stop() {
        this.started = false;
    }

    public void start() {
        for (int i = 0; i < poolSize; i++) {
            executorService.execute(this);
        }
    }

}
