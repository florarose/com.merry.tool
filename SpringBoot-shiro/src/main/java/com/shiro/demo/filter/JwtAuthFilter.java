package com.shiro.demo.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.shiro.demo.shiro.JWTToken;
import com.shiro.demo.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:48
 */
public class JwtAuthFilter extends AuthenticatingFilter {
    private static final int tokenRefreshInterval = 300;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

//    todo
//    @Autowired
//    protected UserServiceImpl userService;


    public JwtAuthFilter() {

    }

    //todo
//    public JwtAuthFilter(UserServiceImpl userService) {
//        this.userService = userService;
//    }
    //判断是否options，请求
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //对于OPTION请求做拦截，不做token校验
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 跨域设置
     */
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) {
        this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
        request.setAttribute("authth", true);
    }

    /**
     * 父类会在请求进入拦截器后调用该方法，返回true则继续，返回false则会调用onAccessDenied()。这里在不通过时，还调用了isPermissive()方法。
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //如果是登录请求，直接跳过去,不进行token的验证,这里根据请求的接口地址来判断，在shiroConfig中添加
        // shiroFilter.setLoginUrl("/api/user/login");设置默认的登录接口,自己登录的接口，替换掉原来的login.jsp
        if (this.isLoginRequest(request, response)) {
            return true;
        }
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allowed || super.isPermissive(mappedValue);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        boolean flag = true;
        AuthenticationToken token = createToken(request, response);
        JWTToken jwtToken = null;
        if (null == token) {
            throw new IllegalStateException();
        } else {
            try {
                if (token instanceof JWTToken) {
                    jwtToken = (JWTToken) token;
                    // 提交给realm进行登入，如果错误他会抛出异常并被捕获
                    Subject subject = getSubject(request, response);
                    subject.login(jwtToken);
                    flag = onLoginSuccess(token, subject, request, response);
                }
            } catch (AuthenticationException e) {
                flag = onLoginFailure(token, e, request, response);
            }
        }
        return flag;
    }

    /**
     * 66行  executeLogin(request, response);  执行成功
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if (token instanceof JWTToken) {
            JWTToken jwtToken = (JWTToken) token;
            String username = JwtUtils.getUsername(jwtToken.getToken());
            boolean shouldRefresh = shouldTokenRefresh(JwtUtils.getIssuedAt(jwtToken.getToken()));
            if (shouldRefresh) {
                //todo
//                newToken = userService.generateJwtToken(username);
            }
        }
        if (StringUtils.isNotBlank(newToken)) {
            httpResponse.setHeader("token", newToken);
        }
        return true;
    }

    /**
     * 66行  executeLogin(request, response);  执行失败
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        logger.error("JwtAuthFilter  Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }

    /**
     * 重写 66 行 executeLogin(request, response) 中创建token的方法
     *
     * @param servletRequest
     * @return
     */
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String jwtToken = getAuthzHeader(servletRequest);
        if (StringUtils.isNotBlank(jwtToken) && !JwtUtils.isTokenExpired(jwtToken)) {
            return new JWTToken(jwtToken);
        }
        return null;
    }

    protected String getAuthzHeader(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        String header = httpRequest.getHeader("token");
        return StringUtils.removeStart(header, "Bearer");
    }

    protected boolean shouldTokenRefresh(Date issueAt) {
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().minusSeconds(tokenRefreshInterval).isAfter(issueTime);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }

    protected void fillCorsHeader(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,x-requested-with,Authorization,token");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Accept-Ranges", "byte");
    }


}
