package com.sky.mapper;

import com.sky.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {
    /**
     * 根据OPENID查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid=#{openid}")
    User getByOpenid(String openid);

    void insert(User user);

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    User getById(Long id);

    /**
     * 统计规定时间内用户
     * @return
     */
    Integer countByMap(Map map);
}
