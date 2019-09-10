package com.zr.controller;

import com.zr.domain.Role;
import com.zr.domain.UserInfo;
import com.zr.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 查询用户信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userList = userService.findAll();
        mv.addObject("userList",userList);
        mv.setViewName("user-list");
        return mv;
    }

    /**
     * 添加用户
     * @param userInfo
     */
    @RequestMapping("/save.do")
    public String save(UserInfo userInfo) throws Exception {
        userService.save(userInfo);
        return "redirect:findAll.do";
    }

    /**
     * 根据id查询角色详情
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user",userInfo);
        mv.setViewName("user-show");
        return mv;
    }

    /**
     * 查询用户还可以添加哪些角色，然后跳转到用户角色添加页面
     * @param userId
     * @return
     */
    @RequestMapping("findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(
            @RequestParam(name = "id" ,required = true)String userId) throws Exception {
        ModelAndView mv = new ModelAndView();
        //1、根据用户id查询用户
        UserInfo userInfo = userService.findById(userId);
        //2、根据用户id查询用户可以添加的角色
        List<Role> otherRoles = userService.findOtherRoles(userId);
        mv.addObject("user",userInfo);//返回用户
        mv.addObject("roleList",otherRoles);//返回用户还可以添加的角色
        mv.setViewName("user-role-add");
        return mv;
    }

    /**
     * 给用户添加角色
     */
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(
            @RequestParam(name = "userId",required = true)String userId,
            @RequestParam(name = "ids",required = true)String[] roleId) throws Exception {
        userService.addRoleToUser(userId,roleId);
        return "redirect:findAll.do";//添加完毕返回用户查询页面
    }

}
