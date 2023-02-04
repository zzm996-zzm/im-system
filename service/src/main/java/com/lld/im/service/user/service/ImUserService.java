package com.lld.im.service.user.service;

import com.lld.im.common.ResponseVO;
import com.lld.im.service.user.dao.ImUserDataEntity;
import com.lld.im.service.user.model.req.DeleteUserReq;
import com.lld.im.service.user.model.req.GetUserInfoReq;
import com.lld.im.service.user.model.req.ImportUserReq;
import com.lld.im.service.user.model.req.ModifyUserInfoReq;
import com.lld.im.service.user.model.resp.GetUserInfoResp;

public interface ImUserService {
    //导入用户
    public ResponseVO importUser(ImportUserReq req);
    //获取用户信息
     public ResponseVO<GetUserInfoResp> getUserInfo(GetUserInfoReq req);
    //获取单独用户信息
     public ResponseVO<ImUserDataEntity> getSingleUserInfo(String userId,Integer appId);
     //删除用户
     public ResponseVO deleteUser(DeleteUserReq req);
     //修改用户
     public ResponseVO modifyUserInfo(ModifyUserInfoReq req);


}
