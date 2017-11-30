package design.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wss.design.Proxy.Proxy;
import wss.design.Proxy.Sourceable;
import wss.design.Proxy.Target;

import java.io.IOException;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-11-30 上午9:56
 **/
public class MainTest {

    private static Logger logger = LoggerFactory.getLogger(Target.class);

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Prototype prototype = new Prototype();
        Object clone = prototype.clone();
        Object deepClone = prototype.deepClone();

        if (clone == prototype) {
            logger.info("浅复制：将一个对象复制后，基本数据类型的变量都会重新创建，而引用类型，指向的还是原对象所指向的。");
        }
        if (prototype == deepClone) {

        } else {
            logger.info("深复制：将一个对象复制后，不论是基本数据类型还有引用类型，都是重新创建的。简单来说，就是深复制进行了完全彻底的复制，而浅复制不彻底。");
        }

    }
}
