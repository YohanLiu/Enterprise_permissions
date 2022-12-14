package ssm.controller;


import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ssm.domain.Role;
import ssm.domain.UserInfo;
import ssm.service.IUserService;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/findByInformation.do")
    public ModelAndView findByInformation(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size, @RequestParam(name = "information", required = true, defaultValue = "") String information) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userList = userService.findByInformation(page,size,information);
        PageInfo pageInfo = new PageInfo(userList);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("userSize",size);
        mv.setViewName("user-page-list");
        return mv;
    }


    // 给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId",required = true) Integer userId, @RequestParam(name = "ids",required = true) Integer[] roleIds) {
        userService.addRoleToUser(userId,roleIds);
        return "redirect:findAll.do";
    }


    // 查询用户以及用户可以添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id",required = true) Integer userId) throws Exception {
        ModelAndView mv = new ModelAndView();
        // 1.根据用户id查询用户
        UserInfo userInfo = userService.findById(userId);
        // 2.根据用户id查询可以添加的角色
        List<Role> otherRoles = userService.findOtherRoles(userId);
        mv.addObject("user",userInfo);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }



    // 查询指定id的用户
    @RequestMapping("/findById.do")
    public ModelAndView findById(Integer id) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user",userInfo);
        mv.setViewName("user-show");
        return mv;
    }

    @RequestMapping("/save.do")
    @PreAuthorize("authentication.principal.username == 'tom'")
    public String save(UserInfo userInfo) throws Exception {
        userService.save(userInfo);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page, @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userList = userService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(userList);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("userSize",size);
        mv.setViewName("user-page-list");
        return mv;
    }

    @RequestMapping("/deleteUserById.do")
    public String deleteUserById(Integer id) {
        userService.deleteUsers_roleById(id);
        userService.deleteUserById(id);
        return "redirect:findAll.do";
    }
}
