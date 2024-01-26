package com.dong.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.constant.DriverCarConstant;
import com.dong.internalcommon.request.DriverUserDTO;
import com.dong.internalcommon.response.OrderDriverResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.Car;
import com.dong.servicedriveruser.domain.DriverCarBindingRelationship;
import com.dong.servicedriveruser.domain.DriverUser;
import com.dong.servicedriveruser.domain.DriverUserWorkStatus;
import com.dong.servicedriveruser.mapper.CarMapper;
import com.dong.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.dong.servicedriveruser.mapper.DriverUserMapper;
import com.dong.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    /**
     * 新增 司机用户
     *
     * @return
     */
    public ResponseResult addDriverUser(DriverUserDTO driverUserDTO) {
        DriverUser driverUser = new DriverUser();
        BeanUtils.copyProperties(driverUserDTO, driverUser);
        driverUser.setState(DriverCarConstant.DRIVER_STATE_VALID);
        driverUserMapper.insert(driverUser);
        // 初始化 司机工作状态表
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstant.WORK_STATUS_STOP);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success();
    }

    /**
     * 根据司机用户id修改信息
     *
     * @param driverUserDTO
     * @return
     */
    public ResponseResult updateDriverUser(DriverUserDTO driverUserDTO) {
        DriverUser driverUser = new DriverUser();
        BeanUtils.copyProperties(driverUserDTO, driverUser);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success();
    }

    /**
     * 根据司机手机号查询司机信息是否可用
     *
     * @param driverPhone
     * @return
     */
    public ResponseResult checkDriver(String driverPhone) {
        LambdaQueryWrapper<DriverUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DriverUser::getDriverPhone, driverPhone);
        wrapper.eq(DriverUser::getState, DriverCarConstant.DRIVER_STATE_VALID);
        DriverUser driverUser = driverUserMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(driverUser)) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_USER_NOT_EXISTS);
        }

        return ResponseResult.success(driverUser);
    }

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    @Autowired
    private CarMapper carMapper;

    /**
     * 根据carId查询创建订单所需要的司机信息-----司机ID，司机Phone
     *
     * @param carId
     * @return
     */
    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId) {
        // 查询车辆绑定关系
        LambdaQueryWrapper<DriverCarBindingRelationship> driverCarBindingRelationshipLambdaQueryWrapper = new LambdaQueryWrapper<>();
        driverCarBindingRelationshipLambdaQueryWrapper.eq(DriverCarBindingRelationship::getCarId, carId);
        driverCarBindingRelationshipLambdaQueryWrapper.eq(DriverCarBindingRelationship::getBindState, DriverCarConstant.BINDING);
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(driverCarBindingRelationshipLambdaQueryWrapper);

        Long driverId = driverCarBindingRelationship.getDriverId();
        // 查询司机的工作状态
        LambdaQueryWrapper<DriverUserWorkStatus> driverUserWorkStatusLambdaQueryWrapper = new LambdaQueryWrapper<>();
        driverUserWorkStatusLambdaQueryWrapper.eq(DriverUserWorkStatus::getDriverId, driverId);
        driverUserWorkStatusLambdaQueryWrapper.eq(DriverUserWorkStatus::getWorkStatus, DriverCarConstant.WORK_STATUS_START);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(driverUserWorkStatusLambdaQueryWrapper);

        if(null == driverUserWorkStatus){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_AVAILABLE);
        }else{
            // 查询司机是否有效
            LambdaQueryWrapper<DriverUser> driverUserWrapper = new LambdaQueryWrapper<>();
            driverUserWrapper.eq(DriverUser::getId,driverId);
            driverUserWrapper.eq(DriverUser::getState,DriverCarConstant.DRIVER_STATE_VALID);
            DriverUser driverUser = driverUserMapper.selectOne(driverUserWrapper);

            // 查询车辆信息
            Car car = carMapper.selectById(carId);

            OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
            orderDriverResponse.setCarId(carId);
            orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());
            orderDriverResponse.setDriverId(driverId);
            orderDriverResponse.setVehicleNo(car.getVehicleNo());
            orderDriverResponse.setVehicleType(car.getVehicleType());
            orderDriverResponse.setLicenseId(driverUser.getLicenseId());
            return ResponseResult.success(orderDriverResponse);
        }

    }

}
