package indi.huishi.shizuo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import indi.huishi.shizuo.dao.StudentDao;
import indi.huishi.shizuo.pojo.Statistics;
import indi.huishi.shizuo.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: Huishi
 * @Date: 2021/5/10 23:50
 */
@SpringBootTest
public class ServiceTest {

    @Autowired
    StudentDao studentDao;
    @Test
    public void test1(){
//        System.out.println(studentDao);
        List<Statistics> list = studentDao.getStatistics();
        System.out.println(list);

        Page<Student> studentPage = new Page<>(2, 8);
        IPage<Student> page = studentDao.selectPageVo(studentPage, null);

        List<Student> records = page.getRecords();
        System.out.println(records.size());
    }

    @Autowired
    StudentService studentService;
    @Test
    public void test2(){
        System.out.println(studentService);
    }


    @Autowired
    private DefaultKaptcha kaptcha;

    @Test
    public void test(){
        String text = kaptcha.createText();
        System.out.println(text);
    }
}
