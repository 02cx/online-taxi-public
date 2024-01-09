package com.dong.servicepassengeruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.servicepassengeruser.domain.PassengerUser;
import org.springframework.stereotype.Repository;

/**
* @author 20117
* @description 针对表【passenger_user】的数据库操作Mapper
* @createDate 2024-01-10 00:03:54
* @Entity com.dong.servicepassengeruser.domain.PassengerUser
*/
@Repository
public interface PassengerUserMapper extends BaseMapper<PassengerUser> {

}
