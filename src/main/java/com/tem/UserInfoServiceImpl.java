//package com.tem;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sohu.auto.content.common.bean.PassportResult;
//import com.sohu.auto.content.common.constant.PassportConstant;
//import com.sohu.auto.content.common.utils.HttpUtil;
//import com.sohu.auto.content.common.utils.PassportUtils;
//import com.sohu.auto.content.common.utils.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Author:zouyu
// * @Date:2019/3/4 15:17
// */
//@Service
//public class UserInfoServiceImpl implements UserInfoService {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Value("${pecker.passport.syskey}")
//    private String sysKey;
//
//    @Value("${pecker.passport.sysid}")
//    private String sysId;
//
//    @Value("${pecker.passport.url.userinfo}")
//    private String userUrl;
//
//    @Resource(name = "str2BeanRedisTemplate")
//    RedisTemplate redisTemplate;
//
//    @Override
//    public String getCurrentUserInfo(String passport) {
//        if(StringUtils.isEmpty(passport)){
//            logger.error("param passport is null");
//            return null;
//        }
//        try {
//            ValueOperations valueOperations = redisTemplate.opsForValue();
//            String redisKey = String.format(PassportConstant.USER_INFO_REDIS_KEY, passport);
//            Object result = valueOperations.get(redisKey);
//            if (result != null) {
//                return (String) result;
//            }
//
//            String userInfo = getUserInfoByApi(passport);
//            if(StringUtils.isNotEmpty(userInfo)) {
//                //缓存1小时
//                valueOperations.set(redisKey, userInfo, 1, TimeUnit.HOURS);
//            }
//            return userInfo;
//        }catch (Exception e){
//            logger.error("get passport userinfo error with passport:"+passport,e);
//            return null;
//        }
//    }
//
//    private String getUserInfoByApi(String passport){
//        String url = String.format(userUrl);
//        Map<String,String> data = new HashMap<>();
//        data.put("passport",passport);
//        String finalUrl = PassportUtils.getPassportUrl(url, data, sysId, sysKey);
//        String re = HttpUtil.doPost(finalUrl, new HashMap<>());
////        String re = HttpUtils.httpPost(finalUrl, null, "UTF-8");
//        PassportResult passportResult = JSONObject.parseObject(re, PassportResult.class);
//        if(passportResult.getStatus() == PassportConstant.OK){
//            return passportResult.getBody();
//        }else{
//            logger.error(passportResult.getMessage());
//        }
//        return null;
//    }
//}
//
