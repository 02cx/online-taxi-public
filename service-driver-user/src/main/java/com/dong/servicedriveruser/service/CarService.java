package com.dong.servicedriveruser.service;

import com.dong.internalcommon.request.CarDTO;
import com.dong.internalcommon.request.TerminalDTO;
import com.dong.internalcommon.request.TrackDTO;
import com.dong.internalcommon.response.TerminalResponse;
import com.dong.internalcommon.response.TrackResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicedriveruser.domain.Car;
import com.dong.servicedriveruser.mapper.CarMapper;
import com.dong.servicedriveruser.remote.ServiceMapClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult insertCar(CarDTO carDTO){
        Car car = new Car();
        BeanUtils.copyProperties(carDTO,car);

        // 获取终端id
        TerminalResponse terminalResponse = serviceMapClient.addTerminal(new TerminalDTO(carDTO.getVehicleNo())).getData();
        car.setTid(terminalResponse.getTid());

        // 获取终端对应的轨迹id
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setTid(terminalResponse.getTid());
        TrackResponse trackResponse = serviceMapClient.addTrack(trackDTO).getData();
        car.setTrid(trackResponse.getTrid());

        carMapper.insert(car);

        return ResponseResult.success();
    }

    public ResponseResult<CarDTO> getCarById(Long carId){
        Car car = carMapper.selectById(carId);
        return ResponseResult.success(car);
    }
}
