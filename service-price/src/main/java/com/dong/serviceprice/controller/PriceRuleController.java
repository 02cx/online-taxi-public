package com.dong.serviceprice.controller;

import com.dong.internalcommon.request.PriceRuleDTO;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
