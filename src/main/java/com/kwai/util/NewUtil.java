package com.kwai.util;

/**
 * @author zouyu <zouyu@kuaishou.com>
 * Created on 2020-06-29
 */
public class NewUtil {

    public static void main(String[] args) {
        String[] st = "gifshow_ug_nebula_user_relation_dt_snapshot.__binlog_timestamp,gifshow_ug_nebula_user_relation_dt_snapshot.__binlog_type,gifshow_ug_nebula_user_relation_dt_snapshot.id,gifshow_ug_nebula_user_relation_dt_snapshot.user_id,gifshow_ug_nebula_user_relation_dt_snapshot.parent_id,gifshow_ug_nebula_user_relation_dt_snapshot.invite_type,gifshow_ug_nebula_user_relation_dt_snapshot.relation_code,gifshow_ug_nebula_user_relation_dt_snapshot.open_id,gifshow_ug_nebula_user_relation_dt_snapshot.union_id,gifshow_ug_nebula_user_relation_dt_snapshot.head_img,gifshow_ug_nebula_user_relation_dt_snapshot.nick_name,gifshow_ug_nebula_user_relation_dt_snapshot.relation_time,gifshow_ug_nebula_user_relation_dt_snapshot.register_time,gifshow_ug_nebula_user_relation_dt_snapshot.create_time,gifshow_ug_nebula_user_relation_dt_snapshot.update_time,gifshow_ug_nebula_user_relation_dt_snapshot.channel,gifshow_ug_nebula_user_relation_dt_snapshot.dt\n".split(",");
        for(String s:st){
            System.out.println(s);
        }
    }

}
