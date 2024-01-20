package com.dong.serviceprice.controller;

import com.dong.internalcommon.request.PriceRuleDTO;
import com.dong.internalcommon.response.PriceRuleResponse;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.serviceprice.domain.PriceRule;
import com.dong.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {

    @Autowired
    private PriceRuleService priceRuleService;

    /**
     * 新增计价规则
     * @param priceRuleDTO
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRuleDTO priceRuleDTO){
        return priceRuleService.add(priceRuleDTO);
    }

    /**
     * 编辑计价规则
     * @param priceRuleDTO
     * @return
     */
    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody PriceRuleDTO priceRuleDTO){
        return priceRuleService.edit(priceRuleDTO);
    }

    /**
     * 判断计价规则是否最新
     * @param fareType
     * @param fareVersion
     * @return
     */
    @GetMapping("/is-new")
    public ResponseResult isNew(@RequestParam String fareType,@RequestParam Integer fareVersion){
        return priceRuleService.isNew(fareType,fareVersion);
    }

    /**
     *  获取最新的计价规则
     * @param fareType
     * @return
     */
    @GetMapping("/latest")
    public ResponseResult<PriceRuleResponse> latest(@RequestParam String fareType){
        return priceRuleService.latest(fareType);
    }

    /**
     *  城市编码和车辆类型的计价规则是否存在
     * @param priceRuleDTO
     * @return
     */
    @PostMapping("/if-exists")
    public ResponseResult<Boolean> ifExists(@RequestBody PriceRuleDTO priceRuleDTO){
        return priceRuleService.ifExists(priceRuleDTO);
    }

}
