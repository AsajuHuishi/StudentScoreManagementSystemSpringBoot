package indi.huishi.shizuo.interceptor;

/**
 * @Author: Huishi
 * @Date: 2021/4/28 0:17
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录检查
 * 1.配置拦截器
 * 2.把配置放在容器中
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 方法执行前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        log.info("1preHandle: {}", request.getRequestURI());
        if (session.getAttribute("user") == null){
            log.info("未登录");
            request.setAttribute("msg","请先登录！");
            request.getRequestDispatcher("/user/login").forward(request, response);
            return false;
        }else{
            return true;//放行
        }
    }
    /**
     * 方法执行后
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("1postHandle: {}", request.getRequestURI());
    }
    /**
     * 页面渲染后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("1afterCompletion: {}", request.getRequestURI());
    }
}
