package wss.thread.LogThread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wss.util.HttpUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-12-05 下午4:41
 **/
public class LogThread {

    private static final Logger logger = LoggerFactory.getLogger(LogThread.class);

    static int begin = 1;

    public static void main(String[] args) throws InterruptedException, IOException {
        //threadPool(10000);
        //completableFuture(10000);
        serial(1);

    }

    /**
     * 异步测试 通过线程池
     */
    public static void threadPool(int num) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        long beginTime = System.nanoTime();
        for (int i = 0; i < num; i++) {
            Future<String> submit = executorService.submit(() -> {
                logger.info("当前线程{}", Thread.currentThread().getName());

                String response = testLog(begin++, "98946635");
                return response;
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
        long endTime = System.nanoTime();
        logger.info("处理请求{}消耗时间{}", num, TimeUnit.NANOSECONDS.toMillis(endTime - beginTime));

    }

    /**
     * 异步测试 通过completableFuture
     */
    public static void completableFuture(int num) {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        long beginTime = System.nanoTime();

        List<CompletableFuture<String>> futures = Stream.iterate(0, n -> n + 1).limit(num).map(number ->

                CompletableFuture.supplyAsync(() -> testLog(number, "98946635"), executorService)

        ).collect(Collectors.toList());

        //futures.stream().map(CompletableFuture::join)  执行完成 不拿结果

        List<String> result = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

        long endTime = System.nanoTime();
        logger.info("处理请求{}消耗时间{}", num, TimeUnit.NANOSECONDS.toMillis(endTime - beginTime));
    }

    public static void serial(int num) {
        long beginTime = System.nanoTime();
        for (int i = 0; i < num; i++) {
            testLog(i, "98946635");
        }
        long endTime = System.nanoTime();
        logger.info("处理请求{}消耗时间{}", num, TimeUnit.NANOSECONDS.toMillis(endTime - beginTime));
    }

    public static String testLog(int i, String userId) {

        String data = "userId=1";
        String url = "http://localhost:8081/acc-app/accManager/queryBalance?userId=" + userId;
        String response = null;
        try {
            response = HttpUtils.doHttpRequest(null, url, HttpUtils.GET, 100000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("第{}执行查询，查询结果{}", i, response);

        return response;
    }
}

