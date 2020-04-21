package com.shiro.demo.shiro;

import com.shiro.demo.entity.User;
import com.shiro.demo.service.impl.UserServiceImpl;
import com.shiro.demo.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/21 14:48
 */
public class JWTShiroRealm extends AuthorizingRealm {
    private final Logger log = LoggerFactory.getLogger(JWTShiroRealm.class);

    @Autowired
    public UserServiceImpl userService;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        JWTToken jwtToken = (JWTToken) authcToken;
        String token = (String) authcToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        token = getAuthzHeader(token);
        String username = JwtUtils.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        //通过username从数据库中查找 ManagerInfo对象
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new AuthenticationException("用户不存在!");
        }

        if (!JwtUtils.verify(token, username, user.getSalt())) {
            throw new AuthenticationException("Token认证失败");
        }
        jwtToken.setHost(user.getUserName());
        //第一个token是存在shiro缓存中的信息。
        return new SimpleAuthenticationInfo(jwtToken, token, getName());
    }

    protected String getAuthzHeader(String token) {
        return org.apache.commons.lang3.StringUtils.removeStart(token, "Bearer");
    }

    /**
     * 此方法调用hasRole,hasPermission的时候才会进行回调.
     * <p>
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println(principals + "进行授权操作");
        /*
         * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行，
         * 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
         * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了，
         * 缓存过期之后会再次执行。
         */
        System.out.println("-------------------------------------------------------------------------");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        String token =  principals.getPrimaryPrincipal().toString();
//        token=getAuthzHeader(token);
//        String username = JwtUtils.getUsername(token);
//        UserAdmin user = userAdminService.getJwtTokenInfo(username);
//        List<String> roles = user.getRoles();
//        if(roles == null) {
//            roles = userAdminService.getUserAdminRoles(user.getId());
//            user.setRoles(roles);
//            simpleAuthorizationInfo.addRoles(roles);
//        }
//        List<UserPermission> userPermissionList = new ArrayList<>();
//        if (roles != null){
//            List<String> rolesName = new ArrayList<>();
//            for(String category :roles){
//                UserRole role = userRoleService.findUserRoleByCategory(category);
//                if(null != role){
//                    List<UserPermission> userPermissions = userPermissionService.findAuthoritiesByRoleId(role.getId());
//                    for(UserPermission p: userPermissions){
//                        if(StringUtil.isEmpty(p.getValue())){
//                            continue;
//                        }
//                        simpleAuthorizationInfo.addStringPermission(p.getValue());
//                    }
//                    userPermissionList.addAll(userPermissions);
//                }
//            }
//            simpleAuthorizationInfo.addRoles(rolesName);
//        }
//        if(null ==userPermissionList || userPermissionList.size()==0){
//            return null;
//        }
        // simpleAuthorizationInfo.addStringPermission("user:query");
        return simpleAuthorizationInfo;
    }
}
