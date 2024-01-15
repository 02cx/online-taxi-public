package com.dong.servicedriveruser.remote;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("service-map")
public class ServiceMapClient {
}
