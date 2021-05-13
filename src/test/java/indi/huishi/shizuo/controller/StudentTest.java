package indi.huishi.shizuo.controller;

import indi.huishi.shizuo.ShizuoApplication;
import indi.huishi.shizuo.pojo.Statistics;
import indi.huishi.shizuo.service.StudentService;
import indi.huishi.shizuo.util.BeanUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @Author: Huishi
 * @Date: 2021/5/13 0:34
 */
@SpringBootTest(classes = ShizuoApplication.class)
@RunWith(SpringRunner.class)
public class StudentTest {

    @Autowired
    StudentService studentService;
    @Test
    public void test() throws Exception {
        System.out.println(studentService);
        List<Statistics> statistics = studentService.statistics();
        List<Map<String, Object>> maps = BeanUtil.objectList2ListMap(statistics);
        System.out.println(maps);

        Map<String, List<Object>> map = BeanUtil.objectList2MapList(statistics, new String[]{"minScore","maxScore","avgScore","countStudent","className"});
        System.out.println(map);
    }
}
