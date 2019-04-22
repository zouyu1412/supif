//package com.tem;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sohu.auto.content.common.constant.PassportConstant;
//import com.sohu.auto.content.common.pojo.ResultVO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.SetOperations;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
///**
// * @Author:zouyu
// * @Date:2019/3/7 19:24
// */
//@Service
//public class PooledService implements InitializingBean {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    @Qualifier("str2BeanRedisTemplate")
//    private RedisTemplate<String,Integer> redisTemplate;
//
//    /** 定时器 */
//    private Timer timer;
//
//    /** 随机数生成器 */
//    private static final Random RANDOM_NUMBER_GENERATOR = new Random();
//
//    private SetOperations<String,Integer> setOperations;
//    private ValueOperations<String,Integer> valueOperations;
//
//    @Autowired
//    private CommentService commentService;
//
//    /**
//     * 随机产生 [from ,to] 范围内随机数
//     * @param from
//     * @param to
//     * @return
//     */
//    private static int getRandomInt(int from, int to) {
//        return from + RANDOM_NUMBER_GENERATOR.nextInt(to - from + 1);
//    }
//
//    public void addnewsId(int id){
//        logger.info("add newsId[{}] in cache list",id);
//        setOperations.add(PassportConstant.REFRESHED_NEWSID_SET_REDIS_KEY,id);
//    }
//
//    public void refreshRedisKey() {
//
//        Set<Integer> refreshIdList = setOperations.members(PassportConstant.REFRESHED_NEWSID_SET_REDIS_KEY);
//        if(!refreshIdList.isEmpty()) {
//            StringBuilder para = new StringBuilder();
//            for(Iterator<Integer> it = refreshIdList.iterator();it.hasNext();){
//                Integer next = it.next();
//                para.append(next+",");
//            }
//            ResultVO resultVO = commentService.getLikeStatisticFromApi(para.substring(0, para.length() - 1));
//            if(resultVO.getCode() == PassportConstant.OK){
//                Map<String, JSONObject> map = (Map<String, JSONObject>)resultVO.getData();
//                for(Map.Entry<String, JSONObject> entry:map.entrySet()){
//                    String key = entry.getKey();
//                    JSONObject value = entry.getValue();
//                    if(value != null && value.getString("likeCount") != null){
//                        valueOperations.set(String.format(PassportConstant.NEWSID_COMMENT_NUM_REDIS_KEY,key),value.getInteger("commentCount"));
//                    }
//                }
//                redisTemplate.delete(PassportConstant.REFRESHED_NEWSID_SET_REDIS_KEY);
//            }else{
//                logger.error("call api to get comment num error",resultVO.getMsg());
//            }
//        }
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        setOperations = redisTemplate.opsForSet();
//        valueOperations = redisTemplate.opsForValue();
//        timer = new Timer();
//        try {
//            MyTimeTask task = new MyTimeTask();
//            //5个实例 每隔20分钟调用一次
//            timer.schedule(task, getRandomInt(0, 300000), 300000);
////            timer.schedule(task, getRandomInt(0, 1200000), 1200000);
//        }catch (Exception e){
//            logger.error("schedule run error",e);
//        }
//    }
//
//    /**
//     * 自定义定时器
//     */
//    public class MyTimeTask extends TimerTask{
//        @Override
//        public void run() {
//            logger.info("start refresh comment-related redis key");
//            try {
//                refreshRedisKey();
//            } catch (Exception e) {
//                logger.error("redis key refresh error",e);
//            }
//            logger.info("stop refresh");
//        }
//    }
//}
