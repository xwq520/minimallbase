package com.minimall.boilerplate.common;

/**
 * Title: 共通check处理
 * <p>Description: Cookies</p>
 *
 * @author tony
 */
public class CheckUtils {
  public static boolean isEmpty(Object target){
    if(target == null || target.toString().length() == 0 || "".equals(target)
      || "null".equals(String.valueOf(target).toLowerCase())
      || "undefined".equals(String.valueOf(target).toLowerCase())){
      return true;
    }
    return false;
  }
}
