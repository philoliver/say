package com.say.rest.aspect;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.say.rest.annotation.RequiresPermission;

@Component
@Aspect
public class AuthorizeAspect {
	@Autowired
    private HttpServletRequest request;

	@Value("${passphrase}")
	private String passphrase;

	private static final String AUTH_TYPE = "USERLESS-PASSPH ";

	@Before("execution(* in.neptune.say.rest.*.*(..))")
    public void invoke(JoinPoint joinPoint)
    {
		Annotation[] annotations = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotations();
		for(Annotation annotation: annotations){
			if( annotation instanceof RequiresPermission ){
				String authHeader = request.getHeader("authorization");
				String passphrase= extractPassphrase(authHeader);

				if( !validatePassphrase(passphrase) ){
					throw new IllegalArgumentException("Wrong passphrase.");
				}
			}
		}
    }

	private String extractPassphrase(String authHeader){
		if( authHeader != null && authHeader.contains(AUTH_TYPE) ){
			return authHeader.substring(AUTH_TYPE.length()+1, authHeader.length()-1);
		}else{
			throw new IllegalArgumentException("Authentication type not supported.");
		}
	}

	private boolean validatePassphrase(String passphrase){
		return this.passphrase.trim().toLowerCase().equals(passphrase.trim().toLowerCase());
	}
}