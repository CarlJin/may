package wss.java8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-11-29 下午2:23
 **/
public class Java8Test {

    private static Logger logger = LoggerFactory.getLogger(Java8Test.class);

    @Test
    private void timeTest() {
        logger.info("hello");
    }

    @Test
    private void optionTest() {
        //创建Optional实例，也可以通过方法返回值得到。
        Optional<String> name = Optional.of("Sanaulla");

        //创建没有值的Optional实例，例如值为'null'
        Optional empty = Optional.ofNullable(null);

        //isPresent方法用来检查Optional实例是否有值。
        if (name.isPresent()) {
            //调用get()返回Optional值。
            System.out.println(name.get());
        }

        try {
            //在Optional实例上调用get()抛出NoSuchElementException。
            System.out.println(empty.get());
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }

        //ifPresent方法接受lambda表达式参数。
        //如果Optional值不为空，lambda表达式会处理并在其上执行操作。
        name.ifPresent((value) -> {
            System.out.println("The length of the value is: " + value.length());
        });

        //如果有值orElse方法会返回Optional实例，否则返回传入的错误信息。
        System.out.println(empty.orElse("There is no value present!"));
        System.out.println(name.orElse("There is some value!"));

        //orElseGet与orElse类似，区别在于传入的默认值。
        //orElseGet接受lambda表达式生成默认值。
        System.out.println(empty.orElseGet(() -> "Default Value"));
        System.out.println(name.orElseGet(() -> "Default Value"));

        try {
            //orElseThrow与orElse方法类似，区别在于返回值。
            //orElseThrow抛出由传入的lambda表达式/方法生成异常。
            empty.orElseThrow(ValueAbsentException::new);
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }

        //map方法通过传入的lambda表达式修改Optonal实例默认值。
        //lambda表达式返回值会包装为Optional实例。
        Optional<String> upperName = name.map((value) -> value.toUpperCase());
        System.out.println(upperName.orElse("No value found"));

        //flatMap与map（Funtion）非常相似，区别在于lambda表达式的返回值。
        //map方法的lambda表达式返回值可以是任何类型，但是返回值会包装成Optional实例。
        //但是flatMap方法的lambda返回值总是Optional类型。
        upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));
        System.out.println(upperName.orElse("No value found"));

        //filter方法检查Optiona值是否满足给定条件。
        //如果满足返回Optional实例值，否则返回空Optional。
        Optional<String> longName = name.filter((value) -> value.length() > 6);
        System.out.println(longName.orElse("The name is less than 6 characters"));

        //另一个示例，Optional值不满足给定条件。
        Optional<String> anotherName = Optional.of("Sana");
        Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
        System.out.println(shortName.orElse("The name is less than 6 characters"));

    }


    @Test
    private void CompletableFutureTest() {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "hello").thenApply(s -> s + "word");
        String join = stringCompletableFuture.join();
        logger.info(join);

        CompletableFuture.supplyAsync(() -> "hello").thenAccept(s -> logger.info(s + " world"));
    }

    @Test
    public void thenRun() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println("hello world"));
        while (true) {
        }
    }

    @Test
    public void localDateTime() throws InterruptedException {

        LocalDateTime beginTime = LocalDateTime.now();
        logger.info("开始时间{}", beginTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        logger.info("格式化后String的时间开始时间{}", dtf.format(beginTime));
        logger.info("格式化后DateTimeFormatter的时间开始时间{}", dtf.parse("2017-12-07 09:39:27"));

        Thread.sleep(1000);
        LocalDateTime endTime = LocalDateTime.now();
        logger.info("耗时{}", Duration.between(endTime, beginTime).toMillis());

    }

}
