package zx.cn.comm.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zx.cn.comm.exception.ZxException;
import zx.cn.comm.pojo.ResultDO;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * 全局异常处理
 * Created by huangYi on 2018/7/9
 * 如果单使用@ExceptionHandler，只能在当前Controller中处理异常。
 * 但当配合@ControllerAdvice一起使用的时候，就可以摆脱那个限制了。
 *   @ControllerAdvice 控制器增强
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     *JSR和Hibernate validator的校验只能对Object的属性进行校验，不能对单个的参数进行校验，
     * spring 在此基础上进行了扩展，添加了MethodValidationPostProcessor拦截器，可以实现对方法参数的校验
     * @return
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @ExceptionHandler
    public ResultDO handle(ZxException e){
        log.error("控制层捕获ZX异常 GlobalBangerException,[{}]", e.getCause());
        return new ResultDO("0",e.getMessage());
    }
    @ExceptionHandler
    public ResultDO handle(InternalAuthenticationServiceException e){
        log.error(e.getMessage(),e);
        return new ResultDO("0",e.getMessage());
    }

    @ExceptionHandler
    public ResultDO handle(BadCredentialsException e){
        log.error("坏的凭证",e);
        return new ResultDO("0","用户名或密码错误");
    }

    @ExceptionHandler
    public ResultDO handle(AccessDeniedException e){
        log.error("权限不足",e);
        return new ResultDO("0","权限不足");
    }

    @ExceptionHandler
    public ResultDO handle(Exception e){
        log.error("其他异常",e);
        return new ResultDO("0","系统异常");
    }

    @ExceptionHandler
    public ResultDO handle(ArrayIndexOutOfBoundsException e){
        log.error("数组越界",e);
        return new ResultDO("0","数组越界异常");
    }

    @ExceptionHandler
    public ResultDO handle(NullPointerException e){
        log.error("空指针异常",e);
        return new ResultDO("0","空指针异常");
    }

    /**
     * 没使用BindingResult接受会抛出这种异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResultDO handle(BindException e){
        log.error("绑定参数异常",e);
        return new ResultDO("0",e.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler
    public ResultDO handle(HttpRequestMethodNotSupportedException exception) {
        log.error("控制层捕获请求方式异常,[{}]", exception);
        return new ResultDO("0","请求方式不正确");
    }

    @ExceptionHandler
    public ResultDO handle(MissingServletRequestParameterException exception) {
        log.error("参数未提交",exception);
        return new ResultDO("0","参数未提交");
    }

    @ExceptionHandler
    public ResultDO handle(ValidationException exception) {
        if(exception instanceof ConstraintViolationException){
            ConstraintViolationException ex=(ConstraintViolationException)exception;
            Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
            if(!CollectionUtils.isEmpty(set)){
                ConstraintViolation<?> next = set.iterator().next();
                log.error("控制层捕获入参异常,[{}]",ex);
                return new ResultDO("0",next.getMessageTemplate());
            }
        }else{
            log.error("控制层捕获入参异常,[{}]",exception);
        }
        return new ResultDO("0","请求参数不正确");
    }
}
