package com.minimall.boilerplate.common;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by yons on 16/10/7.
 */
public class RestServiceHelper {

  public final static String CONTENT_TYPE_JSON =   "application/json;charset=UTF-8";

  public final static String CONTENT_TYPE_XML =   "text/xml;charset=UTF-8";

  /**
   * http的POST方式发送JSON
   *
   * @param url 请求的URL
   * @param  jsonBean 请求参数
   * @return String 返回请求结果
   * @throws Exception 异常
   *
   */
  public static String restPost(final String url, final Object jsonBean){
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType(CONTENT_TYPE_JSON);
    headers.setContentType(type);
    headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

    String TempMsg = JSON.toJSONString(jsonBean);
    HttpEntity<?> entity = new HttpEntity<>(TempMsg, headers);
    String res = restTemplate.postForObject(
      builder.build().toUriString(),
      entity,
      String.class);

    return res;
  }

  /**
   * http的POST方式发送JSON
   *
   * @param builder 请求的URL
   * @param  content 请求参数
   * @return String 返回请求结果
   * @throws Exception 异常
   *
   */
  public static String restPost(UriComponentsBuilder builder, String content){
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType(CONTENT_TYPE_JSON);
    headers.setContentType(type);
    headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

    HttpEntity<?> entity = new HttpEntity<>(content, headers);
    String res = restTemplate.postForObject(
      builder.build().toUriString(),
      entity,
      String.class);

    return res;
  }

  /**
   * http的POST方式发送其他数据
   *
   * @param url 请求的URL
   * @param  content 请求参数
   * @param  mediaType 媒体类型
   * @return String 返回请求结果
   * @throws Exception 异常
   *
   */
  public static String restPost(final String url, final String content, final String mediaType){
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    MediaType type = MediaType.parseMediaType(mediaType);
    headers.setContentType(type);
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

    HttpEntity<?> entity = new HttpEntity<>(content, headers);
    String res = restTemplate.postForObject(
      builder.build().toUriString(),
      entity,
      String.class);

    return res;
  }

  /**
   * http的Get
   *
   * @param builder 请求的URL builder
   * @return String 返回请求结果
   * @throws Exception 异常
   *
   */
  public static String restGet(UriComponentsBuilder builder){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

    HttpEntity<?> entity = new HttpEntity<>(headers);
    HttpEntity<String> response = restTemplate.exchange(
      builder.build().toString(),
      HttpMethod.GET,
      entity,
      String.class);

    String body = response.getBody();
    return body;
  }

  /**
   * http的Get
   *
   * @param url 请求的URL builder
   * @param  accessToken 请求参数
   * @return String 返回请求结果
   * @throws Exception 异常
   *
   */
  public static String restGet(String url,String accessToken){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
    headers.set("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
    headers.set("Authorization", "Bearer "+accessToken);
    headers.set("X-PAGE-ID", "0");

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    HttpEntity<?> entity = new HttpEntity<>(headers);
    HttpEntity<String> response = restTemplate.exchange(
      builder.build().toString(),
      HttpMethod.GET,
      entity,
      String.class);

    String body = response.getBody();
    return body;
  }

  /**
   * http的POST方式发送JSON
   *
   * @param url 请求的URL
   * @param  jsonBean 请求参数
   * @param  accessToken 请求参数
   * @return String 返回请求结果
   * @throws Exception 异常
   *
   */
  public static String restPost(final String url, final Object jsonBean,String accessToken){
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();

    MediaType type = MediaType.parseMediaType(CONTENT_TYPE_JSON);
    headers.setContentType(type);
    headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
    headers.set("Authorization", "Bearer "+accessToken);
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

    String TempMsg = JSON.toJSONString(jsonBean);
    HttpEntity<?> entity = new HttpEntity<>(TempMsg, headers);
    String res = restTemplate.postForObject(
            builder.build().toUriString(),
            entity,
            String.class);

    return res;
  }


  /**
   * http的Get
   *
   * @param url 请求的URL builder
   * @return String 返回请求结果
   * @throws Exception 异常
   *
   */
  public static String restGet(String url){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    HttpEntity<?> entity = new HttpEntity<>(headers);
    HttpEntity<String> response = restTemplate.exchange(
            builder.build().toString(),
            HttpMethod.GET,
            entity,
            String.class);

    String body = response.getBody();
    return body;
  }
}
