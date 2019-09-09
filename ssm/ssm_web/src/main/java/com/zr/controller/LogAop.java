package com.zr.controller;

import com.zr.domain.SysLog;
import com.zr.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ISysLogService sysLogService;

    private Date visitTime; //开始访问时间
    private Class clazz;    //访问类
    private Method method;  //访问方法

    /**
     * 配置前置通知：
     * 1、开始访问时间
     * 2、执行的类
     * 3、执行的方法
     * @param jp
     */
    @Before("execution(* com.zr.controller.*.*(..))")   //拦截controller下的所有方法
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        //1、开始访问时间
        visitTime = new Date();
        //2、访问类
        clazz = jp.getTarget().getClass();
        String methodName = jp.getSignature().getName(); //获取访问的方法的名称
        Object[] args = jp.getArgs();//获取访问的方法的参数

        //3、获取具体执行的方法的Method对象
        if (args == null || args.length == 0) {
            method = clazz.getMethod(methodName); //只能获取无参数的方法
        } else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            clazz.getMethod(methodName, classArgs);
        }
    }

    /**
     * 配置后置通知：
     * 1、访问时长
     * 2、获取url
     * 3、获取访问的ip
     * 4、获取当前操作的用户
     * @param jp
     */
    @After("execution(* com.zr.controller.*.*(..))")   //拦截controller下的所有方法
    public void doAfter(JoinPoint jp) throws Exception {
        //1、获取访问时长
        long time = new Date().getTime() - visitTime.getTime();
        //2、获取url：获取/product/find.do
        String url = "";
        if (clazz != null && method != null && clazz != LogAop.class) {
            //2.1获取类上的@RequestMapping("/orders")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation != null) {
                String[] classValue = classAnnotation.value();
                //2.2获取方法上的@RequestMapping(xxx)
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];

                    //3、获取访问的ip
                    String ip = request.getRemoteAddr();

                    //4、获取当前操作的用户
                    SecurityContext context = SecurityContextHolder.getContext();//从上下文中获了当前登录的用户
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    //将日志相关信息封装到SysLog对象
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time); //执行时长
                    sysLog.setIp(ip);
                    sysLog.setMethod("[类名] " + clazz.getName() + "[方法名] " + method.getName());
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(visitTime);

                    //调用Service完成操作
                    sysLogService.save(sysLog);
                }
            }
        }
    }
}