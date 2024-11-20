package com.qit.usercenter.controller;

import com.qit.usercenter.common.BaseResponse;
import com.qit.usercenter.domain.dto.*;
import com.qit.usercenter.domain.vo.UserVo;
import com.qit.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 永
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody UserRegisterDTO userRegisterDTO){
        userService.register(userRegisterDTO);
        log.info("注册成功");
        return new BaseResponse<>(200,"注册成功");
    }

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<UserVo> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request){
        UserVo userVo = userService.login(userLoginDTO,request);
        log.info("登录成功");
        return new BaseResponse<>(200,"登录成功",userVo);
    }

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<UserVo> getCurrentUser(HttpServletRequest request){
        Object user = request.getSession().getAttribute("user");
        UserVo currentUser = (UserVo) user;
        if (currentUser == null){
            return new BaseResponse<>(401,"未登录");
        }
        log.info("获取用户信息成功");
        return new BaseResponse<>(200,"获取用户信息成功",currentUser);
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request){
        userService.logout(request);
        log.info("退出登录成功");
        return new BaseResponse<>(200,"退出登录成功");
    }

    /**
     * 查询所有用户
     * @param request
     * @return
     */
    @GetMapping("list")
    public BaseResponse<List<UserVo>> list(HttpServletRequest request, UserQueryDTO userQueryDTO){
        //判断当前用户权限
        UserVo currentUser = (UserVo) request.getSession().getAttribute("user");
        if (currentUser.getRole()!=0){
            return new BaseResponse<>(401,"权限不足");
        }
        ArrayList<UserVo> resultList = new ArrayList<>();

        List<UserVo> list = userService.query2(userQueryDTO);
        resultList.addAll(list);


        log.info("查询所有用户成功");
        return new BaseResponse<>(200,"查询所有用户成功",resultList);
    }

    /**
     * 添加用户
     * @param userAddAndUpdatedDTO
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> add(@RequestBody UserAddDTO userAddAndUpdatedDTO){
        userService.add(userAddAndUpdatedDTO);
        log.info("添加用户成功");
        return new BaseResponse<>(200,"添加用户成功");
    }

    /**
     * 修改用户
     * @param userDTO
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody UserUpdateDTO userDTO){
        userService.update(userDTO);
        log.info("修改用户成功");
        return new BaseResponse<>(200,"修改用户成功");
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> delete(@PathVariable("id") String id){
        userService.removeById(id);
        log.info("删除用户成功");
        return new BaseResponse<>(200,"删除用户成功");
    }



}
