package multi_thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TP {

    /**
     * 验证线程池在没有引用后,仍有核心线程在运行,在核心线程工作完成之后,最终,这些线程会不会自动结束
     * <p>
     * 结论:
     * 核心线程如果allowCoreThreadTimeOut为false(默认情况),那么在任务执行完之后,即使线程池对象没有引用,系统也不会回收核心线程
     * 如果allowCoreThreadTimeOut为true,那么在任务执行完之后,系统会回收核心线程
     */
    public static void testCoreThreadLife(boolean allowCoreThreadTimeOut) {
        int totalThreadSize = 10;
        ThreadPoolExecutor tp = new ThreadPoolExecutor(
                5,
                totalThreadSize,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                new ThreadFactoryBuilder()
                        .setNameFormat("my_thread_%d")
                        .build());
        tp.allowCoreThreadTimeOut(allowCoreThreadTimeOut);

        for (int i = 0; i < totalThreadSize; ++i) {
            tp.submit(() -> {
                System.out.println(String.format("%s start", Thread.currentThread().getName()));
                try {
                    Thread.sleep(10000);
                } catch (Throwable ignored) {
                }
                System.out.println(String.format("%s job finish", Thread.currentThread().getName()));
            });
        }
    }

    /**
     * 向线程池提交了任务之后,一些任务没有被执行,被存储在队列中。一些任务正在被执行。
     * 如果此时shutDown线程池,那么未被执行的任务会被执行吗?正在被执行的任务会继续执行吗?
     * 在shutDown后,再提交任务,会出现什么情况?
     *
     * 结论:
     * 在线程池被shutdown前提交的任务,都会被执行
     * 在shutdown之后提交任务,线程池会拒绝接受任务
     */
    public static void testShutDown() {
        int totalThreadSize = 10;
        ThreadPoolExecutor tp = new ThreadPoolExecutor(
                5,
                totalThreadSize,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000),
                new ThreadFactoryBuilder()
                        .setNameFormat("my_thread_%d")
                        .build());

        for (int i = 0; i < totalThreadSize; ++i) {
            final int taskId = i;
            tp.submit(() -> {
                System.out.println(String.format("task_%s start in thread %s",
                        taskId, Thread.currentThread().getName()));

                try {
                    Thread.sleep(10000);
                } catch (Throwable ignored) {
                }

                System.out.println(String.format("task_%s finish in thread %s",
                        taskId, Thread.currentThread().getName()));
            });
        }

        tp.shutdown();

        tp.submit(()->{
            System.out.println("task run after tp shutdown");
        });

        System.out.println("thread pool shutdown");
    }

    public static void main(String[] args) throws InterruptedException {
        testShutDown();
    }

}
