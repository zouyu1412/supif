//package com.tem;
//
//import com.sohu.auto.content.common.bean.Comment;
//import com.sohu.auto.content.common.pojo.ResultVO;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author:zouyu
// * @Date:2019/3/4 17:15
// */
//public interface CommentService {
//
//    /**
//     * 发表评论
//     * @param topicTitle
//     * @param newsId
//     * @param topicUrl
//     * @param replyId
//     * @param content
//     * @param ip
//     * @param passport
//     * @param mediaId
//     * @param attachmentInfo
//     * @param from
//     * @return
//     */
//    ResultVO deliverComment(String topicTitle, String newsId, String topicUrl, String replyId, String content, String ip, String passport, String mediaId, String attachmentInfo, String from);
//
//    /**
//     * 获取评论列表
//     * @param newsId
//     * @param pageNo
//     * @param pageSize
//     * @return
//     */
//    ResultVO listComment(String newsId, Integer pageNo, Integer pageSize);
//
//    /**
//     * 删除评论
//     * @param newsId
//     * @param commentId
//     * @param passport
//     * @return
//     */
//    ResultVO deleteComment(String newsId, long commentId, String passport);
//
//    /**
//     * 点赞
//     * @param passport
//     * @param newsId
//     * @param commentId
//     * @return
//     */
//    ResultVO likeComment(String passport, String newsId, long commentId);
//
//    /**
//     * 获取评论点赞情况
//     * @param newsIds
//     * @return
//     */
//    ResultVO getLikeStatisticFromApi(String newsIds);
//
//    /**
//     * 首页使用 业务id -> 评论数
//     * @param newsIds
//     * @return
//     */
//    Map<String,Integer> getLikeStatisticFromRedis(String newsIds);
//
//    /**
//     * 评论注水
//     * @param topicTitle
//     * @param newsId
//     * @param topicUrl
//     * @param replyId
//     * @param content
//     * @param ip
//     * @param passport
//     * @param attachmentInfo
//     * @param commentTime
//     * @return
//     */
//    ResultVO bumpComment(String topicTitle, String newsId, String topicUrl, String replyId, String content, String ip, String passport, String attachmentInfo, long commentTime);
//
//    /**
//     * 删除注水评论
//     * @param newsId
//     * @param commentIds
//     * @return
//     */
//    ResultVO waterDelete(String newsId, String commentIds);
//
//    ResultVO setCommentPersonInfo(long userId, String userName, String userAvatar);
//
//    String getCommentPersonInfo(long userId);
//
//    void commentsWatering(List<Comment> commentList);
//}
