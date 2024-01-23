package com.dong.servicedriveruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dong.servicedriveruser.domain.DriverUser;
import org.springframework.stereotype.Repository;

/**
* @author 20117
* @description 针对表【driver_user】的数据库操作Mapper
* @createDate 2024-01-13 12:40:13
* @Entity com.dong.servicedriveruser.domain.DriverUser
*/
@Repository
public interface DriverUserMapper extends BaseMapper<DriverUser> {

    // 测试
    Integer selectCountTest();

    // 根据城市编码查询城市是否有可用司机
    Integer isAvailableDriver(String cityCode);
}




