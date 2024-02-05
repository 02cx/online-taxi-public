package com.dong.servicealipay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "alipay")
@Data
public class MyAlipayConfig {

    private String appId;
    private String appPrivateKey;
    private String publicKey;
    private String notifyUrl;


/*    @PostConstruct
    public void init(){
        Config config = new Config();
        // 基本配置
        config.protocol = "https";
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com/gateway.do";
        config.signType = "RSA2";
        // 业务配置
        config.appId = appId;
        config.alipayPublicKey = publicKey;
        config.notifyUrl = notifyUrl;
        config.merchantPrivateKey = appPrivateKey;

        Factory.setOptions(config);
        System.out.println("支付宝初始化配置完成");
    }*/

}
