package com.java.accumulator;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AccumulatorImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Accumulator accumulator;

    @Before
    public void setup() {
        accumulator = new AccumulatorImpl();
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
    public void testAccumulateForOverflowExceptionWith_Integer_MIn_Value() {
        expectedException.expect(ArithmeticException.class);
        expectedException.expectMessage(equalTo("integer overflow"));
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

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                accumulator.accumulate(1);
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                accumulator.accumulate(2);
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 1000_000; i++) {
            service.submit(r1);
        }
        for (int i = 0; i < 1000_000; i++) {
            service.submit(r2);
        }

        service.shutdown();

        try {
            service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(accumulator.getTotal(), equalTo(3000_000));
    }

    @After
    public void tearDown() {
        accumulator.reset();
    }

}