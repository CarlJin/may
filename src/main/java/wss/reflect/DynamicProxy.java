package wss.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-12-11 上午11:11
 **/
public class DynamicProxy implements InvocationHandler {
    public static final Logger logger = LoggerFactory.getLogger(DynamicProxy.class);

    private Object subject;

    public DynamicProxy() {

    }

    public DynamicProxy(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("方法之前");

        Object invoke = method.invoke(subject, args);

        logger.info("方法之后");
        return invoke;
    }
}

