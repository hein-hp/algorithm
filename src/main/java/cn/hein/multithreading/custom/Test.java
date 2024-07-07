package cn.hein.multithreading.custom;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池 ThreadPool 测试
 *
 * @author hein
 */
@Slf4j
public class Test {

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(2, 1000L, TimeUnit.MILLISECONDS, 1,
                // 死等
                // BlockingQueue::put
                // 带超时等待
                // (queue, task) -> queue.offer(task, 1500L, TimeUnit.MILLISECONDS)
                // 让调用者放弃执行
                // (queue, task) -> log.debug("放弃 {}", task)
                // 让调用者自己执行
                (queue, task) -> task.run()
        );
        for (int i = 0; i < 4; i++) {
            int j = i;
            pool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    log.error("error: ", e);
                }
                log.debug("{}", j);
            });
        }
    }
}