package com.minimall.boilerplate.common;

import com.minimall.boilerplate.exception.BadRequestException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.minimall.boilerplate.common.Constants.*;
import static com.minimall.boilerplate.common.Message.E124;
import static java.util.Objects.isNull;

/**
 * Title: 字符串帮助类.
 * <p>Description: 字符串相关处理.</p>

 */
public final class StringHelper {

  private StringHelper() {
  }

  public static boolean isEmpty(String s) {
    return "".equals(s);
  }

  public static boolean nonEmpty(String s) {
    return !isNullOrEmpty(s);
  }

  public static boolean isNullOrEmpty(String s) {
    return isNull(s) || "".equals(s);
  }

  public static String nullToEmpty(String s) {
    return isNull(s) ? "" : s;
  }

  public static String appendPostfix(String s, String postfix) {
    Assert.isTrue(nonEmpty(s), "参数不能为空");
    Assert.isTrue(nonEmpty(postfix), "参数不能为空");
    int dotIndex = s.lastIndexOf(".");
    if(dotIndex > 0) {
      String originPostfix = s.substring(dotIndex + 1);
      if(postfix.equals(originPostfix)) return s;
    }
    return s + "." + postfix;
  }

  public static String joinWith(String separator, String... keys) {
    if(isNull(keys) || keys.length == 0) return "";
    if(keys.length == 1) return nullToEmpty(keys[0]);
    final String finalSeparator = nullToEmpty(separator);
    return Stream.of(keys).reduce((key1, key2) ->
            String.format("%s%s%s", nullToEmpty(key1), finalSeparator, nullToEmpty(key2)))
            .orElseThrow(IllegalArgumentException::new);
  }

  public static String getSystemCode() {
    String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z" };
    List list = Arrays.asList(beforeShuffle);
    Collections.shuffle(list);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      sb.append(list.get(i));
    }
    String afterShuffle = sb.toString();
    String result = afterShuffle.substring(5, 9);
    return result;
  }

  public static String getExportFilePath(String exportFileName) throws IOException {
    Path excelOutPath = Paths.get(System.getProperty("jxservice.root"), "WEB-INF/classes", exportFileName);
    Resource exportFileResource = new ClassPathResource(exportFileName);
    if(!excelOutPath.toFile().createNewFile())
      throw new BadRequestException(E124);
    return exportFileResource.getFile().getAbsolutePath();
  }

  // a List转换为字符串并加入分隔符
  public static String listToString(List<Long> stringList, String separator){
    if (stringList==null || stringList.isEmpty()) {
      return null;
    }
    StringBuilder result=new StringBuilder();
    boolean flag=false;
    for (Object string : stringList) {
      if (flag) {
        result.append(separator);
      }else {
        flag=true;
      }
      result.append(string);
    }
    return result.toString();
  }


  /**
   * 把中文转成Unicode码
   * @param str
   * @return
   */
  public static String chinaToUnicode(String str){
    String result="";
    for (int i = 0; i < str.length(); i++){
      int chr1 = (char) str.charAt(i);
      if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
        result+="u" + Integer.toHexString(chr1);
      }else{
        result+=str.charAt(i);
      }
    }
    return result;
  }

  /**
   * 判断是否为中文字符
   * @param c
   * @return
   */
  public static  boolean isChinese(char c) {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
      return true;
    }
    return false;
  }
}
