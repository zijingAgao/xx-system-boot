package com.agao.utils;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class StringRandom {

  public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String lower = upper.toLowerCase(Locale.ROOT);
  public static final String digits = "0123456789";
  public static final String character = "~`!@#$%^&*()_-+=|\\/[]{};':\",.<>?";
  public static final String alphanum = upper + lower + digits + character;
  private final char[] symbols;
  private final char[] buf;

  public StringRandom(int maxLength, String symbols) {
    if (maxLength < 1) {
      throw new IllegalArgumentException("maxLength[" + maxLength + "] must >= 1");
    }
    if (symbols.length() < 2) {
      throw new IllegalArgumentException("symbols[" + symbols + "] length must >=2 ");
    }
    this.symbols = symbols.toCharArray();
    this.buf = new char[maxLength];
  }

  /** Create an alphanumeric string generator. */
  public StringRandom(int maxLength) {
    this(maxLength, alphanum);
  }

  public static String randomAlphanumeric(int length) {
    return new StringRandom(length, alphanum).nextString(length);
  }

  public static String randomNumeric(int length) {
    return new StringRandom(length, digits).nextString(length);
  }

  public String nextString(int len) {
    if (len > buf.length) {
      throw new IllegalArgumentException("len[" + len + "] > maxLength[" + buf.length + "]");
    }
    for (int idx = 0; idx < len; ++idx) {
      buf[idx] = symbols[ThreadLocalRandom.current().nextInt(symbols.length)];
    }
    return new String(buf, 0, len);
  }
}
