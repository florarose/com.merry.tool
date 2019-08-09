package com.json.demo.api;

import com.json.demo.common.ResponseCode;
import com.json.demo.common.ResponseModel;
import com.json.demo.entity.UserAdminView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liudongting
 * @date 2019/8/9 17:50
 */
@RequestMapping("api/user")
@RestController
public class UserApi {

    /**
     * 自己设定了返回值的用这个
     * @return
     */
    @RequestMapping(value = "/listUser",method = RequestMethod.POST)
    public ResponseModel listUser(){
        UserAdminView userAdminView = new UserAdminView();
        return new ResponseModel(ResponseCode.OK,userAdminView);
    }

    /**
     * 如果直接返回UserAdminView ，不需要在ResponseModel 中设置以下内容
     *   @JsonView(value = View.Base.class )
     *   private T data;
     * @return
     */

    @RequestMapping(value = "/listUser2",method = RequestMethod.POST)
    public UserAdminView listUser2(){
        UserAdminView userAdminView = new UserAdminView();
        return userAdminView;
    }
}
