package com.zouyu.leetcode.DataStructure;

import java.util.*;

/**
 * 推特简易版
 */
class Twitter {

    long ind;
    Map<Integer,List<Integer>> userToTwitterIds;
    Map<Integer, HashSet<Integer>> userToFollow;
    Map<Integer, Long> twitterToDeliverIndex;

    /** Initialize your data structure here. */
    public Twitter() {
        ind = 0L;
        userToFollow = new HashMap<>();
        userToTwitterIds =  new HashMap<>();
        twitterToDeliverIndex = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        List<Integer> twi = userToTwitterIds.get(userId);
        if(twi == null){
            twi = new ArrayList<>();
            twi.add(tweetId);
            userToTwitterIds.put(userId,twi);
        }else{
            twi.add(tweetId);   
        }
        twitterToDeliverIndex.put(tweetId,ind++);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> fow = userToFollow.get(userId);
        List<Integer> twis = userToTwitterIds.get(userId);
        List<Integer> ts;
        if(twis == null) {
             ts = new ArrayList<>();
        }else{
            ts = new ArrayList<>(twis);
        }
        if(fow != null && fow.size()>0) {
            for (int x : fow) {
                List<Integer> tss = userToTwitterIds.get(x);
                if (tss != null) {
                    ts.addAll(tss);
                }
            }
        }
        if(ts == null || ts.size() == 0){
            return Collections.emptyList();
        }
        ts.sort((o1, o2) -> twitterToDeliverIndex.get(o2).compareTo(twitterToDeliverIndex.get(o1)));
        return ts.size()>10?ts.subList(0,10):ts;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(followeeId == followerId){
            return;
        }
        HashSet<Integer> fow = userToFollow.get(followerId);
        if(fow == null){
            fow = new HashSet<>();
            fow.add(followeeId);
            userToFollow.put(followerId,fow);
        }else {
            fow.add(followeeId);
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        HashSet<Integer> fow = userToFollow.get(followerId);
        if(fow != null){
            fow.remove(followeeId);
        }
    }

    public static void main(String[] args) {
//["Twitter","postTweet","getNewsFeed","follow","getNewsFeed","unfollow","getNewsFeed"]
//[[],[1,1],[1],[2,1],[2],[2,1],[2]]
        Twitter twitter = new Twitter();
        twitter.postTweet(1,1);
        List<Integer> newsFeed = twitter.getNewsFeed(1);
        System.out.println(newsFeed);
        twitter.follow(2,1);
        List<Integer> newsFeed1 = twitter.getNewsFeed(2);
        System.out.println(newsFeed1);
        twitter.unfollow(2,1);
        List<Integer> newsFeed2 = twitter.getNewsFeed(2);
        System.out.println(newsFeed2);

    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */