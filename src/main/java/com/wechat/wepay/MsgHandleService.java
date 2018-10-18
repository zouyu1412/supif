//package com.wechat.wepay;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.sohu.auto.db.data.api.thrift.client.DBClient;
//import com.sohu.auto.wx.constant.WechatConstant;
//import com.sohu.auto.wx.data.message.ImageMessage;
//import com.sohu.auto.wx.data.message.NewsMessage;
//import com.sohu.auto.wx.data.message.TextMessage;
//import com.sohu.auto.wx.utils.util.WechatCommonUtil;
//import com.sohu.auto.wx.utils.WechatConfigLoader;
//import com.sohu.auto.wx.utils.aes.WXBizMsgCrypt;
//import org.apache.commons.lang.StringUtils;
//import org.apache.thrift.TException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import scala.Int;
//import sun.misc.LRUCache;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Vector;
//
//@Service("msgHandleService")
//public class MsgHandleService {
//
//    private static Logger logger = LoggerFactory.getLogger(MsgHandleService.class);
//
//    @Resource(name = "wechatActivityService")
//    WechatActivityService wechatActivityService;
//
//    @Autowired
//    DBClient dbClient;
//
//    /**
//     * 根据request进行消息回复
//     *
//     * @param request
//     * @return
//     */
//    public String reply(HttpServletRequest request) throws Exception {
//
//        String clearText = getClearText(request);
//
//        boolean canHandle = wechatActivityService.handleResend(clearText);
//        if(!canHandle){
//            //重复消息 直接返回
//            return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//        }
//
//        Map<String, String> xmlMap = WechatCommonUtil.parseXml(clearText);
//        if (null == xmlMap || xmlMap.isEmpty()) {
//            logger.info("接收到的消息为空");
//            return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//        }
//
//        String resultXML = null;
//
//        String openId = xmlMap.get(WechatConstant.WECHAT_MSG_FROMUSERNAME);
//        String wechatAccount = xmlMap.get(WechatConstant.WECHAT_MSG_TOUSERNAME);
//
//        if (xmlMap.containsKey(WechatConstant.WECHAT_MSG_EVENT)) {
//            //触发事件
//            String event = xmlMap.get(WechatConstant.WECHAT_MSG_EVENT);
//            if (event.equals(WechatConstant.WECHAT_MSG_SUBSCRIBLE)) {
//                //未关注用户关注事件
//                if (xmlMap.containsKey(WechatConstant.WECHAT_MSG_EVENTKEY)) {
//                    logger.info("用户" + openId + "扫描带参二维码关注了公众号");
//                    TextMessage textMessage = new TextMessage();
//                    textMessage.setFromUserName(wechatAccount);
//                    textMessage.setToUserName(openId);
//
//                    String sceneKey = xmlMap.get(WechatConstant.WECHAT_MSG_EVENTKEY);
//                    if (!StringUtils.isEmpty(sceneKey)) {
//                        //扫描带参二维码关注
//
//                        String sceneValue = sceneKey.replace(WechatConstant.WECHAT_MSG_EVENTKEY_PREFIX, "");
//                        logger.info("场景值为:" + sceneKey);
//                        //根据场景进行内容选取
//                        if (sceneValue.startsWith("tijianquan")) {
//
//                            String[] split = sceneValue.split("---");
//                            String realSceneKey = split[0];
//                            String userOpenId = split[1];
//                            //添加活动扫描积分
//                            String re1 = dbClient.incActScore(realSceneKey);
//                            JSONObject re1Object = JSONObject.parseObject(re1);
//                            if (re1Object.getInteger("code") != 0) {
//                                logger.error("活动场景" + realSceneKey + "积分增长失败");
//
//                            }
//
//                            //获取图片
//                            String mediaId = wechatActivityService.getUserActSharePicture(openId, realSceneKey,WechatConstant.PIC_PATH_TIJIANQUAN);
//                            logger.info("用户{} {} {} {}", openId, mediaId, "", sceneKey);
//                            String re3 = dbClient.addUserShareRecord(openId, mediaId, "", realSceneKey);
//                            JSONObject re3Object = JSONObject.parseObject(re3);
//                            logger.info("插入用户记录结果:"+re3Object.toJSONString());
//                            if(null != re3Object && null != re3Object.getInteger("code") && re3Object.getInteger("code") == 0){
//                                //新用户给分享用户助力成功
//                                JSONObject userInfo = WechatCommonUtil.getUserInfo(userOpenId);
//                                String tStr = "";
//                                if(null != userInfo){
//                                    tStr = userInfo.getString("nickname");
//                                }
//
//                                WechatCommonUtil.sendCustomServiceMsg(openId, WechatConstant.WECHAT_MSG_FENSILIEBIAN1.replace("FRIEND",tStr));
//                                String re2 = dbClient.incUserScore(userOpenId, realSceneKey);
//                                JSONObject re2Object = JSONObject.parseObject(re2);
//                                logger.info("被关注用户积分增长发送消息:" + re2Object.toJSONString());
//                                if (null != re2Object && null != re2Object.getInteger("code") && re2Object.getInteger("code") >= 0) {
//                                    logger.error("被关注用户" + userOpenId + "积分增长成功");
//                                    String result = re2Object.getString("result");
//                                    if(re2Object.getInteger("code") == 0) {
//                                        String reMsg = WechatConstant.WECHAT_MSG_FENSILIEBIAN5.replace("CODE", result.split("---")[1]);
//                                        WechatCommonUtil.sendCustomServiceMsg(userOpenId, reMsg);
//                                    }else{
//                                        Integer codeVal = re2Object.getInteger("code");
//                                        if(StringUtils.isEmpty(result)){
//                                            logger.info("用户积分值："+codeVal+"过剩");
//                                        }else {
//                                            String[] tem = result.split("---");
//                                            String temMsg = WechatConstant.WECHAT_MSG_FENSILIEBIAN4.replace("XXX",String.valueOf(codeVal)).replace("YYY",tem[0]).replace("ZZZ",tem[2]);
//                                            WechatCommonUtil.sendCustomServiceMsg(userOpenId,temMsg);
//                                        }
//                                    }
//                                } else {
//                                    logger.error("被关注用户" + userOpenId + "积分增长失败");
//                                }
//                            }else if(re3Object.getInteger("code") == 1){
//                                logger.error("用户"+openId+"插入图片id数据失败");
//                                return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//                            }
//
//                            //发送客户消息
//                            WechatCommonUtil.sendCustomServiceMsg(openId, WechatConstant.WECHAT_MSG_FENSILIEBIAN2);
//                            ImageMessage imageMessage = new ImageMessage();
//                            imageMessage.setFromUserName(wechatAccount);
//                            imageMessage.setToUserName(openId);
//                            imageMessage.setMediaId(mediaId);
//                            resultXML = imageMessage.toXmlString();
//                            return getSecretText(resultXML);
//
//                        }else if(sceneValue.startsWith("danchequan")) {
//
//                            String re1 = dbClient.incActScore(sceneValue);
//                            JSONObject re1Object = JSONObject.parseObject(re1);
//                            if (re1Object.getInteger("code") != 0) {
//                                logger.error("活动场景" + sceneValue + "积分增长失败:" + re1Object.getString("msg"));
//                            }
//                            textMessage.setContent(WechatConstant.WECHAT_MSG_DANCHEQUAN3);
//                            String res = textMessage.toXmlString();
//                            return getSecretText(res);
//
//                        }else if (sceneValue.startsWith("tuiguang")) {
//                            String re1 = dbClient.incActScore(sceneValue);
//                            JSONObject re1Object = JSONObject.parseObject(re1);
//                            if (re1Object.getInteger("code") != 0) {
//                                logger.error("活动场景" + sceneValue + "积分增长失败:" + re1Object.getString("msg"));
//                            }
//                            textMessage.setContent(WechatConstant.WECHAT_MSG_SUBSCRIBLE_RESPONSE);
//                            String res = textMessage.toXmlString();
//                            return getSecretText(res);
//                        } else if (sceneValue.startsWith("shanghaichezhan")) {
//                            String[] split = sceneValue.split("---");
//                            String realSceneKey = split[0];
//                            String userOpenId = split[1];
//                            //添加活动扫描积分
//                            String re1 = dbClient.incActScore(realSceneKey);
//                            JSONObject re1Object = JSONObject.parseObject(re1);
//                            if (re1Object.getInteger("code") != 0) {
//                                logger.error("活动场景" + realSceneKey + "积分增长失败");
//                                //TODO 日后补偿   目前先忽略
//                            }
//
//                            //用户积分增加
//                            String re2 = dbClient.incUserScore(userOpenId, realSceneKey);
//
//                            JSONObject re2Object = JSONObject.parseObject(re2);
//                            logger.info("被关注用户积分增长发送消息:" + re2Object.toJSONString());
//                            if (re2Object.getInteger("code") != 0) {
//                                logger.error("被关注用户" + userOpenId + "积分增长失败");
//                                //TODO 日后补偿   目前先忽略
//                            } else {
//                                String prize = re2Object.getString("result");
//                                String[] split1 = prize.split("---");
//                                String prizeCode = split1[1];
//                                String msg = WechatConstant.WECHAT_MSG_SHANGHAICHEZHAN2.replace("CODE", prizeCode);
//                                JSONObject jsonObject = WechatCommonUtil.sendCustomServiceMsg(userOpenId, msg);
//                                logger.info("被关注用户积分增长发送消息:" + jsonObject.toJSONString());
//                                if (jsonObject.getInteger("errcode") != 0) {
//                                    logger.error("被关注用户推送卡券失败" + jsonObject.getString("errmsg"));
//                                }
//                            }
//                            //获取图片
//                            String mediaId = wechatActivityService.getUserActSharePicture(openId, realSceneKey,WechatConstant.PIC_PATH_SHANGHAICHEZHAN);
//
//                            String replyMsg;
//                            if (null != mediaId) {
//                                //新用户插入记录
//                                String re3 = dbClient.addUserShareRecord(openId, mediaId, "", realSceneKey);
//                                JSONObject re3Object = JSONObject.parseObject(re3);
//                                logger.info("用户{} {} {} {}", openId, mediaId, "", sceneKey);
//                                logger.info("用户插入活动记录:" + re3Object.getInteger("code") + " " + re3Object.getString("result"));
//                                //发奖品
//                                String prize = dbClient.giveOutPrizeCode(openId, realSceneKey);
//                                JSONObject jsonObject = JSONObject.parseObject(prize);
//                                if (null != jsonObject && null != jsonObject.getInteger("code") && jsonObject.getInteger("code") == 0) {
//                                    logger.info("用户" + openId + "发放奖品成功");
//                                    String pri = jsonObject.getString("result").split("---")[1];
//                                    replyMsg = WechatConstant.WECHAT_MSG_SHANGHAICHEZHAN1.replace("CODE", pri);
//                                    //发送客户消息
//                                    WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
//                                } else if (jsonObject.getInteger("code") == 2) {
//                                    replyMsg = WechatConstant.WECHAT_MSG_SHANGHAICHEZHAN4;
//                                    //发送客户消息
//                                    WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
//                                }
//
//                            } else {
//                                //失败补偿
//                                replyMsg = WechatConstant.WECHAT_MSG_SUBSCRIBLE_RESPONSE;
//                                WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
//                                return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//                            }
//
//                            //回复用户分享图片
//                            ImageMessage imageMessage = new ImageMessage();
//                            imageMessage.setFromUserName(wechatAccount);
//                            imageMessage.setToUserName(openId);
//                            imageMessage.setMediaId(mediaId);
//                            resultXML = imageMessage.toXmlString();
//                            return getSecretText(resultXML);
//                        }
//                    } else {
//                        //普通关注
//                        logger.info("用户" + openId + "普通关注了公众号");
//                        textMessage.setContent(WechatConstant.WECHAT_MSG_SUBSCRIBLE_RESPONSE);
//                    }
//
//                    resultXML = textMessage.toXmlString();
//                }
//            } else if (event.equals(WechatConstant.WECHAT_MSG_SCAN)) {
//                //已关注用户扫描事件
//
//                TextMessage textMessage = new TextMessage();
//                textMessage.setFromUserName(wechatAccount);
//                textMessage.setToUserName(openId);
//
//                String s = xmlMap.get(WechatConstant.WECHAT_MSG_EVENTKEY);
//
//                s = s.replace(WechatConstant.WECHAT_MSG_EVENTKEY_PREFIX, "");
//                logger.info("EventKey:" + s);
//                if (StringUtils.isEmpty(s)) {
//                    //扫描普通二维码
//                    logger.info("已关注用户" + openId + "扫描了普通二维码");
//                    return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//                } else if(s.startsWith("danchequan")){
////                    logger.info("已关注用户" + openId + "扫描了带参" + s + "二维码");
////
////                    String sceneValue = s.replace(WechatConstant.WECHAT_MSG_EVENTKEY_PREFIX, "");
////                    String[] split = sceneValue.split("---");
////                    String realSceneKey = split[0];
////                    String userOpenId = split[1];
////                    //添加活动扫描积分
////                    String re1 = dbClient.incActScore(realSceneKey);
////                    JSONObject re1Object = JSONObject.parseObject(re1);
////                    if (re1Object.getInteger("code") != 0) {
////                        logger.error("活动场景" + realSceneKey + "积分增长失败");
////                    }
////
////                    String mediaId = wechatActivityService.getUserActSharePicture(openId, realSceneKey,WechatConstant.PIC_PATH_DANCHEQUAN);
////                    String replyMsg;
////                    if (null != mediaId) {
////                        //新用户插入记录
////                        dbClient.addUserShareRecord(openId, mediaId, "", realSceneKey);
////
////                        replyMsg = WechatConstant.WECHAT_MSG_DANCHEQUAN1;
////
////                    } else {
////                        //失败补偿
////                        logger.error("获取用户" + userOpenId + "图片失败");
////                        replyMsg = WechatConstant.WECHAT_MSG_SUBSCRIBLE_RESPONSE;
////                        WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
////                        return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
////                    }
////                    //发送客户消息
////                    WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
////
////                    //回复用户分享图片
////                    ImageMessage imageMessage = new ImageMessage();
////                    imageMessage.setFromUserName(wechatAccount);
////                    imageMessage.setToUserName(openId);
////                    imageMessage.setMediaId(mediaId);
////                    resultXML = imageMessage.toXmlString();
////                    return getSecretText(resultXML);
//
//                    String re1 = dbClient.incActScore(s);
//                    JSONObject re1Object = JSONObject.parseObject(re1);
//                    if (re1Object.getInteger("code") != 0) {
//                        logger.error("活动场景" + s + "积分增长失败:" + re1Object.getString("msg"));
//                    }
//                    textMessage.setContent(WechatConstant.WECHAT_MSG_DANCHEQUAN3);
//                    String res = textMessage.toXmlString();
//                    return getSecretText(res);
//                }else if (s.startsWith("tuiguang")) {
//                    logger.info("已关注用户" + openId + "扫描了带参" + s + "二维码");
//                    textMessage.setContent(WechatConstant.WECHAT_MSG_SUBSCRIBLE_RESPONSE);
//                } else if (s.startsWith("tijianquan")) {
//                    logger.info("已关注用户" + openId + "扫描了带参" + s + "二维码");
//
//                    String sceneValue = s.replace(WechatConstant.WECHAT_MSG_EVENTKEY_PREFIX, "");
//                    String[] split = sceneValue.split("---");
//                    String realSceneKey = split[0];
//                    String userOpenId = split[1];
//                    //添加活动扫描积分
//                    String re1 = dbClient.incActScore(realSceneKey);
//                    JSONObject re1Object = JSONObject.parseObject(re1);
//                    if (re1Object.getInteger("code") != 0) {
//                        logger.error("活动场景" + realSceneKey + "积分增长失败");
//                    }
//
//                    String mediaId = wechatActivityService.getUserActSharePicture(openId, realSceneKey,WechatConstant.PIC_PATH_TIJIANQUAN);
//                    String replyMsg;
//                    if (null != mediaId) {
//                        //新用户插入记录
//                        dbClient.addUserShareRecord(openId, mediaId, "", realSceneKey);
//
//                        replyMsg = WechatConstant.WECHAT_MSG_FENSILIEBIAN2;
//
//                    } else {
//                        //失败补偿
//                        logger.error("获取用户" + userOpenId + "图片失败");
//                        replyMsg = WechatConstant.WECHAT_MSG_SUBSCRIBLE_RESPONSE;
//                        WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
//                        return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//                    }
//                    //发送客户消息
//                    WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
//
//                    //回复用户分享图片
//                    ImageMessage imageMessage = new ImageMessage();
//                    imageMessage.setFromUserName(wechatAccount);
//                    imageMessage.setToUserName(openId);
//                    imageMessage.setMediaId(mediaId);
//                    resultXML = imageMessage.toXmlString();
//                    return getSecretText(resultXML);
//
//                } else if (s.startsWith("shanghaichezhan")) {
//                    logger.info("已关注用户" + openId + "扫描了带参" + s + "二维码");
//
//                    String sceneValue = s.replace(WechatConstant.WECHAT_MSG_EVENTKEY_PREFIX, "");
//                    String[] split = sceneValue.split("---");
//                    String realSceneKey = split[0];
//                    String userOpenId = split[1];
//                    //添加活动扫描积分
//                    String re1 = dbClient.incActScore(realSceneKey);
//                    JSONObject re1Object = JSONObject.parseObject(re1);
//                    if (re1Object.getInteger("code") != 0) {
//                        logger.error("活动场景" + realSceneKey + "积分增长失败");
//                        //TODO 日后补偿   目前先忽略
//                    }
//                    //用户积分增加
////                                String re2 = dbClient.incUserScore(userOpenId,realSceneKey);
//                    //获取图片
//
//                    String mediaId = wechatActivityService.getUserActSharePicture(openId, realSceneKey,WechatConstant.PIC_PATH_SHANGHAICHEZHAN);
//                    String replyMsg;
//                    if (null != mediaId) {
//                        //新用户插入记录
//                        dbClient.addUserShareRecord(openId, mediaId, "", realSceneKey);
//
//                        replyMsg = WechatConstant.WECHAT_MSG_SHANGHAICHEZHAN3;
//
//                    } else {
//                        //失败补偿
//                        logger.error("获取用户" + userOpenId + "图片失败");
//                        replyMsg = WechatConstant.WECHAT_MSG_SUBSCRIBLE_RESPONSE;
//                        WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
//                        return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//                    }
//                    //发送客户消息
//                    WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
//
//                    //回复用户分享图片
//                    ImageMessage imageMessage = new ImageMessage();
//                    imageMessage.setFromUserName(wechatAccount);
//                    imageMessage.setToUserName(openId);
//                    imageMessage.setMediaId(mediaId);
//                    resultXML = imageMessage.toXmlString();
//                    return getSecretText(resultXML);
//
//                }
//                resultXML = textMessage.toXmlString();
//            } else if (event.equals(WechatConstant.WECHAT_MSG_CLICK)) {
//
//                TextMessage textMessage = new TextMessage();
//                textMessage.setFromUserName(wechatAccount);
//                textMessage.setToUserName(openId);
//
//                String eventkey = xmlMap.get("EventKey");
//                logger.info("用户" + openId + "点击了自定义菜单,EventKey:" + eventkey);
//                if (eventkey.equals("shanghaichezhan1")) {
//                    String mediaId = wechatActivityService.getUserActSharePicture(openId, eventkey, WechatConstant.PIC_PATH_SHANGHAICHEZHAN);
//                    String re3 = dbClient.addUserShareRecord(openId, mediaId, "", eventkey);
//                    JSONObject re3Object = JSONObject.parseObject(re3);
//                    logger.info("用户{} {} {} {}", openId, mediaId, "", eventkey);
//                    logger.info("用户插入活动记录:" + re3Object.getInteger("code") + " " + re3Object.getString("result"));
//                    String replyMsg = WechatConstant.WECHAT_MSG_SHANGHAICHEZHAN3;
//                    WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
//
//                    //回复用户图片
//                    ImageMessage imageMessage = new ImageMessage();
//                    imageMessage.setFromUserName(wechatAccount);
//                    imageMessage.setToUserName(openId);
//                    imageMessage.setMediaId(mediaId);
//                    resultXML = imageMessage.toXmlString();
//                    return getSecretText(resultXML);
//                }else if(eventkey.equals("tijianquan")){
//                    String mediaId = wechatActivityService.getUserActSharePicture(openId, eventkey, WechatConstant.PIC_PATH_TIJIANQUAN);
//                    String re3 = dbClient.addUserShareRecord(openId, mediaId, "", eventkey);
//                    JSONObject re3Object = JSONObject.parseObject(re3);
//                    logger.info("用户{} {} {} {}", openId, mediaId, "", eventkey);
//                    logger.info("用户插入活动记录:" + re3Object.getInteger("code") + " " + re3Object.getString("result"));
//                    String replyMsg = WechatConstant.WECHAT_MSG_FENSILIEBIAN2;
//                    WechatCommonUtil.sendCustomServiceMsg(openId, replyMsg);
//
//                    //回复用户图片
//                    ImageMessage imageMessage = new ImageMessage();
//                    imageMessage.setFromUserName(wechatAccount);
//                    imageMessage.setToUserName(openId);
//                    imageMessage.setMediaId(mediaId);
//                    resultXML = imageMessage.toXmlString();
//                    return getSecretText(resultXML);
//                }
////                    NewsMessage newsMessage = new NewsMessage();
////                    replyMap.put(WechatConstant.WECHAT_MSG_ARTICLES)
//
//                //获取图文素材
////                    JSONObject article = WechatCommonUtil.getArticleList(WechatCommonUtil.getWxAccessToken(WechatConfigLoader.getAppId(), WechatConfigLoader.getAppSecret()));
////                    JSONArray items = article.getJSONArray("item");
////                    JSONObject jsonObject = items.getJSONObject(0);
////                    JSONObject content = jsonObject.getJSONObject("content");
////                    JSONArray news_item = content.getJSONArray("news_item");
////                    for (int i = 0; i < news_item.size(); i++) {
////                        news_item.getJSONObject(i);
////
////                    }
////                    Map<String, String> map;
//
//
//            } else {
//                //TODO 待开发
//                logger.info("用户" + openId + "触发了其他事件");
//                return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//            }
//
//            return getSecretText(resultXML);
//        } else {
//            //TODO 信息交互，待开发
//            logger.info("收到用户" + openId + "普通消息");
//        }
//
//
//        return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//    }
//
//    //获取明文
//    private String getClearText(HttpServletRequest request) {
//        try {
//            String postData = WechatCommonUtil.readRequestBody(request);
//            String signature = request.getParameter("msg_signature");
//            String timestamp = request.getParameter("timestamp");
//            String nonce = request.getParameter("nonce");
//
//            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(WechatConstant.TOKEN, WechatConstant.ENCODING_AES_KEY, WechatConfigLoader.getAppId());
//
////            logger.info("即将对消息进行解密--postData:[{}],signature:[{}],timestamp:[{}],nonce:[{}]",postData,signature,timestamp,nonce);
//
//            //解密
//            String mingwen = wxBizMsgCrypt.decryptMsg(signature, timestamp, nonce, postData);
//            logger.info("解密后的明文为：" + mingwen);
//
//            return mingwen;
//        } catch (Exception e) {
//            logger.error("获取明文异常", e);
//            return null;
//        }
//    }
//
//    //生成密文
//    private String getSecretText(String reply) {
//
//        if (StringUtils.isEmpty(reply)) {
//            return WechatConstant.WECHAT_MSG_BASE_RESPONSE;
//        }
//
//        try {
//            logger.info("进行加密：" + reply);
//            WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(WechatConstant.TOKEN, WechatConstant.ENCODING_AES_KEY, WechatConfigLoader.getAppId());
//            String result = wxBizMsgCrypt.encryptMsg(reply, String.valueOf(WechatCommonUtil.createTimestamp()), WechatCommonUtil.createNonceStr());
//            logger.debug("加密后:" + result);
//            return result;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//}
