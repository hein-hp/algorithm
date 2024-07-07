package cn.hein.multithreading.custom;

/**
 * 拒绝策略接口
 *
 * @author hein
 */
@FunctionalInterface
public interface RejectPolicy<T> {

    /**
     * 拒绝策略
     */
    void reject(BlockingQueue<T> queue, T task);
}