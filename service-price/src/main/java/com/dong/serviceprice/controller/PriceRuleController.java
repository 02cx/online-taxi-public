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

    @PostMapping("/add")
    public ResponseResult add(@RequestBody PriceRuleDTO priceRuleDTO){
        return priceRuleService.add(priceRuleDTO);
    }

}
