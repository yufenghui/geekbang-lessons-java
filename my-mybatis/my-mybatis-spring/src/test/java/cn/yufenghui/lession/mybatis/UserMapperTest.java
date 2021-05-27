package cn.yufenghui.lession.mybatis;

import cn.yufenghui.lession.mybatis.entity.UserEntity;
import cn.yufenghui.lession.mybatis.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Yu Fenghui
 * @date 2021/5/11 15:28
 * @since
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootstrapApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testUserList() {

        List<UserEntity> list = userMapper.list();

        System.out.println(list);
    }

}
