package wss.reflect.Annotation;

import java.lang.annotation.*;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-12-11 下午2:11
 **/
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //次注解只能作用于方法上
public @interface MethodAnnotation {

    String desc() default "method1";
}