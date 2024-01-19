package com.dong.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.internalcommon.request.PriceRuleDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.serviceprice.domain.PriceRule;
import com.dong.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceRuleService {

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult add(PriceRuleDTO priceRuleDTO){
        PriceRule priceRule = new PriceRule();
        BeanUtils.copyProperties(priceRuleDTO,priceRule);
        // 拼接计价类型
        String cityCode = priceRuleDTO.getCityCode();
        String vehicleType = priceRuleDTO.getVehicleType();
        String fareType = cityCode + vehicleType;
        priceRule.setFareType(fareType);
        // 计价版本
        Integer fareVersion = 0;
        LambdaQueryWrapper<PriceRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceRule::getCityCode,priceRule.getCityCode());
        wrapper.eq(PriceRule::getVehicleType,priceRule.getVehicleType());
        // 因为有了计价版本，cityCode  + vehicleType不能唯一确定一条数据，所以每次使用version最大的一条数据
        wrapper.orderByDesc(PriceRule::getFareVersion);
        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        if(priceRules.size() > 0){
            fareVersion = priceRules.get(0).getFareVersion();
        }
        priceRule.setFareVersion(++fareVersion);
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();

    }
}
