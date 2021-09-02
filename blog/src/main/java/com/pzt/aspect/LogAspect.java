package com.pzt.aspect;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志切面aop动态代理 流上切一刀
 *
 * @author 蒲正庭 on 2021/7/29
 */
//@Aspect：切面   @Component：组件扫描
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //切面 web下面的所有类的所有方法的所有参数
    @Pointcut("execution(* com.pzt.web.*.*(..))")
    public void log() {
    }

    //所有方法执行之前  通过joinpoint获取类和方法对象
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + '.' + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);

        logger.info("Request : {}",requestLog);
    }

    //所有方法执行之后
    @After("log()")
    public void doAfter() {
//        logger.info("---------doAfter----------");
    }

    //返回的值 切面
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result: {}" + result);
    }

    //记录请求日志
    private class RequestLog {
        private String url;//地址
        private String ip;//ip
        private String classMethod;//类和方法
        private Object[] args;//请求的参数

        //全参构造
        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        //记录日志后转换成字符串
        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
