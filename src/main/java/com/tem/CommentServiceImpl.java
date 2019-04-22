//package com.tem;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sohu.auto.content.common.bean.Comment;
//import com.sohu.auto.content.common.bean.CommentResult;
//import com.sohu.auto.content.common.bean.CommentUser;
//import com.sohu.auto.content.common.bean.HomePageData;
//import com.sohu.auto.content.common.constant.PassportConstant;
//import com.sohu.auto.content.common.enums.CommentBusinessEnum;
//import com.sohu.auto.content.common.pojo.ResultVO;
//import com.sohu.auto.content.common.utils.HttpUtils;
//import com.sohu.auto.content.common.utils.PassportUtils;
//import com.sohu.auto.content.common.utils.StringUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.math.NumberUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author:zouyu
// * @Date:2019/3/6 14:30
// */
//@Service
//public class CommentServiceImpl implements CommentService {
//
//    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
//
//    /**
//     * 评论页最大size
//     */
//    private static int pageMaxSize = 30;
//    private static String sep = "@---@";
//
//    @Value("${pecker.comment.channel_id}")
//    private String channelId;
//
//    @Value("${pecker.comment.app_key}")
//    private String appKey;
//
//    @Value("${pecker.comment.secret}")
//    private String secret;
//
//    @Value("${pecker.comment.url.deliver}")
//    private String deliverUrl;
//    @Value("${pecker.comment.url.pages}")
//    private String pagesUrl;
//    @Value("${pecker.comment.url.delete}")
//    private String deleteUrl;
//    @Value("${pecker.comment.url.like}")
//    private String likeUrl;
//    @Value("${pecker.comment.url.count}")
//    private String countUrl;
//    @Value("${pecker.comment.url.bump}")
//    private String bumpUrl;
//    @Value("${pecker.comment.url.waterdel}")
//    private String waterdelUrl;
//
//    @Autowired
//    @Qualifier("str2BeanRedisTemplate")
//    private RedisTemplate redisTemplate;
//
//    @Autowired
//    PooledService pooledService;
//
//    @Override
//    public ResultVO deliverComment(String topicTitle, String newsId, String topicUrl, String replyId, String content, String ip, String passport, String mediaId, String attachmentInfo, String from) {
//
//        String sourceId = getSourceId(newsId);
//
//        Map<String,String> params = new HashMap<>();
//        params.put("topic_title",topicTitle);
//        params.put("source_id",sourceId);
//        params.put("topic_url",topicUrl);
//        if(StringUtils.isNotEmpty(replyId)) {
//            params.put("reply_id", replyId);
//        }
//        if(StringUtils.isNotEmpty(mediaId)) {
//            params.put("media_id", mediaId);
//        }
//        if(StringUtils.isNotEmpty(attachmentInfo)) {
//            params.put("attachment_info", attachmentInfo);
//        }
//        if(StringUtils.isNotEmpty(from)) {
//            params.put("from", from);
//        }
//        params.put("content",content);
//        params.put("channel_id",channelId);
//        params.put("ip",ip);
//        params.put("passport",passport);
//
//        String re = doPost(deliverUrl, params, new HashMap<>());
//        CommentResult commentResult = JSONObject.parseObject(re, CommentResult.class);
//
//        if(commentResult != null) {
//            if (isSuccess(commentResult.getCode())) {
//                String data = commentResult.getData();
//                if (StringUtils.isNotEmpty(data)) {
//                    logger.info("user[{}] deliver comment[{}] success",passport,content);
//                    Comment comment = JSONObject.parseObject(data, Comment.class);
//                    handleComment(comment);
//                    return new ResultVO(comment);
//                }
//            } else {
//                return new ResultVO(-1, commentResult.getMsg(), commentResult.getData());
//            }
//        }else {
//            logger.error("commentResult is null");
//            return ResultVO.ERROR;
//        }
//        return null;
//    }
//
//    @Override
//    public ResultVO listComment(String newsId, Integer pageNo, Integer pageSize) {
//
//        String sourceId = getSourceId(newsId);
//
//        Map<String,String> map =new HashMap<>();
//        HomePageData commentPage;
//
//        if(pageNo == null || pageSize == null){
//            //不分页 返回所以评论列表
//            commentPage = getCommentPage(sourceId, 1, pageMaxSize);
//            if(commentPage == null || 0 == commentPage.getTotalPageNo()){
//                logger.info("sourceId["+sourceId+"]comment list is null");
//                return new ResultVO(-1,"sourceId["+sourceId+"]comment list is null",null);
//            }
//            int totalPageNo = commentPage.getTotalPageNo();
//            List<Comment> realComments = commentPage.getComments();
//
//            if(totalPageNo >1){
//                for(int i=2;i<=totalPageNo;i++){
//                    HomePageData page = getCommentPage(sourceId, 1, pageMaxSize);
//                    if(page != null){
//                        List<Comment> comments = page.getComments();
//                        if(CollectionUtils.isNotEmpty(comments)){
//                            realComments.addAll(comments);
//                        }
//                    }
//                }
//            }
//        }else{
//            //需要分页
//            pageNo = (pageNo == null || pageNo == 0)? 1 :pageNo;
//            pageSize = (pageSize == null || pageSize == 0)? pageMaxSize :pageSize;
//            commentPage = getCommentPage(sourceId, pageNo, pageSize);
//        }
////        List<Comment> comments = commentPage.getComments();
////        for(Comment comment:comments){
////            handleComment(comment);
////        }
//        return new ResultVO(commentPage);
//    }
//
//    /**
//     * 分页获取评论
//     * @param sourceId
//     * @param pageNo
//     * @param pageSize
//     * @return
//     */
//    private HomePageData getCommentPage(String sourceId,Integer pageNo,Integer pageSize){
//        try {
//            Map<String, String> map = new HashMap<>();
//            map.put("source_id", sourceId);
//            map.put("page_size", String.valueOf(pageSize));
//            map.put("page_no", String.valueOf(pageNo));
//            String re = doGet(pagesUrl, map);
//            CommentResult commentResult = JSONObject.parseObject(re, CommentResult.class);
//            if(commentResult != null) {
//                if (isSuccess(commentResult.getCode())) {
//                    String data = commentResult.getData();
//                    if (StringUtils.isNotEmpty(data)) {
//                        HomePageData homePageData = JSONObject.parseObject(data, HomePageData.class);
//                        Map<String, CommentUser> users = homePageData.getUsers();
//                        addUserAvatar(users);
//                        homePageData.handleComment();
//                        return homePageData;
//                    }
//                } else {
//                    logger.error("get comment list error with sourceId:" + sourceId + " msg:" + commentResult.getMsg());
//                }
//            }else {
//                logger.error("commentResult is null");
//            }
//        }catch (Exception e){
//            logger.error("get comment list error",e);
//        }
//        return null;
//    }
//
//    @Override
//    public ResultVO deleteComment(String newsId, long commentId, String passport) {
//        String sourceId = getSourceId(newsId);
//
//        Map<String,String> params = new HashMap<>();
//        params.put("source_id",sourceId);
//        params.put("comment_id",String.valueOf(commentId));
//        params.put("passport",passport);
//
//        String re = doPost(deleteUrl, params, new HashMap<>());
//        CommentResult commentResult = JSONObject.parseObject(re, CommentResult.class);
//        if(commentResult != null) {
//            if (isSuccess(commentResult.getCode())) {
//                return new ResultVO(commentResult.getData());
//            } else {
//                logger.error("delete error:" + commentResult.getMsg());
//                return new ResultVO(-1, "delete comment[" + commentId + "] error", null);
//            }
//        }else {
//            logger.error("commentResult is null");
//            return ResultVO.ERROR;
//        }
//    }
//
//    @Override
//    public ResultVO likeComment(String passport, String newsId, long commentId) {
//        String sourceId = getSourceId(newsId);
//
//        Map<String,String> params = new HashMap<>();
//        params.put("source_id",sourceId);
//        params.put("comment_id",String.valueOf(commentId));
//        params.put("c_id",passport);
//
//        String re = doPost(likeUrl, params, new HashMap<>());
//        CommentResult commentResult = JSONObject.parseObject(re, CommentResult.class);
//        if(commentResult != null) {
//            if (isSuccess(commentResult.getCode())) {
//                logger.info("user[{}] like comment[{}] success",passport,commentId);
//                return new ResultVO(commentResult.getData());
//            } else {
//                logger.error("like comment error:" + commentResult.getMsg());
//                return new ResultVO(-1, "like comment[" + commentId + "] error", null);
//            }
//        }else {
//            logger.error("commentResult is null");
//            return ResultVO.ERROR;
//        }
//
//    }
//
//    @Override
//    public Map<String,Integer> getLikeStatisticFromRedis(String newsIds) {
//        try {
//            Map<String, Integer> result = new HashMap<>();
//            ValueOperations<String ,Integer> valueOperations = redisTemplate.opsForValue();
//            if (StringUtils.isEmpty(newsIds)) {
//                logger.info("newsIds param is null");
//                return result;
//            }
//
//            String[] split = newsIds.split(",");
//            for (String newsId : split) {
//				if (newsId == null || !NumberUtils.isNumber(newsId)) {
//					continue;
//				}
//                Integer id = Integer.parseInt(newsId);
//                String temKey = String.format(PassportConstant.NEWSID_COMMENT_NUM_REDIS_KEY, newsId);
//                Integer integer = valueOperations.get(temKey);
//                if (integer == null) {
//                    result.put(newsId, 0);
//                } else {
//                    result.put(newsId, integer);
//                }
//                //加入到待更新集合
//                pooledService.addnewsId(id);
//            }
//
//            return result;
//        }catch (Exception e){
//            logger.error("get comment num from redis error",e);
//            return null;
//        }
//    }
//
//    @Override
//    public ResultVO getLikeStatisticFromApi(String newsIds) {
//
//        String pre = CommentBusinessEnum.getSourceIdPreByName("pecker");
//        Map<String, JSONObject> result = new HashMap<>();
//
//        Map<String,String> params = new HashMap<>();
//        StringBuilder sb = new StringBuilder();
//        String[] split = newsIds.split(",");
//        for(String newsId:split){
//            sb.append(pre+newsId+",");
//        }
//        String sourceIds = sb.substring(0,sb.length()-1);
//        params.put("source_ids",sourceIds);
//
//        String re = doGet(countUrl, params);
//        CommentResult commentResult = JSONObject.parseObject(re, CommentResult.class);
//        if(commentResult != null) {
//            if (isSuccess(commentResult.getCode())) {
//                String data = commentResult.getData();
//                Map<String, JSONObject> map = new HashMap<>();
//                Map<String, JSONObject> resultData = JSONObject.parseObject(data, map.getClass());
//                for(Map.Entry<String, JSONObject> entry:resultData.entrySet()){
//                    String key = entry.getKey();
//                    JSONObject value = entry.getValue();
//                    key = key.replace(pre,"");
//                    result.put(key,value);
//                }
//                return new ResultVO(result);
//            } else {
//                logger.error("get statistics error:" + commentResult.getMsg());
//                return new ResultVO(-1, "get statistics error with param:"+newsIds, null);
//            }
//        }else {
//            logger.error("commentResult is null");
//            return ResultVO.ERROR;
//        }
//
//    }
//
//    @Override
//    public ResultVO bumpComment(String topicTitle, String newsId, String topicUrl, String replyId, String content, String ip, String passport, String attachmentInfo, long commentTime) {
//
//        String sourceId = getSourceId(newsId);
//
//        Map<String,String> params = new HashMap<>();
//        params.put("topic_title",topicTitle);
//        params.put("source_id",sourceId);
//        params.put("topic_url",topicUrl);
//        if(StringUtils.isNotEmpty(replyId)) {
//            params.put("reply_id", replyId);
//        }else{
//            params.put("reply_id", "0");
//        }
//        if(StringUtils.isNotEmpty(attachmentInfo)) {
//            params.put("attachment_info", attachmentInfo);
//        }
//        params.put("content",content);
//        params.put("channel_source_id",channelId);
//        params.put("comment_time",String.valueOf(commentTime));
//        params.put("ip",ip);
//        params.put("passport",passport);
//
//        String re = doPost(bumpUrl, params, new HashMap<>());
//        CommentResult commentResult = JSONObject.parseObject(re, CommentResult.class);
//
//        if(commentResult != null) {
//            if (isSuccess(commentResult.getCode())) {
//                logger.info("newsId[{}] bump success",newsId);
//                return new ResultVO(commentResult.getData());
//            } else {
//                return new ResultVO(-1, commentResult.getMsg(), commentResult.getData());
//            }
//        }else {
//            logger.error("commentResult is null");
//            return ResultVO.ERROR;
//        }
//    }
//
//    @Override
//    public ResultVO waterDelete(String newsId, String commentIds) {
//        String sourceId = getSourceId(newsId);
//        Map<String,String> params = new HashMap<>();
//        params.put("comment_ids",commentIds);
//        params.put("source_id",sourceId);
//        String re = doPost(bumpUrl, params, new HashMap<>());
//        CommentResult commentResult = JSONObject.parseObject(re, CommentResult.class);
//        if(commentResult != null) {
//            if (isSuccess(commentResult.getCode())) {
//                logger.info("newsId[{}] delete watering success",newsId);
//                return new ResultVO(commentResult.getData());
//            } else {
//                return new ResultVO(-1, commentResult.getMsg(), commentResult.getData());
//            }
//        }else {
//            logger.error("delete watering error");
//            return ResultVO.ERROR;
//        }
//    }
//
//    @Override
//    public ResultVO setCommentPersonInfo(long userId, String userName, String userAvatar) {
//        try {
//            String redisKey = String.format(PassportConstant.COMMENT_USER_NAME_AND_AVATAR_INFO_REDIS_KEY, String.valueOf(userId));
//            String val = userName + sep + userAvatar;
//            ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
//            valueOperations.set(redisKey, val);
//            return ResultVO.ok();
//        }catch (Exception e){
//            logger.error("set user["+userId+"] info error",e);
//            return ResultVO.ERROR;
//        }
//    }
//
//    @Override
//    public String getCommentPersonInfo(long userId) {
//        try {
//            String redisKey = String.format(PassportConstant.COMMENT_USER_NAME_AND_AVATAR_INFO_REDIS_KEY, String.valueOf(userId));
//            ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
//            String o = valueOperations.get(redisKey);
//            return o;
//        }catch (Exception e){
//            logger.error("set user["+userId+"] info error",e);
//        }
//        return null;
//    }
//
//    @Override
//    public void commentsWatering(List<Comment> commentList) {
//        if(CollectionUtils.isEmpty(commentList)){
//            return;
//        }
//    }
//
//    private String getSourceId(String newsId){
//        Assert.notNull(newsId,"newsId cannot be null");
//        String pre = CommentBusinessEnum.getSourceIdPreByName("pecker");
//        return pre + newsId;
//    }
//
//    /**
//     * post调用评论系统接口
//     * @param url
//     * @param params
//     * @param data
//     * @return
//     */
//    private String doPost(String url,Map<String,String> params,Map<String,String> data){
//        try {
//            String finalUrl = PassportUtils.getCommentUrl(url, params, appKey, secret);
//            String re = HttpUtils.httpPost(finalUrl, data, "UTF-8");
//            if(StringUtils.isNotEmpty(re)){
//                return re;
//            }else {
//                logger.info("do post with url[{}] return null",finalUrl);
//            }
//        }catch (Exception e){
//            logger.error("error occur while do post with url:"+url,e);
//        }
//        return null;
//    }
//
//    /**
//     * get调用评论系统接口
//     * @param url
//     * @param params
//     * @return
//     */
//    private String doGet(String url,Map<String,String> params){
//        try {
//            String finalUrl = PassportUtils.getCommentUrl(url, params, appKey, secret);
//            String re = HttpUtils.httpGet(finalUrl, "UTF-8");
//            if(StringUtils.isNotEmpty(re)){
//                return re;
//            }else {
//                logger.info("do get with url[{}] return null",finalUrl);
//            }
//        }catch (Exception e){
//            logger.error("error occur while do get with url:"+url,e);
//        }
//        return null;
//    }
//
//    private void handleComment(Comment comment){
//        comment.handleDate();
//        if(comment == null){
//            return;
//        }
//        long userId = comment.getUserId();
//        String commentPersonInfo = getCommentPersonInfo(userId);
//        if(StringUtils.isEmpty(commentPersonInfo)){
//            return;
//        }
//        String[] split = commentPersonInfo.split(sep);
//        if(split.length<2){
//            return;
//        }
//        comment.setUserName(split[0]);
//        comment.setUserAvatar(split[1]);
//        if(CollectionUtils.isNotEmpty(comment.getParents())){
//            for(Comment com:comment.getParents()){
//                handleComment(com);
//            }
//        }
//    }
//
//    private void addUserAvatar(Map<String, CommentUser> users) {
//        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
//        String redisKey;
//        for(String key:users.keySet()){
//            redisKey = String.format(PassportConstant.COMMENT_USER_NAME_AND_AVATAR_INFO_REDIS_KEY,key);
//            String s = valueOperations.get(redisKey);
//            if(s != null){
//                String[] split = s.split(sep);
//                CommentUser commentUser = users.get(key);
//                commentUser.setUserName(split[0]);
//                commentUser.setAvatar(split[1]);
//            }
//        }
//    }
//
//    private boolean isSuccess(int code){
//        return code == PassportConstant.OK ||
//               code == PassportConstant.NEW ||
//               code == PassportConstant.HYTRIX;
//    }
//}
