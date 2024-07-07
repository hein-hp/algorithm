package cn.hein.multithreading.custom;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列
 *
 * @author hein
 */
@Slf4j
public class BlockingQueue<T> {

    /**
     * 任务队列
     */
    private final Deque<T> queue = new ArrayDeque<>();

    /**
     * 锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 生产者条件变量，当阻塞队列满则进入等待
     */
    private final Condition full = lock.newCondition();

    /**
     * 消费者条件变量，当阻塞队列空则进入等待
     */
    private final Condition empty = lock.newCondition();

    /**
     * 队列容量
     */
    private final int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 有时限的添加
     */
    public boolean offer(T task, long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            // 若队列满则生产者阻塞（有时限）
            while (queue.size() == capacity) {
                try {
                    if (nanos <= 0) {
                        return false;
                    }
                    // awaitNanos() 返回值是等待时限的剩余时间
                    nanos = full.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    log.error("error:" + e);
                }
            }
            // 若队列非满，则将任务添加到队列中并唤醒
            queue.addLast(task);
            empty.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 无时限的添加，会一直阻塞
     */
    public void put(T task) {
        lock.lock();
        try {
            // 若队列满，则生成者阻塞
            while (queue.size() == capacity) {
                try {
                    full.await();
                } catch (InterruptedException e) {
                    log.error("error:" + e);
                }
            }
            queue.addLast(task);
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 无时限的获取，会一直阻塞
     */
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    empty.await();
                } catch (InterruptedException e) {
                    log.error("error:" + e);
                }
            }
            T t = queue.removeFirst();
            full.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 有时限的获取
     */
    public T poll(long timeout, TimeUnit unit) {
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (queue.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = empty.awaitNanos(timeout);
                } catch (InterruptedException e) {
                    log.error("error:" + e);
                }
            }
            T t = queue.removeFirst();
            full.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取队列中任务数量
     */
    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 尝试添加，若添加失败则执行自定义的拒绝策略
     */
    public void tryPut(RejectPolicy<T> policy, T task) {
        lock.lock();
        try {
            // 判断队列是否满
            if (queue.size() == capacity) {
                // 策略模式
                policy.reject(this, task);
            } else {
                queue.addLast(task);
                empty.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}