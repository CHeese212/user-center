package com.zjh.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjh.usercenter.common.BaseResponse;
import com.zjh.usercenter.common.ErrorCode;
import com.zjh.usercenter.common.exception.BusinessException;
import com.zjh.usercenter.constant.UserConstant;
import com.zjh.usercenter.pojo.User;
import com.zjh.usercenter.pojo.request.UserLoginRequest;
import com.zjh.usercenter.pojo.request.UserRegisterRequest;
import com.zjh.usercenter.service.UserService;
import com.zjh.usercenter.utils.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户接口
 *
 * @author cheese
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册信息待完善");
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResponseUtil.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        //Controller层对传进来的数进行检验，Service层检测是为了在其他Service类调用该方法时也能进行检验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户或密码未输入");
        }
        User safetyUser = userService.doLogin(userAccount, userPassword, request);
        if (safetyUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "账户或密码错误");
        } else {
            return ResponseUtil.success(safetyUser);
        }
    }

    @PostMapping("/logout")
    public void userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "request对象为空");
        }
        userService.doLogout(request);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object objUser = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User curUser = (User) objUser;
        if (curUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
        //重新从数据库中查询一次，防止用户信息被修改了却察觉不到
        long userId = curUser.getId();
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        request.setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);
        return ResponseUtil.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无权查找该数据");
        }
        //3.查询数据库，模糊查询
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (username != null) {
            userQueryWrapper.like("username", username);
        }
        List<User> userList = userService.list(userQueryWrapper).stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResponseUtil.success(userList);
    }

    private boolean isAdmin(HttpServletRequest request) {
        //1.获取当前会话的用户
        User curUser = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        //2.鉴权，检查是否为管理员,判断是否为空
        if (curUser == null || !curUser.getUserRole().equals(UserConstant.ADMIN_ROLE)) {
            return false;
        }
        return true;
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUserById(Integer id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无权删除该数据");
        }
        //3.执行删除数据库
        boolean result = userService.removeById(id);
        return ResponseUtil.success(result);
    }
}

