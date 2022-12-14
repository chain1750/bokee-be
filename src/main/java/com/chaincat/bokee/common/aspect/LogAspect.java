package com.chaincat.bokee.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.chaincat.bokee.common.data.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * 日志切面
 *
 * @author Chain
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Value("${aspect.log.exclude-params}")
    private List<String> excludeParams;

    /**
     * 切点，所有controller
     */
    @Pointcut(value = "execution(public * com.chaincat.bokee.controller..*Controller.*(..))")
    public void controllerPointcut() {
    }

    /**
     * 前置通知
     *
     * @param joinPoint 连接点
     */
    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getExcludes().addAll(excludeParams);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("request url:       {}", request.getRequestURL().toString());
        log.info("request method:    {}", request.getMethod());
        log.info("request remote:    {}", request.getRemoteAddr());
        log.info("request implement: {}.{}", signature.getDeclaringTypeName(), signature.getName());
        log.info("request param:     {}", JSON.toJSONString(joinPoint.getArgs(), filter));
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * 正常返回通知
     *
     * @param joinPoint 连接点
     * @param result  统一返响应体
     */
    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void doAfter(JoinPoint joinPoint, Result<?> result) {
        Signature signature = joinPoint.getSignature();
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("response implement: {}.{}", signature.getDeclaringTypeName(), signature.getName());
        log.info("response result:    {}", JSON.toJSONString(result));
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
