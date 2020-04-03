package com.study.websocket.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/2 10:44
 */
@RestController
@RequestMapping(value="/websocket")
public class WebSendController {


    @RequestMapping(value="/sendToOne", produces = {"application/json; charset=utf-8"},method= RequestMethod.GET)
    public ResponseEntity sendToOne(HttpServletRequest request, String message){
        String str = message;
        try {
            WebSocketServer.sendInfo(str);
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ResponseEntity(str,HttpStatus.OK);
    }
}
