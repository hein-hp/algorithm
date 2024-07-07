package cn.hein.multithreading;

import java.util.concurrent.Semaphore;

/**
 * 三个线程交替打印 0 - 100 的数
 *
 * @author hein
 */
public class PrintNum {

    /**
     * Semaphore
     */
    static class ways1 {

        private static final Semaphore semA = new Semaphore(1);
        private static final Semaphore semB = new Semaphore(0);
        private static final Semaphore semC = new Semaphore(0);

        public static void main(String[] args) {
            new Thread(new PrintTask(0, semA, semB)).start();
            new Thread(new PrintTask(1, semB, semC)).start();
            new Thread(new PrintTask(2, semC, semA)).start();
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
                        print += 3;
                        next.release();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
