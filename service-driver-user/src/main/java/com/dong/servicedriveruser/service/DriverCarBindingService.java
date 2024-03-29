package com.dong.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.constant.DriverCarConstant;
import com.dong.internalcommon.request.DriverCarBindingDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.DriverCarBindingRelationship;
import com.dong.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.xml.ws.Response;
import java.time.LocalDateTime;

@Service
public class DriverCarBindingService {

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;


    /**
     *  司机 车辆绑定
     * @param driverCarBindingDTO
     * @return
     */
    public ResponseResult driverCarBinding(DriverCarBindingDTO driverCarBindingDTO){
        // 司机_车辆_1  已绑定
        LambdaQueryWrapper<DriverCarBindingRelationship> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DriverCarBindingRelationship::getCarId,driverCarBindingDTO.getCarId());
        wrapper.eq(DriverCarBindingRelationship::getDriverId,driverCarBindingDTO.getDriverId());
        wrapper.eq(DriverCarBindingRelationship::getBindState, DriverCarConstant.BINDING);
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(wrapper);
        if(!ObjectUtils.isEmpty(driverCarBindingRelationship)){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BINGDING);
        }
        // 车辆被绑定了
        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DriverCarBindingRelationship::getCarId,driverCarBindingDTO.getCarId());
        wrapper.eq(DriverCarBindingRelationship::getBindState, DriverCarConstant.BINDING);
        driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(wrapper);
        if(!ObjectUtils.isEmpty(driverCarBindingRelationship)){
            return ResponseResult.fail(CommonStatusEnum.CAR_BINDING);
        }
        // 司机被绑定
        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DriverCarBindingRelationship::getDriverId,driverCarBindingDTO.getDriverId());
        wrapper.eq(DriverCarBindingRelationship::getBindState, DriverCarConstant.BINDING);
        driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(wrapper);
        if(!ObjectUtils.isEmpty(driverCarBindingRelationship)){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BINDING);
        }

        driverCarBindingRelationship = new DriverCarBindingRelationship();
        driverCarBindingRelationship.setDriverId(driverCarBindingDTO.getDriverId());
        driverCarBindingRelationship.setCarId(driverCarBindingDTO.getCarId());
        driverCarBindingRelationship.setBindState(DriverCarConstant.BINDING);
        driverCarBindingRelationship.setBindingTime(LocalDateTime.now());

        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);

        return ResponseResult.success();
    }


    /**
     *  司机车辆解绑
     * @param driverCarBindingDTO
     * @return
     */
    public ResponseResult driverCarUnbinding(DriverCarBindingDTO driverCarBindingDTO){
        LambdaQueryWrapper<DriverCarBindingRelationship> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DriverCarBindingRelationship::getCarId,driverCarBindingDTO.getCarId());
        wrapper.eq(DriverCarBindingRelationship::getDriverId,driverCarBindingDTO.getDriverId());
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(wrapper);
        if(ObjectUtils.isEmpty(driverCarBindingRelationship)){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS);
        }
        if(driverCarBindingRelationship.getBindState() == DriverCarConstant.NO_BINDING){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_UNBINDING);
        }

        driverCarBindingRelationship.setBindState(DriverCarConstant.NO_BINDING);
        driverCarBindingRelationship.setUnBindingTime(LocalDateTime.now());
        driverCarBindingRelationshipMapper.updateById(driverCarBindingRelationship);

        return ResponseResult.success();
    }


}
