package com.qit.usercenter.service;

import com.qit.usercenter.domain.dto.*;
import com.qit.usercenter.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qit.usercenter.domain.vo.UserVo;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 永
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-11-15 11:51:03
*/
public interface UserService extends IService<User> {

    void register(UserRegisterDTO userRegisterDTO);

    UserVo login(UserLoginDTO userLoginDTO, HttpServletRequest request);

    void extracted(UserVo userVo);

    void logout(HttpServletRequest request);

    void add(UserAddDTO userAddAndUpdatedDTO);

    void update(UserUpdateDTO userDTO);

    List<UserVo> query2(UserQueryDTO userQueryDTO);
}
