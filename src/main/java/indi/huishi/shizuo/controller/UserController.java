package indi.huishi.shizuo.controller;


import indi.huishi.shizuo.pojo.User;
import indi.huishi.shizuo.service.UserService;
import indi.huishi.shizuo.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @Author: Huishi
 * @Date: 2021/4/20 17:16
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource(name = "userServiceRedis")
    private UserService userService;

    /**
     * 来到登录页
     * @return
     */
    @GetMapping(value = {"/","/login"})
    public String loginPage(){
        return "user/login";
    }

    /**
     * 验证登录信息
     * @param model
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/login.action")
    public String login(Model model, User user, HttpSession session) {
        //检查 用户名和密码是否存在
        User user1 = userService.login(user);
        if (user1 == null) {
            log.info("用户不存在,返回登录界面");
            model.addAttribute("msg", "用户名或密码错误");
            model.addAttribute("username", user.getUsername());//这里是输入的user,因为user1==null
            // 把错误信息 和回显表单信息 保存
            //返回登录界面
            return "user/login";
        } else {
            log.info("跳转到显示页面");
            // 保存用户成功登陆的信息在session域 因为后面都会用到
            session.setAttribute("user", user1);
            log.info("登录成功");
//            ModelAndView view = new ModelAndView("redirect:/student/show.action?pageNo=1");
            return "redirect:/student/show.action?pageNo=1";
        }
    }

    /**
     * 注册
     */
    @PostMapping("/register.action")
    public String register(Model model, HttpServletRequest req, HttpSession session) throws Exception {
        // 验证码校驗
        boolean checkVerifyCode = CodeUtil.checkVerifyCode(req);

        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");
        log.info("注册信息{}...", username+password+email+code);
        //判断验证码 忽略大小写
        if (checkVerifyCode) {
            //如果验证码正确 检查用户名是否可用
            if (userService.existsUsername(username)) {
                //如果用户名已经存在，返回注册页面
                // 回显
                model.addAttribute("msgs", "用户名已经存在");
                model.addAttribute("username", username);
                model.addAttribute("email", email);
                return "user/regist";
            } else {
                // 用户名不存在，将用户名 密码 邮箱 保存到数据库
                User user = new User(null, username, password, email);
                userService.registerUser(user);
                // 保存用户成功登陆的信息在session域 因为后面都会用到
                session.setAttribute("user", user);
                //跳转到 注册成功界面
                //跳转到显示页面
                // 保存用户成功登陆的信息在session域 因为后面都会用到
                log.info("用户{}注册成功", user);
                return "redirect:/student/show.action?pageNo=1";
            }
        }else {
            //验证码错误
            log.info("验证码错误:{}", code);
            // 把回显信息保存到request域 回显用户名邮箱
            model.addAttribute("msgs","验证码错误");
            model.addAttribute("username",username);
            model.addAttribute("email",email);
            return "user/regist";
        }
    }

    /**
     * 注销
     * @param session
     */
    @RequestMapping("/logout.action")
    protected String logout(HttpSession session){
        //1.销毁Session中用户登录的信息
        session.invalidate();
        //2.重定向到首页
        return "redirect:/user/login";//全路径
    }

    /**
     * 跳转到指定页面
     * @param model
     * @param sourceName
     * @return
     */
    @GetMapping("{sourceName}.html")
    public String jumpTo(Model model, @PathVariable("sourceName") String sourceName){
        String string = new StringBuffer().append("user/").append(sourceName).append(".html").toString();
        return string;
    }
}
