package com.papers.controller;

import com.papers.domain.User;
import com.papers.domain.response.responseData;
import com.papers.service.paperUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouyong
 * @date 2021/2/21 23:57
 */
@RestController
@RequestMapping("/user")
public class UserLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private paperUserService userService;

    /**
     * 登录
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/login")
    public responseData userLogin(@RequestBody User user, HttpServletRequest request){
        try {
            User user1 = userService.login(user);
            if( null != user1 ){
                request.getSession().setAttribute("login-status", user1.getUsername());
                // 设置session存活时间为3600s
                request.getSession().setMaxInactiveInterval(3600);
                return new responseData(200,"success", user1);
            }
            return new responseData(500, "fail");
        }catch (Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public responseData Userregister(@RequestBody User user) {
        try {
            Integer register = userService.register(user);
            if( 1 == register ) {
                return new responseData(200, "success", user);
            }
            return new responseData(500, "fail");
        }catch (Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 登出操作
     * @param request
     * @return
     */
    @GetMapping("/loginOut")
    public responseData loginOut(HttpServletRequest request){
        if( null != request.getSession() && null != request.getSession().getAttribute("login-status") ){
            request.getSession().removeAttribute("login-status");
            return new responseData(200,"success");
        }
        return new responseData(500,"fail");
    }


    /**
     * 更新账户信息
     * @param user
     * @return
     */
    @PostMapping("/updateUser")
    public responseData updateUser(@RequestBody User user){
        try {
            Integer integer = userService.updateUser(user);
            if(  1 == integer ){
                return new responseData(200,"success");
            }
            return new responseData(500,"fail");
        }catch(Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }

    /**
     * 通过userid查询对应的用户
     * @param id
     * @return
     */
    @GetMapping("/findUserById")
    public responseData findUserById(@RequestParam Integer id){
        try {
            User userById = userService.findUserById(id);
            if(  null != userById) {
                return new responseData(200, "查询成功", userById);
            }
            return new responseData(500,"fail");
        }catch (Exception e){
            return new responseData(500,"服务异常", "【" + e.getMessage() + "】");
        }
    }


}
