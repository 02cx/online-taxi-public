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
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     *  新增车辆
     * @param carDTO
     * @return
     */
    @Transactional
    public ResponseResult insertCar(CarDTO carDTO){
        Car car = new Car();
        BeanUtils.copyProperties(carDTO,car);
        carMapper.insert(car);

        // 获取终端id
        TerminalDTO terminalDTO = new TerminalDTO();
        terminalDTO.setName(carDTO.getVehicleNo());
        terminalDTO.setDesc(car.getId() + "");
        TerminalResponse terminalResponse = serviceMapClient.addTerminal(terminalDTO).getData();
        car.setTid(terminalResponse.getTid());

        // 获取终端对应的轨迹id
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setTid(terminalResponse.getTid());
        TrackResponse trackResponse = serviceMapClient.addTrack(trackDTO).getData();
        car.setTrid(trackResponse.getTrid());

        carMapper.updateById(car);


        return ResponseResult.success();
    }

    public ResponseResult<CarDTO> getCarById(Long carId){
        Car car = carMapper.selectById(carId);
        return ResponseResult.success(car);
    }
}
