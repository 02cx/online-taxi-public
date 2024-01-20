package com.dong.serviceorder.remote;

import com.dong.internalcommon.response.PriceRuleResponse;
import com.dong.internalcommon.result.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-price")
public interface ServicePriceClient {

    /**
     * 判断计价规则是否最新
     * @param fareType
     * @param fareVersion
     * @return
     */
    @GetMapping("/price-rule/is-new")
    public ResponseResult isNew(@RequestParam String fareType, @RequestParam Integer fareVersion);
    /**
     *  获取最新的计价规则
     * @param fareType
     * @return
     */
    @GetMapping("/price-rule/latest")
    public ResponseResult<PriceRuleResponse> latest(@RequestParam String fareType);

}
