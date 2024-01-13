package com.dong.servicemap.service;

import com.dong.internalcommon.constant.AmapConfigConstant;
import com.dong.internalcommon.constant.CommonStatusEnum;
import com.dong.internalcommon.result.ResponseResult;
import com.dong.servicemap.domain.DicDistrict;
import com.dong.servicemap.mapper.DicDistrictMapper;
import com.dong.servicemap.remote.MapDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {

    @Autowired
    private MapDistrictClient mapDistrictClient;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    public ResponseResult initDicDistrict(String keywords){
        String districtStr = mapDistrictClient.district(keywords);
        System.out.println(districtStr);
        JSONObject jsonObjectAll = JSONObject.fromObject(districtStr);
        int status = jsonObjectAll.getInt(AmapConfigConstant.STATUS);
        if(status != 1){
            return ResponseResult.fail(CommonStatusEnum.QUERY_DISTRICT_ERROR);
        }

        // country数组
        JSONArray jsonArrayCountry = jsonObjectAll.getJSONArray(AmapConfigConstant.DISTRICTS);
        JSONObject jsonObjectCountry = jsonArrayCountry.getJSONObject(0);
        String addressCodeCountry = jsonObjectCountry.getString(AmapConfigConstant.ADCODE);
        String addressNameCountry = jsonObjectCountry.getString(AmapConfigConstant.NAME);
        String parentAddressCodeCountry = "0";
        Integer levelCountry = generatorLevel(jsonObjectCountry.getString(AmapConfigConstant.LEVEL));
        insertDicDistrict(addressCodeCountry,addressNameCountry,parentAddressCodeCountry,levelCountry);

        // province数组
        JSONArray jsonArrayProvince = jsonObjectCountry.getJSONArray(AmapConfigConstant.DISTRICTS);
        for (int i = 0; i < jsonArrayProvince.size(); i++) {
            // 获取province对象
            JSONObject jsonObjectProvince = jsonArrayProvince.getJSONObject(i);
            String addressCodeProvince = jsonObjectProvince.getString(AmapConfigConstant.ADCODE);
            String addressNameProvince = jsonObjectProvince.getString(AmapConfigConstant.NAME);
            String parentAddressCodeProvince = addressCodeCountry;
            Integer levelProvince = generatorLevel(jsonObjectProvince.getString(AmapConfigConstant.LEVEL));
            insertDicDistrict(addressCodeProvince,addressNameProvince,parentAddressCodeProvince,levelProvince);
            // city数组
            JSONArray jsonArrayCity = jsonObjectProvince.getJSONArray(AmapConfigConstant.DISTRICTS);
            for (int j = 0; j < jsonArrayCity.size(); j++) {
                // city对象
                JSONObject jsonObjectCity = jsonArrayCity.getJSONObject(j);
                String addressCodeCity = jsonObjectCity.getString(AmapConfigConstant.ADCODE);
                String addressNameCity = jsonObjectCity.getString(AmapConfigConstant.NAME);
                String parentAddressCodeCity = addressCodeProvince;
                Integer levelCity = generatorLevel(jsonObjectCity.getString(AmapConfigConstant.LEVEL));
                insertDicDistrict(addressCodeCity,addressNameCity,parentAddressCodeCity,levelCity);
                // district数组
                JSONArray jsonArrayDistrict = jsonObjectCity.getJSONArray(AmapConfigConstant.DISTRICTS);
                for (int k = 0; k < jsonArrayDistrict.size(); k++) {
                    // district对象
                    JSONObject jsonObjectDistrict = jsonArrayDistrict.getJSONObject(k);
                    String addressCodeDistrict = jsonObjectDistrict.getString(AmapConfigConstant.ADCODE);
                    String addressNameDistrict = jsonObjectDistrict.getString(AmapConfigConstant.NAME);
                    String parentAddressCodeDistrict = addressCodeCity;
                    Integer levelDistrict = generatorLevel(jsonObjectDistrict.getString(AmapConfigConstant.LEVEL));
                    insertDicDistrict(addressCodeDistrict,addressNameDistrict,parentAddressCodeDistrict,levelDistrict);

                    // street 数组
                    JSONArray jsonArrayStreet = jsonObjectDistrict.getJSONArray(AmapConfigConstant.DISTRICTS);
                    if(jsonArrayStreet.size() != 0){
                        for (int m = 0; m < jsonArrayStreet.size(); m++) {
                            // street对象
                            JSONObject jsonObjectStreet = jsonArrayStreet.getJSONObject(m);
                            String addressCodeStreet = jsonObjectStreet.getString(AmapConfigConstant.ADCODE);
                            String addressNameStreet = jsonObjectStreet.getString(AmapConfigConstant.NAME);
                            String parentAddressCodeStreet = addressCodeDistrict;
                            Integer levelStreet = generatorLevel(jsonObjectStreet.getString(AmapConfigConstant.LEVEL));
                            insertDicDistrict(addressCodeStreet,addressNameStreet,parentAddressCodeStreet,levelStreet);
                        }
                    }
                }

            }
        }

        return ResponseResult.success();
    }

    public void insertDicDistrict(String addressCode,String addressName,String parentAddressCode,Integer level){
        DicDistrict dicDistrict = DicDistrict.builder()
                .addressCode(addressCode)
                .addressName(addressName)
                .parentAddressCode(parentAddressCode)
                .level(level).build();

        dicDistrictMapper.insert(dicDistrict);
    }


    public Integer generatorLevel(String level){
        Integer newLevel = 0;
        if("country".equals(level)){
            newLevel = 0;
        }else if("province".equals(level)){
            newLevel = 1;
        }else if("city".equals(level)){
            newLevel = 2;
        }else if("district".equals(level)){
            newLevel = 3;
        }else if("street".equals(level)){
            newLevel = 4;
        }
        return newLevel;
    }
}
