package com.minimall.boilerplate.common.oss;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/28.
 * 创建一个OSS配置类
 */
public class OSSConfig {
  //连接区域地址
  private  String endpoint;
  //连接keyId
  private  String accessKeyId;
  //连接秘钥
  private  String accessKeySecret;
  //需要存储的bucketName
  private  String bucketName;
  //图片保存路径Zzz
  private  String picLocation;

  public OSSConfig() {
    try {
      this.endpoint = SystemConfig.getConfigResource("endpoint");
      this.bucketName = SystemConfig.getConfigResource("bucketName");
      this.picLocation = SystemConfig.getConfigResource("picLocation");
      this.accessKeyId = SystemConfig.getConfigResource("accessKeyId");
      this.accessKeySecret = SystemConfig.getConfigResource("accessKeySecret");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getEndpoint() {
    return endpoint;
  }
  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }
  public String getAccessKeyId() {
    return accessKeyId;
  }
  public void setAccessKeyId(String accessKeyId) {
    this.accessKeyId = accessKeyId;
  }
  public String getAccessKeySecret() {
    return accessKeySecret;
  }
  public void setAccessKeySecret(String accessKeySecret) {
    this.accessKeySecret = accessKeySecret;
  }
  public String getBucketName() {
    return bucketName;
  }
  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }
  public String getPicLocation() {
    return picLocation;
  }
  public void setPicLocation(String picLocation) {
    this.picLocation = picLocation;
  }
}
