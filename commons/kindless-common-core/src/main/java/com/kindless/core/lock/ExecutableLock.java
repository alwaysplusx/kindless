package com.kindless.core.lock;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

/**
 * @author wuxin
 */
@Slf4j
public class ExecutableLock implements Lock {

    private final Lock delegate;

    public ExecutableLock(Lock lock) {
        this.delegate = lock;
    }

    public <R> R execute(Supplier<R> supplier) {
        Throwable error = null;
        try {
            delegate.lock();
            return supplier.get();
        } catch (Throwable e) {
            log.error("", e);
            error = e;
            throw e;
        } finally {
            try {
                delegate.unlock();
            } catch (Exception e) {
                log.debug("release lock failed", e);
                ReflectionUtils.rethrowRuntimeException(new LockReleaseException(e, error));
            }
        }
    }

    public void execute(Runnable runnable) {
        execute(() -> {
            runnable.run();
            return null;
        });
    }

    @Override
    public void lock() {
        delegate.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        delegate.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return delegate.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return delegate.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        delegate.unlock();
    }

    @Override
    public Condition newCondition() {
        return delegate.newCondition();
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class LockReleaseException extends Exception {

        private Throwable releaseError;
        private Throwable error;

        public LockReleaseException(Throwable releaseError, Throwable error) {
            super(releaseError.getMessage(), releaseError);
            this.releaseError = releaseError;
            this.error = error;
        }

        @Override
        public void printStackTrace(PrintWriter s) {
            releaseError.printStackTrace(s);
            if (error != null) {
                error.printStackTrace(s);
            }
        }

    }

}
