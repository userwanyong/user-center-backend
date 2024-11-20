package com.qit.usercenter.mapper;

import com.qit.usercenter.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 永
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-11-15 11:51:03
* @Entity com.qit.usercenter.domain.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




