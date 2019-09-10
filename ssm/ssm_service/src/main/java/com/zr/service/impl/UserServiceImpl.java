package com.zr.service.impl;

import com.zr.dao.IUserDao;
import com.zr.domain.Role;
import com.zr.domain.UserInfo;
import com.zr.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;//密码加密工具类

    /**
     * spring-security读取路径
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserInfo userInfo = null;
        try {
            userInfo = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //对象转换：把dao中传回的userInfo转换封装为user对象交给框架
        User user = new User(
                userInfo.getUsername(),//用户名
                userInfo.getPassword(),//用户密码
                userInfo.getStatus() == 0 ? false : true,   //账户是否可用
                true,       //帐户未过期
                true,   //凭据未过期
                true,       //账户未锁
                getAuthority(userInfo.getRoles())   //给用户设置权限（权限不止一个，所以为一个数组）
        );
        return user;//给框架返回一个user对象
    }
    //作用就是返回一个List集合，集合中装入的是角色描述，返回用户权限
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));//两种角色ROLE_USER和ROLE_ADMIN
        }
        return list;
    }

    /**
     * 查询所有用户信息
     * @return
     * @throws Exception
     */
    public List<UserInfo> findAll() throws Exception {
        return userDao.findAll();
    }

    /**
     * 保存用户信息
     * @param userInfo
     * @throws Exception
     */
    @Override
    public void save(UserInfo userInfo) throws Exception {
        //对密码进行加密：bCryptPasswordEncoder.encode(原密码)
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public UserInfo findById(String id) throws Exception {
        return userDao.findById(id);
    }

    /**
     * 根据用户id查询用户还未添加的角色
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<Role> findOtherRoles(String userId) throws Exception {
        return userDao.findOtherRoles(userId);
    }

    /**
     * 给用户添加角色
     * @param userId
     * @param roleId
     * @throws Exception
     */
    @Override
    public void addRoleToUser(String userId, String[] roleId) throws Exception {
        userDao.addRoleToUser(userId,roleId);
    }

}
