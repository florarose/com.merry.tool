package com.error.demo.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 处理404,500异常
 * @author liudongting
 * @date 2019/7/3 15:47
 */
@RestController
public class ErrorController extends BasicErrorController {


    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        //自定义的错误信息类
        //status.value():错误代码，
        //body.get("message").toString()错误信息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", status.value());
        jsonObject.put("msg", body.get("message").toString());
        //TokenException Filter抛出的自定义错误类
        return new ResponseEntity<>(jsonObject, status);
    }

    @Override
    protected ErrorProperties getErrorProperties() {
        return new ErrorProperties();
    }

    @Override
    public String getErrorPath() {
        System.out.println("异常处理来了");
        return "error/error";
    }

}
