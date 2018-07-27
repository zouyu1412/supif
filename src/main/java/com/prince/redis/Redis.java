package com.prince.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by zouy-c on 2018/3/7.
 */
public class Redis {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.56.128");
        System.out.println("192.168.56.128");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
    }
}
