package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类，实现公共字段填充
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill))")
    public void autoFillPointCut() {}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始公共字段填充");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解对象
        OperationType value = autoFill.value();//获得操作类型

        //获得参数对象实体
        Object[] args = joinPoint.getArgs();
        if(args == null && args.length == 0){
            return;
        }
        Object entity = args[0];

        //准备赋值数据
        LocalDateTime localDateTime = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        if(value == OperationType.INSERT){
            try{
                Method setCreatTime =entity.getClass().getDeclaredMethod("setCreateTime",LocalDateTime.class);
                Method setCreatUser =entity.getClass().getDeclaredMethod("setCreateUser",Long.class);
                Method setUpdateTime =entity.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
                Method setUpdateUser =entity.getClass().getDeclaredMethod("setUpdateUser",Long.class);

                setCreatTime.invoke(entity,localDateTime);
                setUpdateTime.invoke(entity,localDateTime);
                setUpdateUser.invoke(entity,id);
                setCreatUser.invoke(entity,id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(value == OperationType.UPDATE){
            try {
                Method setUpdateTime =entity.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
                Method setUpdateUser =entity.getClass().getDeclaredMethod("setUpdateUser",Long.class);
                setUpdateTime.invoke(entity,localDateTime);
                setUpdateUser.invoke(entity,id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
