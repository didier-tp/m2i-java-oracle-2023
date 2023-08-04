package com.inetum.appliSpringWeb.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("perf") //pour que cet aspect ne soit activé qu'avec
//le profile "perf" au démarrage du main ou des tests
//via @ActiveProfiles({"perf", "profil2QueJaime"}) ou autre
public class MyPerfLogAspect {
	
	@Pointcut("execution(* com.inetum.appliSpringWeb.service.*.*(..))")
	public void servicePointcut(){
	}
	
	@Pointcut("execution(* com.inetum.appliSpringWeb.dao.*.*(..))")
	public void daoPointcut(){
	}
	
	@Pointcut("execution(* com.inetum.appliSpringWeb.rest.*.*(..))")
	public void restPointcut(){
	}
	
	Logger logger = LoggerFactory.getLogger(MyPerfLogAspect.class);
	
	//@Around("servicePointcut() || daoPointcut()")
	@Around("servicePointcut()")
	public Object doPerfLog(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug("<< trace == debut == " 
	                + pjp.getSignature().toLongString() + " <<");
		long td = System.nanoTime();
		
		Object objRes = pjp.proceed();
		
		long tf = System.nanoTime();
		logger.debug(
				">> trace == fin == " 
		        + pjp.getSignature().toShortString() 
		        + " [" + (tf - td) / 1000000.0 + " ms] >>");
		return objRes;
	}
}
