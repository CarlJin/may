package wss.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
}
