package wss.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import wss.vo.Persion;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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

        Annotation[] annotations = name.getAnnotations();

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

    @Test
    public void reflect(){
        try {
            //创建类
            Class<?> class1 = Class.forName("wss.vo.Persion");

            //创建实例
            Object person = class1.newInstance();

            //获得id 属性
            Field idField = class1.getDeclaredField( "id" ) ;

            //打破封装  实际上setAccessible是启用和禁用访问安全检查的开关,并不是为true就能访问为false就不能访问
            //由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的
            idField.setAccessible( true );

            //给id 属性赋值
            idField.set(  person , "100") ;

            //获取 setName() 方法
            Method setName = class1.getDeclaredMethod( "setName", String.class ) ;
            //打破封装
            setName.setAccessible( true );

            //调用setName 方法。
            setName.invoke( person , "jack" ) ;

            //获取name 字段
            Field nameField = class1.getDeclaredField( "name" ) ;
            //打破封装
            nameField.setAccessible( true );

            //打印 person 的 id 属性值
            String id_ = (String) idField.get( person ) ;
            System.out.println( "id: " + id_ );

            //打印 person 的 name 属性值
            String name_ = ( String)nameField.get( person ) ;
            System.out.println( "name: " + name_ );

            //获取 getName 方法
            Method getName = class1.getDeclaredMethod( "getName" ) ;
            //打破封装
            getName.setAccessible( true );

            //执行getName方法，并且接收返回值
            String name_2 = (String) getName.invoke( person  ) ;
            System.out.println( "name2: " + name_2 );

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace() ;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
