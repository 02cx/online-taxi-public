package com.dong.servicepassengeruser.service;

import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.vo.UserInfoVO;
import com.dong.servicepassengeruser.domain.PassengerUser;
import com.dong.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone){
        // 根据乘客手机号查询用户信息
        HashMap<String,Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUserList = passengerUserMapper.selectByMap(map);
        // 判断用户信息是否存在

        // 用户信息不存在，则注册，否则不处理
        if(passengerUserList.size() == 0){
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerName("张三");
            passengerUser.setPassengerGender((byte)0);
            passengerUser.setState((byte) 0);

            passengerUser.setGmtCreate(LocalDateTime.now());
            passengerUser.setGmtModified(LocalDateTime.now());
            passengerUserMapper.insert(passengerUser);
        }

        // 响应
        return ResponseResult.success("");
    }


    /**
     * 根据手机号查询用户信息
     */
    public ResponseResult getUserByPhone(String passengerPhone){
        // 根据乘客手机号查询用户信息
        HashMap<String,Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUserList = passengerUserMapper.selectByMap(map);
        if(passengerUserList.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXISTS);
        }
        PassengerUser passengerUser = passengerUserList.get(0);

        UserInfoVO userInfoVO = UserInfoVO.builder()
                .passengerName(passengerUser.getPassengerName())
                .profilePhoto(passengerUser.getProfilePhoto())
                .build();

        return ResponseResult.success(userInfoVO);
    }
}
