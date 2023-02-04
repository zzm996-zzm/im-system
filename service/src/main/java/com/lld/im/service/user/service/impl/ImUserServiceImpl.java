package com.lld.im.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lld.im.common.ResponseVO;
import com.lld.im.common.enums.DelFlagEnum;
import com.lld.im.service.user.dao.ImUserDataEntity;
import com.lld.im.service.user.dao.mapper.ImUserDataMapper;
import com.lld.im.service.user.model.req.DeleteUserReq;
import com.lld.im.service.user.model.req.GetUserInfoReq;
import com.lld.im.service.user.model.req.ImportUserReq;
import com.lld.im.service.user.model.req.ModifyUserInfoReq;
import com.lld.im.service.user.model.resp.GetUserInfoResp;
import com.lld.im.service.user.model.resp.ImportUserResp;
import com.lld.im.service.user.service.ImUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ImUserServiceImpl implements ImUserService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ImUserDataMapper imUserDataMapper;

    @Override
    public ResponseVO importUser(ImportUserReq req) {
        if(req.getUserData().size() > 100){
            //TODO: 返回数量过多
        }

        List<String> successId = new ArrayList<>();
        List<String>  errorId = new ArrayList<>();

        req.getUserData().forEach(e->{
             try{
                 e.setAppId(req.getAppId());
                 int insert = imUserDataMapper.insert(e);
                 if(insert == 1){
                     successId.add(e.getUserId());
                 }
             }catch(Exception ex){
                 ex.printStackTrace();
                 errorId.add(e.getUserId());
             }
        });

        ImportUserResp resp = new ImportUserResp();
        resp.setErrorId(errorId);
        resp.setSuccessId(successId);
        return ResponseVO.successResponse(resp);
    }

    @Override
    public ResponseVO<GetUserInfoResp> getUserInfo(GetUserInfoReq req) {
        QueryWrapper<ImUserDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id",req.getAppId());
        queryWrapper.in("user_id",req.getUserIds());
        queryWrapper.eq("del_flag", DelFlagEnum.DELETE);
        List<ImUserDataEntity> userDataEntities = imUserDataMapper.selectList(queryWrapper);
        HashMap<String,ImUserDataEntity> map = new HashMap<>();
        for(ImUserDataEntity data : userDataEntities){
            map.put(data.getUserId(),data);
        }
        List<String> failUser = new ArrayList<>();
        for(String uid:req.getUserIds()){
            if(!map.containsKey(uid)){
                failUser.add(uid);
            }
        }
        GetUserInfoResp resp = new GetUserInfoResp();
        resp.setUserDataItem(userDataEntities);
        resp.setFailUser(failUser);
        return ResponseVO.successResponse(resp);
    }

    @Override
    public ResponseVO<ImUserDataEntity> getSingleUserInfo(String userId, Integer appId) {
        return null;
    }

    @Override
    public ResponseVO deleteUser(DeleteUserReq req) {
        return null;
    }

    @Override
    public ResponseVO modifyUserInfo(ModifyUserInfoReq req) {
        return null;
    }
}
