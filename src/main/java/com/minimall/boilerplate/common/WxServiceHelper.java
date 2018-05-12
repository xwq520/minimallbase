package com.minimall.boilerplate.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取AccessToken
 *
 * Created by xiangwq
 * on 2017/12/14 0014.
 */
public class WxServiceHelper {

  /**
   * 获取 微信公众平台 accessToken
   * @param comKey
   * @param host
   * @return
   * @throws Exception
   */
  public static String  getAccessToken(String comKey,String host) throws Exception{
    if(CheckUtils.isEmpty(comKey) || CheckUtils.isEmpty(host)) {
      return "";
    }
    Map map2 = new HashMap();
    map2.put("key", comKey);
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "/wechatService/getAccessToken");
    String result = RestServiceHelper.restPost(builder, JSONObject.toJSONString(map2));
    if(!CheckUtils.isEmpty(result)){
      JSONObject obj = (JSONObject)JSONObject.parse(result);
      if(obj.getLong("code") >= 0){
        return obj.getString("accessToken");
      }
      return "";
    }
    return "";
  }

  /**
   * 获取 微信用户信息
   * @param openId 参数封装
   * @return wechatUser 结构体
   * @throws Exception
   */
  public static JSONObject getWechatInfo(String openId,String comKey,String host) throws Exception{
    if(CheckUtils.isEmpty(comKey) || CheckUtils.isEmpty(host) || CheckUtils.isEmpty(openId)) {
      return null;
    }
    //
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "/wechatService/userInfo");
    Map map = new HashMap();
    builder.queryParam("thirdId", openId);
    builder.queryParam("key", comKey);
    String result = RestServiceHelper.restPost(builder, JSONObject.toJSONString(map));
    if(!CheckUtils.isEmpty(result)){
      JSONObject obj = (JSONObject)JSONObject.parse(result);
      if(obj.getLong("code") >= 0){
        return obj;
      }
      return null;
    }
    return null;
  }
}
