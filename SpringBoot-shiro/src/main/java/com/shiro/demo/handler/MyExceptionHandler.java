package com.shiro.demo.handler;


import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.o;

/**
 * spring mvc 从容器中获取了处理异常的HandlerExceptionResolver，spring mvc提供三种异常处理器：
 * DefaultHandlerExceptionResolver， ResponseStatusExceptionResolver， ExceptionHandlerExceptionResolver
 */
/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:48
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
    private static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception e) {
        // 异常处理逻辑 goes here
        logger.error("got exception: ", e);
        Writer out = null;
        try {
            resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            out = resp.getWriter();
            //判断是不是JsonResult
            out.write(JSON.toJSONString(wrapper(e, o)));
        } catch (IOException ex) {
            logger.warn("输出异常", ex);
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException eo) {
                    eo.printStackTrace();
                }
            }
        }
        return new ModelAndView();

    }

    private ResponseEntity wrapper(Exception e, Object o) {
          return new ResponseEntity(null);
    }
}
