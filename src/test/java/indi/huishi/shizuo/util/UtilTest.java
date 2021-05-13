package indi.huishi.shizuo.util;

import indi.huishi.shizuo.pojo.Statistics;
import indi.huishi.shizuo.pojo.User;
import indi.huishi.shizuo.service.StudentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

/**
 * @Author: Huishi
 * @Date: 2021/5/13 0:09
 */
@SpringBootTest
public class UtilTest {

    @Test
    public void test() throws Exception {
        User user = new User(21,"Lin Vien ty","123456","asas");
        Map<String, Object> stringObjectMap = BeanUtil.object2Map(user);
        System.out.println(stringObjectMap);

        System.out.println(stringObjectMap.get("asas"));//null
    }
}
