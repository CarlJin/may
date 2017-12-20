package wss.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-12-07 上午9:51
 **/
public class ThreadTest {

    private static final Logger logger = LoggerFactory.getLogger(ThreadTest.class);
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    @Test
    public void threadLocalTest() throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        LocalDateTime beginTime = LocalDateTime.now();
        int num = 10;
        for (int i = 0; i < num; i++) {
            Future<String> submit = executorService.submit(() -> {
                logger.info("当前线程{}", Thread.currentThread().getName());
                threadLocal.set("threadloacl" + Thread.currentThread().getName());
                logger.info("当前线程{},里面的值{}", Thread.currentThread().getName(), threadLocal.get());
                return Thread.currentThread().getName();
            });

        }
        executorService.shutdown();

        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("所有的子线程都结束了！");
                break;
            }
            Thread.sleep(10);
        }

        LocalDateTime endTime = LocalDateTime.now();

        logger.info("处理请求{}消耗时间{}", num, Duration.between(endTime, beginTime).toMillis());
    }

    @Test
    public void completableFuture1() throws InterruptedException, ExecutionException {

        CompletableFuture<String> completableFuture = new CompletableFuture();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟执行耗时任务
                    logger.info("task doing...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("抛异常了");
                } catch (Exception e) {
                    //告诉completableFuture任务发生异常了
                    completableFuture.completeExceptionally(e);
                }
            }
        }).start();
        //获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        logger.info("计算结果:" + result);

    }

    @Test
    public void completableFuture2() throws InterruptedException, ExecutionException {
        //http://www.jianshu.com/p/4897ccdcb278
        //supplyAsync内部使用ForkJoinPool线程池执行任务
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result";
        });
        logger.info("计算结果:" + completableFuture.get());

    }

    @Test
    public void completableFuture3() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            logger.info(Thread.currentThread().getName() + "task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return 100;
        });

        //注册完成事件
        completableFuture1.thenAccept(result -> logger.info(Thread.currentThread().getName() + "task1 done,result:" + result));

        CompletableFuture<Integer> completableFuture2 =
                //第二个任务
                CompletableFuture.supplyAsync(() -> {
                    //模拟执行耗时任务
                    logger.info(Thread.currentThread().getName() + "task2 doing...");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //返回结果
                    return 2000;
                });

        //注册完成事件
        completableFuture2.thenAccept(result -> logger.info(Thread.currentThread().getName() + "task2 done,result:" + result));

        //将第一个任务与第二个任务组合一起执行，都执行完成后，将两个任务的结果合并
        CompletableFuture<Integer> completableFuture3 = completableFuture1.thenCombine(completableFuture2,
                //合并函数
                (result1, result2) -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return result1 + result2;
                });

        logger.info(Thread.currentThread().getName() + completableFuture3.get());
    }

    @Test
    public void completableFuture4() throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task1 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result1";
        });

        //等第一个任务完成后，将任务结果传给参数result，执行后面的任务并返回一个代表任务的completableFuture
        CompletableFuture<String> completableFuture2 = completableFuture1.thenCompose(result -> CompletableFuture.supplyAsync(() -> {
            //模拟执行耗时任务
            System.out.println("task2 doing...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //返回结果
            return "result2";
        }));

        System.out.println(completableFuture2.get());
    }

    @Test
    public void completableFuture5() throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<String> submit = executorService.submit(() -> {
            int i = 1 / 0;
            return "result";
        });

        logger.info("" + submit.isCancelled());
        logger.info("" + submit.isDone());
        try {
            logger.info("等待结果{}", submit.get());
        } catch (Exception e) {
            logger.info("出错啦{}", e.getMessage());
        }
    }

    @Test
    public void completableFuture6() throws InterruptedException, ExecutionException {

        CompletableFuture<String> completableFuture = new CompletableFuture();
        new Thread(() -> {
            //模拟执行耗时任务
            logger.info("task doing...");
            int i = 1/0;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //告诉completableFuture任务已经完成
            completableFuture.complete("result");
        }
        ).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("" + completableFuture.isCancelled());
        logger.info("" + completableFuture.isDone());
        //获取任务结果，如果没有完成会一直阻塞等待
        String result = completableFuture.get();
        logger.info("计算结果:" + result);
    }

    @Test
    public void test12() throws InterruptedException {

        Instant beginTim = Instant.now();
        Thread.sleep(200);
        Instant endTime = Instant.now();

        logger.info("接口执行时间{}", Duration.between(endTime, beginTim).toMillis());
    }
}
