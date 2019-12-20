package zx.cn.comm.advice;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import zx.cn.comm.pojo.ResultDO;

/**
 * Created by huangYi on 2018/8/18
 * 全局返回对象增强类,进行统一封装
 **/
@ControllerAdvice
public class ZxResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        /*针对swagger actuator请求不对其拦截*/
        String path=serverHttpRequest.getURI().getPath();
        if (path.contains("/swagger")||path.contains("actuator")) {
            log.info("返回参数={}", JSONObject.toJSON(o));
            return o;
        }
        /*从Logback中获取requestId*/
        String requestId = null;
        try {
            requestId = MDC.get("requestId");
            MDC.clear();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        if (o instanceof ResultDO) {
            ((ResultDO) o).setRequestId(requestId);
            log.info("返回参数={}", JSONObject.toJSON(o));
            return o;
        } else {

            ResultDO res= new ResultDO(requestId,"200","成功",o);

            log.info("返回参数={}", JSONObject.toJSON(res));
            /*重写StringMessageConverter的解析器则不需要这样转 WebMvcConfig下的configureMessageConverters 已重写*/
            return res;

        }
    }
}
