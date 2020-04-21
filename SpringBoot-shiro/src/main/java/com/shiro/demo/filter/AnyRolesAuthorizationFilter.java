package com.shiro.demo.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:48
 */
@Slf4j
public class AnyRolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) {
        request.setAttribute("anyRolesAuthFilter.FILTERED", true);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        Boolean afterFiltered = (Boolean) (servletRequest.getAttribute("anyRolesAuthFilter.FILTERED"));
        if (BooleanUtils.isTrue(afterFiltered))
            return true;

        Subject subject = getSubject(servletRequest, servletResponse);
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) { //没有角色限制，有权限访问
            return true;
        }
        for (String role : rolesArray) {
            //若当前用户是rolesArray中的任何一个，则有权限访问
            if (subject.hasRole(role)) {
                return true;
            }
        }
        log.error("没有权限");
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String nn= servletRequest.getHeader("token");
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        //处理跨域问题，跨域的请求首先会发一个options类型的请求
        if (servletRequest.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }
        boolean isAccess = isAccessAllowed(request, response, mappedValue);
        if (isAccess) {
            return true;
        }
        servletResponse.setCharacterEncoding("UTF-8");
        Subject subject = getSubject(request, response);
        PrintWriter printWriter = servletResponse.getWriter();
        servletResponse.setContentType("application/json;charset=UTF-8");
        servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"));
        servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        servletResponse.setHeader("Vary", "Origin");
        String respStr = null;
        if (subject.getPrincipal() == null) {
//            respStr = JSONObject.toJSONString();
        } else {
//            respStr = JSONObject.toJSONString(new ResponseModel(403, "您没有此权限，请联系管理员"));
        }
        printWriter.write(respStr);
        printWriter.flush();
        servletResponse.setHeader("content-Length", respStr.getBytes().length + "");
        return false;
    }

}
