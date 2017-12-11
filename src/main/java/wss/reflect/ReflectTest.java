package wss.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import sun.font.TrueTypeFont;
import wss.thread.LogThread.LogThread;
import wss.vo.Persion;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-12-11 上午9:49
 **/
public class ReflectTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectTest.class);

    @Test
    public void reflectTest() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Persion persion = new Persion();
        Class<?> aClass = persion.getClass();
        Class<?> bClass = persion.getClass();
        logger.info("" + (aClass == bClass));

        Constructor<?> constructor = aClass.getConstructor();

        constructor.setAccessible(true);
        Object obj = constructor.newInstance();

        Constructor<?> constructor1 = aClass.getConstructor(String.class, Integer.class, String.class);
        Object o = constructor1.newInstance("carl", 26, "man");

        /**
         * 方式1获取属性值
         */
        Field name = aClass.getDeclaredField("name");
        name.setAccessible(true);
        Object o1 = name.get(o);
        logger.info(o1.toString());

        /**
         * 方式2获取属性值
         */
        Method setName = aClass.getDeclaredMethod("setName", String.class);
        Object bob = setName.invoke(o, "bob");
        logger.info("" + bob);
        name = aClass.getDeclaredField("name");
        name.setAccessible(true);
        o1 = name.get(o);
        logger.info(o1.toString());
    }
}
