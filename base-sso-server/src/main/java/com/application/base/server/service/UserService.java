package com.application.base.server.service;

import com.application.base.core.entity.ResultDataVO;
import com.application.base.server.core.model.UserInfo;

public interface UserService {
    
    /**
     * 查找结果。
     * @param userName
     * @param passWord
     * @return
     */
    public ResultDataVO<UserInfo> findUser(String userName, String passWord);

}
