package cn.hein.multithreading.custom;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * 线程池组件
 *
 * @author hein
 */
@Slf4j
public class ThreadPool {

    /**
     * 任务队列
     */
    private final BlockingQueue<Runnable> tasks;

    /**
     * 线程集合
     */
    private final HashSet<Worker> workers = new HashSet<>();

    /**
     * 核心线程数
     */
    private final int coreSize;

    /**
     * 任务超时时间
     */
    private final long timeout;

    /**
     * 时间单位
     */
    private final TimeUnit unit;

    /**
     * 拒绝策略
     */
    private final RejectPolicy<Runnable> policy;

    public ThreadPool(int coreSize, long timeout, TimeUnit unit, int queueCapacity,
                      RejectPolicy<Runnable> policy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.unit = unit;
        this.tasks = new BlockingQueue<>(queueCapacity);
        this.policy = policy;
    }

    /**
     * 执行任务
     * <p>当任务数量超过 coreSize 时，进入任务队列，等待被执行，否则创建新的 Worker 执行</p>
     */
    public void execute(Runnable task) {
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                workers.add(worker);
                worker.start();
            } else {
                tasks.tryPut(policy, task);
            }
        }
    }

    /**
     * 工作线程内部类
     */
    class Worker extends Thread {

        /**
         * 任务对象
         */
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            while (task != null || (task = tasks.poll(timeout, unit)) != null) {
                try {
                    log.debug("正在执行 {}", task);
                    task.run();
                } catch (Exception e) {
                    log.error("error: ", e);
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                workers.remove(this);
            }
        }
    }
}
