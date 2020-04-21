package com.shiro.demo.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:48
 */
public class ShiroPermissionsFilter extends PermissionsAuthorizationFilter {


    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) { //没有角色限制，有权限访问
            return true;
        }
        for (String role : rolesArray) {
            //若当前用户是rolesArray中的任何一个，则有权限访问\
            if (subject.isPermitted(role)) {
                return true;
            }

        }
        return false;
    }

    /**
     * shiro认证perms资源失败后回调方法
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        PrintWriter printWriter = null;
        try {
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json");
            printWriter = servletResponse.getWriter();
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            printWriter.write("{\"error\": \"没有权限\"}");
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
        return false;
    }
}
