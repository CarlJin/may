package wss.log;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-12-06 下午3:27
 **/
@Order(Ordered.LOWEST_PRECEDENCE)
@Aspect
@Component
public class ILogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ILogAspect.class);

    @Pointcut("")
    public void logServiceAspect() {

    }

    public void log(JoinPoint point, Object object) {

        Signature signature = point.getSignature();
        StringBuilder signStr = new StringBuilder();
        signStr.append(signature.getDeclaringType().getSimpleName()).append(".").append(signature.getName());

        StringBuilder sb = new StringBuilder();

        Arrays.stream(point.getArgs()).forEach(arg -> sb.append(JSON.toJSONString(arg)).append(","));

        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }

        logger.info("账户中心", "acc-app", "充值", signStr);
        logger.info("入参", sb.toString());


    }


    public void log(ProceedingJoinPoint point) {

        Signature signature = point.getSignature();
        StringBuilder signStr = new StringBuilder();
        signStr.append(signature.getDeclaringType().getSimpleName()).append(".").append(signature.getName());

        StringBuilder sb = new StringBuilder();

        Arrays.stream(point.getArgs()).forEach(arg -> sb.append(JSON.toJSONString(arg)).append(","));

        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        logger.info("账户中心", "acc-app", "充值", signStr);
        logger.info("入参", sb.toString());

        Object result = null;
        try {
            result = point.proceed();
        } catch (Throwable throwable) {
            //判断是否为业务异常
            if (throwable instanceof Exception) {
                Exception exception = (Exception) throwable;

            } else {

            }
        }

        sb.setLength(0);
        sb.append(JSON.toJSONString(result));
        logger.info("出参", sb.toString());
        logger.info("账户中心", "acc-app", "充值", signStr);
    }
}
