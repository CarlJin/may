package wss.design.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-11-29 下午4:37
 **/
public class Proxy implements Sourceable {
    private static Logger logger = LoggerFactory.getLogger(Proxy.class);

    private Target target;

    public Proxy() {
        this.target = new Target();
    }

    @Override
    public void method() {
        before();
        target.method();
        after();
    }

    private void before() {
        logger.info("代理前置");
    }

    private void after() {
        logger.info("代理后置");
    }
}
