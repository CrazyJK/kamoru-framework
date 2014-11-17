package jk.kamoru.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 수행시간 측정 advice bean
 * 
 * <p> 메서드의 수행 시간을 millisecond단위로 측정하여 debug log를 남긴다.<br>
 * 메서드 선정은 pointcut 표현식으로 결정된다.
 * <pre>
 * 	&lt;aop:config&gt;
 *		&lt;aop:advisor advice-ref="elapsedTimeAdvice" pointcut="execution(* jk.kamoru.*..*Source.reload())"/&gt;
 *	&lt;/aop:config&gt;
 *	&lt;bean id="elapsedTimeAdvice" class="{@link jk.kamoru.spring.ElapsedTimeAdvice}" /&gt;
 * </pre>
 * @author kamoru
 *
 */
public class ElapsedTimeAdvice implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ElapsedTimeAdvice.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long t1 = System.currentTimeMillis();
		
		invocation.proceed();
		
		long   elapsedTime = System.currentTimeMillis() - t1;
		String   className = invocation.getThis().getClass().getSimpleName();
		String  methodName = invocation.getMethod().getName();
		
		logger.info("{}.{} Elapsed time : {} ms", className, methodName, elapsedTime);
		return null;
	}

}
