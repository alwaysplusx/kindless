package com.kindless.config.lock;

import com.kindless.core.lock.ExecutableLockRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.locks.LockRegistry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Configuration
public class LockConfig {

    @Bean
    public ExecutableLockRegistry lockRegistry() {
        return new ExecutableLockRegistry(new FakeLockRegistry());
    }

    private static class FakeLockRegistry implements LockRegistry {

        @Override
        public Lock obtain(Object lockKey) {
            return new Lock() {
                @Override
                public void lock() {

                }

                @Override
                public void lockInterruptibly() throws InterruptedException {

                }

                @Override
                public boolean tryLock() {
                    return false;
                }

                @Override
                public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
                    return false;
                }

                @Override
                public void unlock() {

                }

                @Override
                public Condition newCondition() {
                    return null;
                }
            };
        }

    }

}
