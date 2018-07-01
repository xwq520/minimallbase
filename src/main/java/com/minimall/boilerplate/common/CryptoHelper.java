package com.minimall.boilerplate.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Title: 加密帮助类.
 * <p>Description: 用于加密和解密.</p>

 */
public final class CryptoHelper {

  private CryptoHelper() {
  }

  public static String encode(final String rawPassword) {
    return StringHelper.isNullOrEmpty(rawPassword)
        ? rawPassword
        : new BCryptPasswordEncoder().encode(rawPassword);
  }

  public static boolean verify(final String rawPassword, final String encodedPassword) {
    return !(StringHelper.isNullOrEmpty(rawPassword) ||
        StringHelper.isNullOrEmpty(encodedPassword)) &&
        new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
  }
}
