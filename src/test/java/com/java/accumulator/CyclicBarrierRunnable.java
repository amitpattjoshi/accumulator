package com.java.accumulator;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class CyclicBarrierRunnable implements Runnable {
    private final Accumulator accumulator;
    private CyclicBarrier barrier;
    private int[] values;

    public CyclicBarrierRunnable(CyclicBarrier barrier, Accumulator accumulator, int... values) {
        this.barrier = barrier;
        this.accumulator = accumulator;
        this.values = values;
    }

    public void run() {
        try {
            System.out.println("Waiting !");
            barrier.await();
            accumulator.accumulate(values);
            System.out.println("Completed thread !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}