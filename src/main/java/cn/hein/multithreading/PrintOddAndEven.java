package cn.hein.multithreading;

import java.util.concurrent.Semaphore;

/**
 * 两个线程交替打印 0 - 100 的奇偶数
 *
 * @author hein
 */
public class PrintOddAndEven {

    /**
     * Semaphore
     */
    static class ways1 {

        private static final Semaphore semA = new Semaphore(1);
        private static final Semaphore semB = new Semaphore(0);

        public static void main(String[] args) {
            new Thread(new PrintTask(0, semA, semB)).start();
            new Thread(new PrintTask(1, semB, semA)).start();
        }

        static class PrintTask implements Runnable {

            private int print;
            private final Semaphore cur;
            private final Semaphore next;

            public PrintTask(int print, Semaphore cur, Semaphore next) {
                this.print = print;
                this.cur = cur;
                this.next = next;
            }

            @Override
            public void run() {
                while (print <= 100) {
                    try {
                        cur.acquire();
                        System.out.println(Thread.currentThread().getName() + ":" + print);
                        print += 2;
                        next.release();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
