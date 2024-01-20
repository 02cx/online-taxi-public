package com.dong.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.request.PriceRuleDTO;
import com.dong.internalcommon.response.PriceRuleResponse;
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

    /**
     *  添加计价规则
     * @param priceRuleDTO
     * @return
     */
    public ResponseResult add(PriceRuleDTO priceRuleDTO){
        PriceRule priceRule = new PriceRule();
        BeanUtils.copyProperties(priceRuleDTO,priceRule);
        // 拼接计价类型
        String cityCode = priceRuleDTO.getCityCode();
        String vehicleType = priceRuleDTO.getVehicleType();
        String fareType = cityCode + "$" + vehicleType;
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
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EXISTS);
        }
        priceRule.setFareVersion(++fareVersion);
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }

    /**
     * 编辑计价规则
     * @param priceRuleDTO
     * @return
     */
    public ResponseResult edit(PriceRuleDTO priceRuleDTO){
        PriceRule priceRule = new PriceRule();
        BeanUtils.copyProperties(priceRuleDTO,priceRule);

        LambdaQueryWrapper<PriceRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceRule::getCityCode,priceRule.getCityCode());
        wrapper.eq(PriceRule::getVehicleType,priceRule.getVehicleType());
        // 因为有了计价版本，cityCode  + vehicleType不能唯一确定一条数据，所以每次使用version最大的一条数据
        wrapper.orderByDesc(PriceRule::getFareVersion);

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        PriceRule latestPriceRule = null;
        if(priceRules.size() > 0){
             latestPriceRule = priceRules.get(0);
            String latestRule = "" +  latestPriceRule.getStartMile() + latestPriceRule.getStartFare() +
                    latestPriceRule.getUnitPricePerMinute() + latestPriceRule.getUnitPricePerMile();
            String newRule = "" + priceRuleDTO.getStartMile() + priceRuleDTO.getStartFare() +
                    priceRuleDTO.getUnitPricePerMinute() + priceRuleDTO.getUnitPricePerMile();
            if(latestRule.equals(newRule)){
                return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_CHANGE);
            }
        }

        priceRule.setFareVersion(latestPriceRule.getFareVersion() + 1);
        priceRule.setFareType(latestPriceRule.getFareType());
        priceRuleMapper.insert(priceRule);
        return ResponseResult.success();
    }


    /**
     *  根据fareType和fareVersion判断计价规则是否最新
     * @param fareType
     * @param fareVersion
     * @return
     */
    public ResponseResult isNew(String fareType, Integer fareVersion){
        LambdaQueryWrapper<PriceRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceRule::getFareType,fareType);
        wrapper.orderByDesc(PriceRule::getFareVersion);
        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        if(priceRules.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS,false);
        }
        if(priceRules.get(0).getFareVersion() != fareVersion){
            return ResponseResult.fail(CommonStatusEnum.FARE_VERSION_NOT_NEW,false);
        }

        return ResponseResult.success(true);
    }

    /**
     * 查询最新的计价规则
     * @param fareType
     * @return
     */
    public ResponseResult<PriceRuleResponse> latest(String fareType){
        LambdaQueryWrapper<PriceRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceRule::getFareType,fareType);
        wrapper.orderByDesc(PriceRule::getFareVersion);
        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        if(priceRules.size() == 0){
            return ResponseResult.success(null);
        }else{
            PriceRuleResponse priceRuleResponse = new PriceRuleResponse();
            PriceRule priceRule = priceRules.get(0);
            BeanUtils.copyProperties(priceRule,priceRuleResponse);
            return ResponseResult.success(priceRuleResponse);
        }
    }

    /**
     * 城市编码和车辆类型的计价规则是否存在
     * @param priceRuleDTO
     * @return
     */
    public ResponseResult<Boolean> ifExists(PriceRuleDTO priceRuleDTO){
        LambdaQueryWrapper<PriceRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PriceRule::getCityCode,priceRuleDTO.getCityCode());
        wrapper.eq(PriceRule::getVehicleType,priceRuleDTO.getVehicleType());
        wrapper.orderByDesc(PriceRule::getFareVersion);
        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        if(priceRules.size() > 0){
            return ResponseResult.success(true);
        }else{
            return ResponseResult.success(false);
        }
    }
}
