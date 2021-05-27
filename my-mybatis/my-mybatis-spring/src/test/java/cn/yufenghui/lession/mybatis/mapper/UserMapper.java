package cn.yufenghui.lession.mybatis.mapper;

import cn.yufenghui.lession.mybatis.entity.UserEntity;

import java.util.List;

/**
 * @author Yu Fenghui
 * @date 2021/5/11 15:52
 * @since
 */
public interface UserMapper {

    List<UserEntity> list();

}
