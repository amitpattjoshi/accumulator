package com.java.accumulator;


/**
 * <p>Accumulates one or more variables together and maintains a running {@code int} sum of the
 * elements. Updates to this total sum are done via the {@link #accumulate} method. The current
 * value of the running total is returned by calling {@link #getTotal}.</p>
 *
 * <p>The initial value of the running sum is 0.</p>
 *
 * <p>The total sum can be set to 0 by calling {@link #reset}.</p>
 *
 * <p>Usage example:
 * <pre>
 * Accumulator accumulator = ...;
 * int firstSum = accumulator.accumulate(1, 2, 3);
 * int secondSum = accumulator.accumulate(4);
 * int total = accumulator.getTotal();
 * </pre>
 * In this case, the value of {@code firstSum} is 6, {@code secondSum} is 4 and the value of {@code
 * total} is 10. Calling {@code accumulator.reset()} would reset the total value to 0.</p>
 */
public interface Accumulator {

    /**
     * <p>Adds one or more values to the running sum.</p>
     *
     * <p>This method calculates the sum of the given arguments first, then updates the total value
     * with this sum.</p>
     *
     * @param values
     *     the item or items to add to the running total
     * @return the sum of the arguments passed to the method
     */
    int accumulate(int... values);

    /**
     * <p>The current value of the total sum.</p>
     *
     * @return the total sum value
     */
    int getTotal();

    /**
     * <p>Resets the running value to 0.</p>
     */
    void reset();

}
