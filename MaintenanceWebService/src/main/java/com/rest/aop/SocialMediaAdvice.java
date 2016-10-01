package com.rest.aop;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * Aspect class
 * 
 * @author vinayaksm
 *
 */

// @Aspect
@Component
public class SocialMediaAdvice {
	private static final Logger LOGGER = Logger.getLogger(SocialMediaAdvice.class);

	/**
	 * Aspect get executed around the all method calls
	 * 
	 * @param points
	 * @return
	 * @throws Throwable
	 */
	// @Around("execution(* com.rest.*.*.*(..))")
	public Object logMethodCall(final ProceedingJoinPoint points) throws Throwable {
		LOGGER.info("******Start executing :" + points.getSignature() + "*******");
		final long startTime = Calendar.getInstance().getTimeInMillis();
		final Object value = points.proceed();
		final long endTime = Calendar.getInstance().getTimeInMillis();
		LOGGER.info("Time taken To execute: " + (endTime - startTime) + " Milliseconds");
		LOGGER.info("******* End executing :" + points.getSignature() + "*********");
		return value;
	}

}
