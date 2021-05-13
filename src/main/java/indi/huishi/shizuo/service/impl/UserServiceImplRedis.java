package indi.huishi.shizuo.service.impl;

import indi.huishi.shizuo.dao.UserDao;
import indi.huishi.shizuo.pojo.User;
import indi.huishi.shizuo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

/**
 * @Author: Huishi
 * @Date: 2021/5/13 22:06
 */
@Service("userServiceRedis")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImplRedis implements UserService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UserDao userDao;
    /**
     * 登录
     * @param user
     * @throws Exception
     */
    @Override
    public User login(User user) {
        String username = user.getUsername();
        // 判断jedis中键值对中保存的密码，是否等于输入的密码
        ValueOperations<String, String> redisUtil = redisTemplate.opsForValue();
        // 输入用户根本不存在
        return Objects.equals(redisUtil.get(username), user.getPassword()) ? user: null;
    }

    /**
     * 注册
     * @param user
     */
    @Override
    public void registerUser(User user) {
        // 保存到数据库一份完整的用户注册信息，包括邮箱
        userDao.saveUser(user);
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
        // 保存到redis用户名密码键值对
        try {
            opsForValue.set(user.getUsername(), user.getPassword());
        }catch (Exception e){
            throw new RuntimeException("redis发生错误");
        }
        // 保存到redis用户名到 set 防止重复
        opsForSet.add("uname", user.getUsername());
    }

    /**
     * Redis判断 用户名不能重复
     * @param username
     * @return
     */
    @Override
    public boolean existsUsername(String username) {
        SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
        return opsForSet.isMember("uname", username);
    }
}
