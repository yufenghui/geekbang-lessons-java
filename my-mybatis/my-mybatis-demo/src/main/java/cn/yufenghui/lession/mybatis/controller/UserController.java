package cn.yufenghui.lession.mybatis.controller;

import cn.yufenghui.lession.mybatis.entity.UserEntity;
import cn.yufenghui.lession.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yu Fenghui
 * @date 2021/5/27 10:39
 * @since
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;


    @GetMapping("list")
    public List<UserEntity> list() {

        List<UserEntity> list = userMapper.list();

        return list;
    }

}
