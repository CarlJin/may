package wss.log;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @ClassName DuplicateKeyEventHandler.java
 * @Description 
 * @author shiyongjun
 * @date 2017年10月17日 下午3:38:29
 */
@Aspect
@Component
public class DuplicateKeyEventHandler implements Ordered {

	private final static Logger logger = LoggerFactory.getLogger(DuplicateKeyEventHandler.class);

	/*@Around("@annotation(wss.log.DuplicatePrimaryKeyExceptionCatcher)")
	public Object duplicatePrimaryKeyExceptionCatcher(ProceedingJoinPoint pjp) throws Throwable {
		try {
			return pjp.proceed();
		} catch (DuplicateKeyException e) {
			Object o = pjp.getArgs()[0];
			Method m = o.getClass().getMethod("getProofId");
			Object proofId = m == null ? null : m.invoke(o);
			if (e.getCause().getMessage().contains("PRIMARY")) {
				logger.error("[凭证表主键{}冲突异常{}]", proofId, e.getMessage());
				throw new BizFailException(PRIMARY_KEY_EXIST, PropertyUtil.getMessage(PRIMARY_KEY_EXIST));
			}
			throw e;
		}
	}

	@Around("@annotation(wss.log.OptimisticLockExceptionCatcher)")
	public Object optimisticLockExceptionCatcher(ProceedingJoinPoint pjp) throws Throwable {
		try {
			return pjp.proceed();
		} catch (DuplicateKeyException e) {
			Object o = pjp.getArgs()[0];
			Method m = o.getClass().getMethod("getProofId");
			Object proofId = m == null ? null : m.invoke(o);
			if (e.getCause().getMessage().contains("PRIMARY")) {
				logger.error("[凭证表主键{}冲突异常{}]", proofId, e.getMessage());
				throw new BizFailException(PRIMARY_KEY_EXIST, PropertyUtil.getMessage(PRIMARY_KEY_EXIST));
			} else {
				logger.error("[唯一性约束{}冲突异常{}]", proofId, e.getMessage());
				throw new BizFailException(PROOF_ID_IS_EXIST, PropertyUtil.getMessage(PROOF_ID_IS_EXIST));
			}
		}
	}*/

	@Override
	public int getOrder() {
		// 优先级高于事务(1000左右)
		return 100;
	}

}