package indi.huishi.shizuo.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import indi.huishi.shizuo.pojo.Statistics;
import indi.huishi.shizuo.pojo.Student;

import java.util.List;

/**
 * 学生成绩管理系统 Service层
 * @author Huishi
 */

public interface StudentService {

    /**
     * 分页查询
     * @return
     */
//    Page<Student> show(Integer pageNo, Integer pageSize);
     IPage<Student> selectPage(Page<Student> page, Integer state);

    int add(Student student);

    void update(Student student);

    int delete(Integer id);

    Student queryById(String id);

    Student queryByNo(String no);

    List<Student> queryByName(String name);

    List<Statistics> statistics();
}
