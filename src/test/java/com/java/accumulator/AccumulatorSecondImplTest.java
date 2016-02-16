package com.java.accumulator;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AccumulatorSecondImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Accumulator accumulator;

    @Before
    public void setup() {
        accumulator = new AccumulatorSecondImpl();
    }

    @Test
    public void testAccumulatorObjectIsNotNull() {
        Assert.assertNotNull(accumulator);
    }

    @Test
    public void testAccumulateForAllPositiveIntegers() {
        int total = accumulator.accumulate(1, 2, 3);
        assertThat(total, equalTo(6));
    }

    @Test
    public void testAccumulateForAllNegativeIntegers() {
        int total = accumulator.accumulate(-1, -2, -3);
        assertThat(total, equalTo(-6));
    }

    @Test
    public void testAccumulateForAddingPositiveToNegativeInteger() {
        int total = accumulator.accumulate(1, 2, -4);
        assertThat(total, equalTo(-1));
    }

    @Test
    public void testAccumulateForOverflowException_Integer_Max_Value() {
        expectedException.expect(ArithmeticException.class);
        expectedException.expectMessage(equalTo("integer overflow"));
        accumulator.accumulate(1, Integer.MAX_VALUE);
    }

    @Test
    public void testAccumulateForUnderflowExceptionWith_Integer_MIn_Value() {
        expectedException.expect(ArithmeticException.class);
        expectedException.expectMessage(equalTo("integer underflow"));
        accumulator.accumulate(-1, Integer.MIN_VALUE);
    }

    @Test
    public void testAccumulateOfInteger_MinValueWithZero() {
        accumulator.accumulate(0, Integer.MIN_VALUE);
        assertThat(accumulator.getTotal(), equalTo(Integer.MIN_VALUE));
    }

    @Test
    public void testAccumulatedSumAfterAddingOneOrMoreValueToRunningSum() {
        int firstSum = accumulator.accumulate(1, 2);
        int secondSum = accumulator.accumulate(3, 4);

        assertThat(firstSum, equalTo(3));
        assertThat(secondSum, equalTo(10));
    }

    @Test
    public void testAccumulatedTotalAfterAddingOneOrMoreValueToRunningSum() {
        accumulator.accumulate(1, 2);
        accumulator.accumulate(3, 4);
        accumulator.accumulate(5);
        assertThat(accumulator.getTotal(), equalTo(15));
    }

    @Test
    public void testAccumulatorGetInitializedToZeroAfterReset() {
        accumulator.reset();
        assertThat(accumulator.getTotal(), equalTo(0));
    }

    @Test
    public void testAccumulatedValueOnThreadedEnv() {
        CyclicBarrier barrier = new CyclicBarrier(1000, () -> System.out.println("Lets Play Concurrently...!"));
        ExecutorService service = Executors.newFixedThreadPool(1000);

        for (int i = 0; i < 1000; i++) {
            service.submit(new CyclicBarrierRunnable(barrier, accumulator, 1, 2));
        }

        service.shutdown();

        try {
            service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(accumulator.getTotal(), equalTo(3000));
    }

    @After
    public void tearDown() {
        accumulator.reset();
    }

}