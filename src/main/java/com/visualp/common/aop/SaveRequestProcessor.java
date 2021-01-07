package com.visualp.common.aop;

import com.visualp.common.util.SaveRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SaveRequestProcessor {

    public static final String SAVE_REQUEST = "FOURFREE_SAVE_REQUEST_URL";

    @Around("@annotation(com.visualp.common.util.SaveRequest)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        SaveRequest annotation = method.getMethod().getAnnotation(SaveRequest.class);

        if (annotation != null) {
            if (getCurrentRequest().getQueryString() == null || getCurrentRequest().getQueryString().isEmpty())
                getCurrentRequest().getSession().setAttribute(SaveRequestProcessor.SAVE_REQUEST, "");
            else
                getCurrentRequest().getSession().setAttribute(SaveRequestProcessor.SAVE_REQUEST, "?" + getCurrentRequest().getQueryString());
        }
        return joinPoint.proceed();
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getRequest();
    }
}