package com.error.demo.api;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/22 16:47
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="page")
public class ErrorPageRest {
    @ResponseBody
    @GetMapping(path = "divide", produces = "application/json;charset=UTF-8")
    public int divide(@RequestParam  int dub) {
        System.out.println("divide1");
        return 1000 / dub;
    }

}
