package com.qit.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qit.usercenter.domain.dto.*;
import com.qit.usercenter.domain.entity.User;
import com.qit.usercenter.domain.vo.UserVo;
import com.qit.usercenter.mapper.UserMapper;
import com.qit.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 永
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-11-15 11:51:03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "qit";

    @Resource
    private UserMapper userMapper;

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        //校验账号密码是否符合规定
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new RuntimeException("参数错误");
        }
        String validate = "^[A-Za-z0-9_-]+$";
        if (!username.matches(validate) || !password.matches(validate)) {
            throw new RuntimeException("账号或密码含有非法字符");
        }
        if (username.length() < 6 || username.length() > 11 || password.length() < 6 || password.length() > 15) {
            throw new RuntimeException("账号或密码长度不符合规定");
        }
        //校验两次密码是否一致
        if (!password.equals(checkPassword)) {
            throw new RuntimeException("两次密码不一致");
        }
        //判断数据库是否已存在该账号
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser!=null) {
            throw new RuntimeException("账号已存在");
        }
        //对密码进行加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT+password).getBytes());
        //构造实体，插入数据库
        User user = new User();
        user.setUsername(username);
        user.setPassword(newPassword);
        this.save(user);
    }

    @Override
    public UserVo login(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        //校验账号密码是否符合规定
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new RuntimeException("参数错误");
        }
        String validate = "^[A-Za-z0-9_-]+$";
        if (!username.matches(validate) || !password.matches(validate)) {
            throw new RuntimeException("账号或密码含有非法字符");
        }
        if (username.length() < 6 || username.length() > 11 || password.length() < 6 || password.length() > 15) {
            throw new RuntimeException("账号或密码长度不符合规定");
        }
        //对输入密码进行加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT+password).getBytes());
        //查询数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", newPassword);
        User dbUser = userMapper.selectOne(queryWrapper);
        if (dbUser==null){
            throw new RuntimeException("账号不存在或密码错误");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(dbUser,userVo);
        userVo.setPassword(userLoginDTO.getPassword());
        //脱敏
        extracted(userVo);
        //记录用户登录态
        request.getSession().setAttribute("user",userVo);//键 值
        return userVo;
    }

    /**
     * 脱敏
     * ctrl+alt+m提取方法
     */
    @Override
    public void extracted(UserVo userVo) {
        int length = userVo.getPassword().length();
        int numAsterisks = length - 4; // 保留前两位和后两位，中间部分用 * 替换
        StringBuilder maskedPassword = new StringBuilder(); //创建一个 StringBuilder 对象 maskedPassword，用于构建最终的格式化密码
        maskedPassword.append(userVo.getPassword(), 0, 2);//添加前两位
        for (int i = 0; i < numAsterisks; i++) {//循环替换为 *
            maskedPassword.append('*');
        }
        maskedPassword.append(userVo.getPassword().substring(length - 2));//添加后两位
        userVo.setPassword(maskedPassword.toString());
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }

    @Override
    public void add(UserAddDTO userAddAndUpdatedDTO) {
        //TODO 添加检验逻辑
        //如果密码为空，默认为123456
        if (StringUtils.isBlank(userAddAndUpdatedDTO.getPassword())){
            String newPassword = DigestUtils.md5DigestAsHex((SALT+userAddAndUpdatedDTO.getPassword()).getBytes());
            userAddAndUpdatedDTO.setPassword(newPassword);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddAndUpdatedDTO,user);
        userMapper.insert(user);
    }

    @Override
    public void update(UserUpdateDTO userDTO) {
        //TODO 添加检验逻辑
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        userMapper.updateById(user);
    }

    @Override
    public List<UserVo> query2(UserQueryDTO userQueryDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userQueryDTO.getUsername()),"username",userQueryDTO.getUsername());
        queryWrapper.like(StringUtils.isNotBlank(userQueryDTO.getNickname()),"nickname",userQueryDTO.getNickname());
        queryWrapper.like(StringUtils.isNotBlank(userQueryDTO.getPhone()),"phone",userQueryDTO.getPhone());
        queryWrapper.like(StringUtils.isNotBlank(userQueryDTO.getEmail()),"email",userQueryDTO.getEmail());
        queryWrapper.eq(userQueryDTO.getRole()!=null,"role",userQueryDTO.getRole());
        queryWrapper.eq(userQueryDTO.getStatus()!=null,"status",userQueryDTO.getStatus());
        queryWrapper.eq(userQueryDTO.getGender()!=null,"gender",userQueryDTO.getGender());
        queryWrapper.eq(userQueryDTO.getId()!=null,"id",userQueryDTO.getId());
        if (userQueryDTO.getCreatedTime() != null && userQueryDTO.getUpdatedTime() != null) {
            queryWrapper.between("created_time", userQueryDTO.getCreatedTime(), userQueryDTO.getUpdatedTime());
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        return userList.stream().map(user -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            extracted(userVo);
            return userVo;
        }).collect(Collectors.toList());
    }

}




