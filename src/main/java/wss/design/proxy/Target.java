package wss.design.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-11-30 上午9:55
 **/
public class Target implements Sourceable{
    private static Logger logger = LoggerFactory.getLogger(Target.class);
    @Override
    public void method() {
        logger.info("Target--目标方法");
    }
}
