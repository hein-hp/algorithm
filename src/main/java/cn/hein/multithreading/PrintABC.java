package cn.hein.multithreading;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程交替打印 ABC
 *
 * @author hein
 */
public class PrintABC {

    /**
     * Semaphore
     */
    static class ways1 {

        private static final Semaphore semA = new Semaphore(1);
        private static final Semaphore semB = new Semaphore(0);
        private static final Semaphore semC = new Semaphore(0);
        public static final int COUNT = 10;

        public static void main(String[] args) {
            new Thread(new PrintTask(semA, semB, 'A')).start();
            new Thread(new PrintTask(semB, semC, 'B')).start();
            new Thread(new PrintTask(semC, semA, 'C')).start();
        }

        static class PrintTask implements Runnable {
            private final Semaphore cur;
            private final Semaphore next;
            private final Character print;

            public PrintTask(Semaphore cur, Semaphore next, Character print) {
                this.cur = cur;
                this.next = next;
                this.print = print;
            }

            @Override
            public void run() {
                for (int i = 0; i < COUNT; i++) {
                    try {
                        cur.acquire();
                        System.out.println(print);
                        next.release();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * 多个 Condition
     */
    static class ways2 {

        private final static ReentrantLock lock = new ReentrantLock();

        private final static Condition conA = lock.newCondition();
        private final static Condition conB = lock.newCondition();
        private final static Condition conC = lock.newCondition();
        private static int state = 0;

        public static final int COUNT = 10;

        public static void main(String[] args) {
            new Thread(new PrintTask(conA, conB, 'A', 0)).start();
            new Thread(new PrintTask(conB, conC, 'B', 1)).start();
            new Thread(new PrintTask(conC, conA, 'C', 2)).start();
        }

        static class PrintTask implements Runnable {

            private final Condition cur;
            private final Condition next;
            private final Character print;
            private final int target;

            public PrintTask(Condition cur, Condition next, Character print, int target) {
                this.cur = cur;
                this.next = next;
                this.print = print;
                this.target = target;
            }

            @Override
            public void run() {
                lock.lock();
                try {
                    for (int i = 0; i < COUNT; i++) {
                        // 由于是精准唤醒，所以无需 while, if 就可以了
                        if (state % 3 != target) {
                            cur.await();
                        }
                        System.out.println(print);
                        state++;
                        next.signal();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 单个 Condition
     */
    static class ways3 {

        private static final ReentrantLock lock = new ReentrantLock();
        private static final Condition condition = lock.newCondition();

        private static int state = 0;

        public static final int COUNT = 10;

        public static void main(String[] args) {
            new Thread(new ways3.PrintTask(condition, 'A', 0)).start();
            new Thread(new ways3.PrintTask(condition, 'B', 1)).start();
            new Thread(new ways3.PrintTask(condition, 'C', 2)).start();
        }

        static class PrintTask implements Runnable {
            private final Condition condition;
            private final Character print;
            private final int target;

            public PrintTask(Condition condition, Character print, int target) {
                this.condition = condition;
                this.print = print;
                this.target = target;
            }

            @Override
            public void run() {
                lock.lock();
                try {
                    for (int i = 0; i < COUNT; i++) {
                        // while 防止虚假唤醒
                        while (state % 3 != target) {
                            condition.await();
                        }
                        state++;
                        System.out.println(print);
                        condition.signalAll();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }

        /**
         * Synchronized
         */
        static class ways4 {
            private static final Object lock = new Object();
            private static int state = 0;

            public static int COUNT = 10;

            public static void main(String[] args) {
                new Thread(new ways4.PrintTask('A', 0)).start();
                new Thread(new ways4.PrintTask('B', 1)).start();
                new Thread(new ways4.PrintTask('C', 2)).start();
            }

            static class PrintTask implements Runnable {

                private final Character print;
                private final int target;

                public PrintTask(Character print, int target) {
                    this.print = print;
                    this.target = target;
                }

                @Override
                public void run() {
                    synchronized (lock) {
                        for (int i = 0; i < COUNT; i++) {
                            // 防止虚假唤醒
                            while (state % 3 != target) {
                                try {
                                    // wait() 进入等待区并释放对象锁
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            state++;
                            System.out.println(print);
                            lock.notifyAll();
                        }
                    }
                }
            }
        }

        /**
         * LockSupport
         */
        static class ways5 {

            public static int COUNT = 10;

            private static int state = 0;

            public static void main(String[] args) {
                Thread[] threads = new Thread[3];
                for (int i = 0; i < 3; i++) {
                    threads[i] = new Thread(new ways5.PrintTask(i, (char) ('A' + i), threads));
                    threads[i].start();
                }
            }

            static class PrintTask implements Runnable {

                private final Character print;
                private final int id;
                private final Thread[] threads;

                public PrintTask(int id, Character print, Thread[] threads) {
                    this.id = id;
                    this.print = print;
                    this.threads = threads;
                }

                @Override
                public void run() {
                    for (int i = 0; i < COUNT; i++) {
                        while (state % 3 != id) {
                            LockSupport.park();
                        }
                        state++;
                        System.out.println(print);
                        LockSupport.unpark(threads[(id + 1) % 3]);
                    }
                }
            }
        }
    }
}