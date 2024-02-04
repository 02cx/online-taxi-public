package com.dong.apidriver.service;

import com.dong.apidriver.remote.ServiceSsePushClient;
import com.dong.internalcommon.constant.IdentityConstant;
import com.dong.internalcommon.request.PushPayPriceDTO;
import com.dong.internalcommon.result.ResponseResult;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PayService {

    @Autowired
    private ServiceSsePushClient serviceSsePushClient;

    /**
     * 司机向乘客推送支付价格
     * @param pushPayPriceDTO
     * @return
     */
    public ResponseResult pushPayPrice(PushPayPriceDTO pushPayPriceDTO){
        //封装消息
        JSONObject data = new JSONObject();
        data.put("price",pushPayPriceDTO.getPrice());
        data.put("orderId",pushPayPriceDTO.getOrderId());

        // 发送消息
        serviceSsePushClient.push(pushPayPriceDTO.getPassengerId(), IdentityConstant.PASSENGER_IDENTITY,data.toString());
        return ResponseResult.success();
    }

}
