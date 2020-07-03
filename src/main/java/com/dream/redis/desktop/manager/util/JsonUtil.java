package com.dream.redis.desktop.manager.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dream
 * @version v1.0.0
 * @description json 工具类
 * @createTime 2020/7/2 1:33 下午
 */
public class JsonUtil {

    private JsonUtil() {}

    /**
     * 美化 json 字符串
     * @param str
     * @return
     */
    public static String prettyJson(String str) {
        if (StringUtils.isEmpty(str)) return str;
        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(str);
        } catch (Exception e) {
            return str;
        }
        return JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
    }
}
