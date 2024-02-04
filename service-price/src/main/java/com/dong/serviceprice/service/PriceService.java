package com.dong.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.request.CalculatePriceDTO;
import com.dong.internalcommon.request.ForecastPriceDTO;
import com.dong.internalcommon.response.DirectionResponse;
import com.dong.internalcommon.response.PriceResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.internalcommon.util.DecimalUtils;
import com.dong.serviceprice.domain.PriceRule;
import com.dong.serviceprice.mapper.PriceRuleMapper;
import com.dong.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class PriceService {

    @Autowired
   private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    /**
     * 估算价格
     * @param forecastPriceDTO
     * @return
     */
    public ResponseResult forecasePrice(ForecastPriceDTO forecastPriceDTO){
        // 调用地图服务，获取距离和时间
        ResponseResult<DirectionResponse> responseResult = serviceMapClient.driving(forecastPriceDTO);
        Integer distance = responseResult.getData().getDistance();
        Integer duration = responseResult.getData().getDuration();

        String cityCode = forecastPriceDTO.getCityCode();
        String vehicleType = forecastPriceDTO.getVehicleType();

        // 读取计价规则
       /* HashMap<String, Object> map = new HashMap<>();
        map.put("city_code","110000");
        map.put("vehicle_type","1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(map);*/
        LambdaQueryWrapper<PriceRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceRule::getCityCode,cityCode);
        wrapper.eq(PriceRule::getVehicleType,vehicleType);
        wrapper.orderByDesc(PriceRule::getFareVersion);

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);

        if(priceRules.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS);
        }
        PriceRule priceRule = priceRules.get(0);
        // 根据 里程、时长、计价规则计算价格
        double forecasePrice = getPrice(distance, duration, priceRule);

        PriceResponse forecasePriceResponse = new PriceResponse();
        forecasePriceResponse.setForecasePrice(forecasePrice);
        forecasePriceResponse.setCityCode(cityCode);
        forecasePriceResponse.setVehicleType(vehicleType);
        return ResponseResult.success(forecasePriceResponse);
    }

    /**
     *  计算实际价格
     * @param calculatePriceDTO
     * @return
     */
    public ResponseResult calculatePrice(CalculatePriceDTO calculatePriceDTO){
        LambdaQueryWrapper<PriceRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceRule::getCityCode,calculatePriceDTO.getCityCode());
        wrapper.eq(PriceRule::getVehicleType,calculatePriceDTO.getVehicleType());
        wrapper.orderByDesc(PriceRule::getFareVersion);

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);

        if(priceRules.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS);
        }
        PriceRule priceRule = priceRules.get(0);
        // 根据 里程、时长、计价规则计算价格
        double actualPrice = getPrice(calculatePriceDTO.getDistance(), calculatePriceDTO.getDuration(), priceRule);

        PriceResponse actualPriceResponse = new PriceResponse();
        actualPriceResponse.setActualPrice(actualPrice);
        actualPriceResponse.setCityCode(calculatePriceDTO.getCityCode());
        actualPriceResponse.setVehicleType(calculatePriceDTO.getVehicleType());
        return ResponseResult.success(actualPriceResponse);
    }

    public  double getPrice(Integer distance, Integer duration, PriceRule priceRule){
        // 初始价格
        double price = 0.0;
        // 起始价格
        Double startFare = priceRule.getStartFare();
        price = DecimalUtils.add(price,startFare);
        // 起步里程
        Integer startMile = priceRule.getStartMile();
        // 里程价格 元/km
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        // 里程差值  m
        // 里程差值 km
        double distanceKM = DecimalUtils.divide(distance,1000);
        double mileDiff = DecimalUtils.subtract(distanceKM, startMile);
        mileDiff = mileDiff < 0 ? 0 : mileDiff;

        price = price + DecimalUtils.multiply(unitPricePerMile,mileDiff);

        // 历程价格 元/分钟
        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        // duration  秒 -> 分钟
        double durationMinute = DecimalUtils.divide(duration, 60.0);
        double minutePrice = DecimalUtils.multiply(unitPricePerMinute, durationMinute);
        price = minutePrice + price;

        return price;
    }




//    public static void main(String[] args) {
//        PriceRule priceRule = new PriceRule();
//        priceRule.setStartFare(10.0);
//        priceRule.setStartMile(3);
//        priceRule.setUnitPricePerMile(1.8);
//        priceRule.setUnitPricePerMinute(0.5);
//
//        // 10 + (6.5 - 3) * 1.8 + 20 * 0.5
//        // 10 + 3.5 * 1.8 + 10 = 26.3
//        double price = calculatePrice(6500, 1200, priceRule);
//        System.out.println(price);
//    }


}
