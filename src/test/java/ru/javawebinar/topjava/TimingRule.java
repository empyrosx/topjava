package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

public class TimingRule implements TestRule {

    private long start;
    private long end;

    @Override
    public Statement apply(Statement base, Description description) {
        return new TimeStatement(base);
    }

    private class TimeStatement extends Statement {

        private Statement base;

        public TimeStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            start = System.nanoTime();
            try {
                base.evaluate();
            } finally {
                end = System.nanoTime();
                printTiming();
            }
        }
    }

    public void printTiming() {
        long rest = end - start;
        long seconds = TimeUnit.SECONDS.convert(rest, TimeUnit.NANOSECONDS);
        rest = rest - TimeUnit.SECONDS.toNanos(seconds);
        long milli = TimeUnit.MILLISECONDS.convert(rest, TimeUnit.NANOSECONDS);
        rest = rest - TimeUnit.MILLISECONDS.toNanos(milli);
        long micro = TimeUnit.MICROSECONDS.convert(rest, TimeUnit.NANOSECONDS);
        long nano = rest - TimeUnit.MICROSECONDS.toNanos(micro);
        System.out.println(String.format("Время выполнения: %d:%3d:%3d.%3d", seconds, milli, micro, nano));
    }

}
