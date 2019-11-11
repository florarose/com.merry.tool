package com.study.thread.methosd;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.study.thread.mapper.TScheduledTaskMapper;
import com.study.thread.model.TScheduledTask;
import com.study.thread.service.impl.TScheduledTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/6 15:10
 */
@Component("demoTask")
public class DemoTask {

    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskMapper tScheduledTaskService;
    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskServiceImpl tScheduledTaskServiceImpl;

    @Async
    public void taskWithParams(String param1, Integer param2) {

        tScheduledTaskServiceImpl.updateTaskSchedule(param2,1);
        String sss=  HttpUtil.sendGet("http://192.168.31.202:8177");
        TScheduledTask tScheduledTask = tScheduledTaskService.selectById(param2);
        JSONObject jsonObject = JSONObject.parseObject(sss);
        StringBuilder sb = new StringBuilder();
        if(null != jsonObject){
            System.out.println(jsonObject);
            JSONArray jj= jsonObject.getJSONArray("data");
            System.out.println(jj);
            Iterator<Object> js =  jj.iterator();
            while (js.hasNext()){
                JSONObject jsonObjects =(JSONObject) js.next();
                System.out.println(jsonObjects.getString("title"));
                sb.append("「");
                sb.append(jsonObjects.getString("title"));
                sb.append("」");
                sb.append("---");
                sb.append("「」");
                sb.append(jsonObjects.getString("content"));
                sb.append("」");
            }
        }
        tScheduledTask.setStatusName(sb.toString());
        tScheduledTaskServiceImpl.updateById(tScheduledTask);
        System.out.println("接受到的信息：" + sss);
        System.out.println("这是有参示例任务：" + param1 + param2);
    }

    public void taskNoParams() {
        System.out.println("这是无参示例任务");
    }
    public void taskNoParamss() {
        System.out.println("这是无参示例任务");
    }
}