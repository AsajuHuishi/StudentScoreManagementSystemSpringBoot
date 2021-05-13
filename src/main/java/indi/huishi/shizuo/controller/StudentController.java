package indi.huishi.shizuo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.huishi.shizuo.pojo.Statistics;
import indi.huishi.shizuo.pojo.Student;
import indi.huishi.shizuo.service.StudentService;
import indi.huishi.shizuo.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 * @Author: Huishi
 * @Date: 2021/4/17 23:29
 */
@Controller
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    private final String REDIRECT_PATH = "redirect:/student/show.action?pageNo=";
    private Integer pageSize = 8;

    /**
     * 跳转到指定页面显示
     * @param pageNo
     * @return
     */
    @RequestMapping("/show.action")
    public String showAll (@RequestParam("pageNo") Integer pageNo, Model model, HttpSession session, HttpServletRequest request){
        Page<Student> page = new Page<>(pageNo, pageSize);
        log.info("主页..查找第{}页",pageNo);
        IPage<Student> studentPage = studentService.selectPage(page, null);
        // 保存当前url
        model.addAttribute("page", studentPage);
        String url = request.getRequestURL().toString();
        model.addAttribute("curr_url", url);
        log.info("url:{}",url);
        session.setAttribute("curr_url", url);
        // 将page保存在Session里面
        session.setAttribute("studentPage",studentPage);
        return "menu/show";
    }

    /**
     * 根据要修改的id查询该学生信息
     * @param stuId
     * @return
     */
    @RequestMapping("/queryById.action")
    public String queryById(Model model, String stuId){
        Student student = studentService.queryById(stuId);
        log.info("查询学生{}",student);
        model.addAttribute("student",student);
        return "menu/update";
    }

    /**
     * 根据修改之后的学生信息更新数据库，重定向回主页
     * @param student 来自接收的表单信息
     * @return
     */
    @RequestMapping("/update.action")
    public String updateById (Student student, HttpSession session){
//        System.out.println("updateById"+student);
        studentService.update(student);
        // 获取当前页
        Page<Student> studentPage = (Page)session.getAttribute("studentPage");
        Integer pageNo = (int)studentPage.getCurrent();
        // 重定向 显示全部
        System.out.println(REDIRECT_PATH + pageNo);
        return REDIRECT_PATH + pageNo;
    }

    /**
     * 删除该学生信息，重定向回主页
     * @param stuId
     * @return
     */
    @RequestMapping("/delete.action")
    public String deleteById(String stuId, HttpSession session){
        Integer id = Integer.parseInt(stuId);
        int delete = studentService.delete(id);
        // 获取当前页
        Page<Student> studentPage = (Page)session.getAttribute("studentPage");
        Integer pageNo = (int)studentPage.getCurrent();
        // 重定向 显示全部
        return REDIRECT_PATH + pageNo;
    }

    /**
     * 接收按学号查询的 学号，返回查找结果
     * @param
     * @return
     */
    @RequestMapping("/queryByNo.action")
    public String queryByNo(@RequestParam("queryNo") String no, Model model){
        System.out.println("输入学号"+no);
        Student student = studentService.queryByNo(no);
        if (student==null){
            log.info("查找的学号{}不存在",no);
            model.addAttribute("status","404");//学号不存在状态码
            model.addAttribute("previous",no);//学号不存在状态码
            return "menu/queryByNo";
        }else {
            log.info("查找的学号{}存在",no);
            List<Student> studentList = new ArrayList<>();
            studentList.add(student);
            model.addAttribute("stuList", studentList);
            model.addAttribute("status","200");//学号不存在状态码
            return "menu/queryResult";
        }
    }

    /**
     * 按姓名查询，返回查找结果
     * @param
     * @return
     */
    @RequestMapping("/queryByName.action")
    public String queryByName(@RequestParam("queryName") String name, Model model){
        System.out.println("输入姓名"+name);
        List<Student> studentList = studentService.queryByName(name);
        if (studentList.size()==0){
            log.info("查找的学号{}不存在", name);
            model.addAttribute("status","404");//学号不存在状态码
            model.addAttribute("previous",name);//学号不存在状态码
            return "menu/queryByName";
        }else {
            model.addAttribute("stuList",studentList);
            model.addAttribute("status","200");//学号不存在状态码
            return "menu/queryResult";
        }
    }

    /**
     * 添加 学生信息 返回到主页
     */
    @RequestMapping("/add.action")
    public String add(Student student, HttpSession session, Model model) throws RuntimeException{
        // 首先查询学号是否已被使用（学号是unique）
        Student query = studentService.queryByNo(student.getNo());
        System.out.println("查询"+query);
        // 获取当前页
        Page<Student> studentPage = (Page) session.getAttribute("studentPage");
        Integer pageNo = (int)studentPage.getCurrent();

        if(query == null) {// 学号未被使用
            int add = studentService.add(student);
            if (add == 0) {
                System.out.println("添加失败");
                model.addAttribute("status", "400");
                return "menu/add";
            }else{
                System.out.println("添加成功");
                return REDIRECT_PATH + pageNo;
            }
        }else{
            System.out.println("该学号已被使用");
            model.addAttribute("status", "404");
            return "menu/add";
        }
    }

    /**
     * 统计学生信息
     */
    @RequestMapping("/stat.action")
    public String stat(Model model) throws Exception {
        List<Statistics> statistics = studentService.statistics();
        // 用于柱状图
        Map<String, List<Object>> resultMap = BeanUtil.objectList2MapList(statistics, new String[]{"minScore", "maxScore", "avgScore", "countStudent", "className"});
        // 用于扇形图
        List<Map<String, Object>> resultList = BeanUtil.objectList2ListMap(statistics);

        model.addAttribute("stat", statistics);
        model.addAttribute("statMap", resultMap);
        model.addAttribute("statList", resultList);
        return "menu/stat";
    }

    /**
     * 跳转到指定页面
     * @param model
     * @param sourceName
     * @return
     */
    @GetMapping("{sourceName}.html")
    public String jumpTo(Model model, @PathVariable("sourceName") String sourceName){
        String string = new StringBuffer().append("menu/").append(sourceName).append(".html").toString();
        return string;
    }

}
