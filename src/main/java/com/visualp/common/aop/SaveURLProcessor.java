package com.visualp.common.aop;

import com.visualp.common.util.SaveURL;
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
public class SaveURLProcessor {

    public static final String SAVE_URL = "FOURFREE_SAVE_URL";

    @Around("@annotation(com.visualp.common.util.SaveURL)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature method = (MethodSignature) joinPoint.getSignature();
        SaveURL annotation = method.getMethod().getAnnotation(SaveURL.class);

        if (annotation != null) {
            String url = getCurrentRequest().getRequestURI();
            if (!(getCurrentRequest().getQueryString() == null || getCurrentRequest().getQueryString().isEmpty())) {
                url = url + "?" + getCurrentRequest().getQueryString();
            }
            getCurrentRequest().getSession().setAttribute(SaveURLProcessor.SAVE_URL, url);
        }

        return joinPoint.proceed();
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return sra.getRequest();
    }

}
