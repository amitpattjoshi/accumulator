package com.java.accumulator;

import java.util.concurrent.atomic.LongAdder;

/**
 * <p>An Accumulator maintains a running total sum of one or more variables.
 * Updating of this total value is done by accumulating more variables.
 * Reading the total value is done by calling the getTotal method.</p>
 * <p>The accumulate method returns the sum of its arguments (which is added to the total running value).</p>
 */
public class AccumulatorSecondImpl implements Accumulator {

    private LongAdder longAdder = new LongAdder();

    /**
     * This method calculates the sum of the given arguments first,
     * then updates the total value with this sum.
     * Adds one or more values to the running sum.
     *
     * @param values the item or items to add to the running total
     * @return the accumulated sum of the arguments passed to the method
     */
    @Override
    public int accumulate(int... values) throws ArithmeticException {
        for (int value : values) {
            longAdder.add(new Long(value));
            throwIntegerOverFlowExceptionIfExceedsMaxValue(longAdder.doubleValue());
        }
        return longAdder.intValue();

    }

    private void throwIntegerOverFlowExceptionIfExceedsMaxValue(double v) {
        if (v > Integer.MAX_VALUE) throw new ArithmeticException("integer overflow");
        else if (v < Integer.MIN_VALUE) {
            throw new ArithmeticException("integer underflow");
        }
    }

    /**
     * The current value of the total sum.
     *
     * @return the total sum value
     */
    @Override
    public int getTotal() {
        return longAdder.intValue();
    }

    /**
     * Resets the running value to 0.
     *
     * @return
     */
    @Override
    public void reset() {
        longAdder.reset();
    }
}