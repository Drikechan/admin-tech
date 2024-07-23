package org.tech.springcode.aspect;


import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.support.MultipartFilter;
import org.tech.springcode.properties.LoggingProperties;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author by: 神秘的鱼仔
 * @ClassName: WhiteListLogAspect
 * @Description:
 * @Date: 2024/6/19 19:50
 */
@Slf4j
@Aspect
@Component
public class WhiteListLogAspect {

    @Autowired
    private LoggingProperties loggingProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Before("execution(* org.tech.springcode.controller..*(..))")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        if (shouldLog(requestURI)) {
            addLog(joinPoint,"",0);
        }
    }

    /**
     * 日志记录入库操作
     */
    public void addLog(JoinPoint joinPoint, String outParams, long time) {
        HttpServletRequest request = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        log.info("\n\r=======================================\n\r" +
                        "请求地址:{} \n\r" +
                        "请求方式:{} \n\r" +
                        "请求类方法:{} \n\r" +
                        "请求方法参数:{} \n\r" +
                        "返回报文:{} \n\r" +
                        "处理耗时:{} ms \n\r" +
                        "=======================================\n\r",
                request.getRequestURI(),
                request.getMethod(),
                joinPoint.getSignature(),
                JSONUtil.toJsonStr(filterArgs(joinPoint.getArgs())),
                outParams,
                String.valueOf(time)
        );
    }

    /**
     * 过滤参数
     * @param args
     * @return
     */
    private List<Object> filterArgs(Object[] args) {
        return Arrays.stream(args).filter(object -> !(object instanceof MultipartFilter)
                && !(object instanceof HttpServletRequest)
                && !(object instanceof HttpServletResponse)
        ).collect(Collectors.toList());
    }

    private boolean shouldLog(String requestURI) {
        return loggingProperties.getIncludePaths().stream().anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }
}


