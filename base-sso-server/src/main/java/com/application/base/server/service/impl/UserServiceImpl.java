package com.application.base.server.service.impl;

import com.application.base.core.entity.ResultDataVO;
import com.application.base.server.core.model.UserInfo;
import com.application.base.server.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static List<UserInfo> mockUserList = new ArrayList<>();
    static {
        for (int i = 0; i <5; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(1000+i);
            userInfo.setUserName("user" + (i>0?String.valueOf(i):""));
            userInfo.setPassWord("123456");
            mockUserList.add(userInfo);
        }
    }

    @Override
    public ResultDataVO<UserInfo> findUser(String username, String password) {

        if (username==null || username.trim().length()==0) {
            return new ResultDataVO<UserInfo>("500", "Please input username.");
        }
        if (password==null || password.trim().length()==0) {
            return new ResultDataVO<UserInfo>("500", "Please input password.");
        }

        // mock user
        for (UserInfo mockUser: mockUserList) {
            if (mockUser.getUserName().equals(username) && mockUser.getPassWord().equals(password)) {
                return new ResultDataVO<UserInfo>(mockUser);
            }
        }
        return new ResultDataVO<UserInfo>("500", "username or password is invalid.");
    }

}
