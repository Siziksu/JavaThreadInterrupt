package com.demo;

public class Main {

  private static final int PAUSE = 4; // number of seconds after which the worker will be interrupted
  private static final boolean INTERRUPT = true; // interrupt or not interrupt the worker

  private Thread worker;

  public static void main(String[] args) {
    new Main().start();
  }

  /**
   * In this example, a worker thread and a stopper thread will be started. The worker thread is in charge of doing
   * some dummy calculations. The stopper thread will interrupt the worker thread after some seconds.
   * <br /><br /><pre>NOTE: The calculation timings depend on the computer used.</pre>
   */
  private void start() {
    System.out.println("---> MAIN THREAD");
    startWorkerThread();
    startStopperThread();
    System.out.println("<--- END MAIN THREAD");
  }

  /**
   * This method will start the worker thread. Will make some calculations that will last some seconds.
   */
  private void startWorkerThread() {
    worker = new Thread(new Runnable() {

      @Override
      public void run() {
        System.out.println("---> WORKER THREAD");
        try {
          System.out.println("---> CALCULATIONS");
          long result = 0;
          int number;
          for (int x = 0; x < Integer.MAX_VALUE; x++) {
            if (Thread.interrupted()) {
              throw new InterruptedException();
            }
            number = 0;
            for (int z = 0; z < 15; z++) {
              number += z;
            }
            result += x - number * 24588456;
          }
          System.out.println("---- RESULT: " + result);
          System.out.println("<--- END CALCULATIONS");
        } catch (InterruptedException e) {
          System.out.println("<--- INTERRUPTED CALCULATIONS");
        } catch (Exception e) {
          System.out.println("<--- EXCEPTION CALCULATIONS");
        }
        System.out.println("<--- END WORKER THREAD");
      }
    });
    worker.start();
  }

  /**
   * This method will interrupt the worker thread after some seconds.
   */
  private void startStopperThread() {
    new Thread(new Runnable() {

      @Override
      public void run() {
        try {
          Thread.sleep(PAUSE * 1000);
          if (INTERRUPT) {
            worker.interrupt();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
