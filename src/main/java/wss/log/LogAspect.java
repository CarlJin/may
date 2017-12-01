package wss.log;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * @desc:
 * @author: jintao
 * @date: 2017-11-27 下午2:19
 **/
@Aspect
@Component
@Order(value = Integer.MAX_VALUE)
public class LogAspect {

    private static final Logger LogAspectLog = LoggerFactory.getLogger(LogAspect.class);

    @Before(value = "execution(* wss.*.*(..)) && @annotation(desc)")
    public void printLog(JoinPoint pjp, MethodDesc desc) {

        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        String methodDesc = desc.desc();

        String classType = pjp.getTarget().getClass().getName();
        try {
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, args);

            Field loggerField = clazz.getDeclaredField("logger");

            loggerField.setAccessible(true);

            Logger logger = (Logger) loggerField.get(null);
            logger.info(methodName, "[" + methodDesc + "]:" + nameAndArgs.toString());
        } catch (Exception e) {
            LogAspectLog.error("打印方法{}异常，异常信息{}", methodName, e.toString());
        }

    }


    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        // String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }

        return map;
    }
}