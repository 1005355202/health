package com.itheima.security;

import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description:
 * @Author: yp
 */
public class SpringSecurityService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    //根据用户名加载  参数username: 就是表单里面提交过来的用户名
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.调用Dao 根据username查询出当前的用户【模拟】
        User user =  findByUsername(username);
        if(user == null){
            return null;
        }
        //2. 进行授权(先写死, 后面会从数据库里面动态获得)
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  //赋予ROLE_ADMIN角色
        grantedAuthorityList.add(new SimpleGrantedAuthority("add"));  //赋予add权限
        grantedAuthorityList.add(new SimpleGrantedAuthority("delete"));  //赋予delete权限

        //3.创建UserDetails返回(自己进行密码比对的)
        // String username 用户名, String password 密码【数据库里面的查询出来的密码 {noop} 不使用加密进行比对】, Collection<? extends GrantedAuthority> authorities 权限集合
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,user.getPassword(),grantedAuthorityList);
        return userDetails;
    }

    //模拟根据username查询数据库
    private User findByUsername(String username) {
        if("admin".equals(username)){
            User user = new User();
            user.setUsername("admin");
            user.setPassword(encoder.encode("123456"));  //就是数据库保存后的密码
            user.setGender("男");
            return user;
        }
        return null;
    }
}
