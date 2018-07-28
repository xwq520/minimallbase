package com.minimall.boilerplate.common.oss;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/7/28.
 * 创建一个读取配置文件的工具类
 */
public class SystemConfig {
  private static final String CONFIG_PROPERTIES="application-com.properties";

  public static String getConfigResource(String key) throws IOException {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    Properties properties = new Properties();
    InputStream in = loader.getResourceAsStream(CONFIG_PROPERTIES);
    properties.load(in);
    String value = properties.getProperty(key);
    // 编码转换，从ISO-8859-1转向指定编码
    value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
    in.close();
    return value;
  }
}
