package com.redis.study.auth;

import com.redis.study.pojo.AuthUser;
import com.redis.study.pojo.User;
import com.redis.study.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

/**
 * 密码模式的用户及权限存到了数据库
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/12 15:20
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 实现UserDetailsService中的loadUserByUsername方法，用于加载用户数据
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
//        User user = userService.queryUserByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("用户不存在");
//        }
//
//        //用户权限列表
//        Collection<? extends GrantedAuthority> authorities = userService.queryUserAuthorities(user.getId());
//
//        return new AuthUser(
//                user.getId(),
//                user.getUsername(),
//                user.getPassword(),
//                true,
//                true,
//                true,
//                true,
//                authorities);
    }
}
