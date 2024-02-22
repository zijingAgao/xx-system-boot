package com.agao.security.cache;

import lombok.Data;

@Data
public class LoginExpiredCacheValue {

  private Long timestamp;
  private Integer code;

  public static LoginExpiredCacheValue of(long timestamp, int code) {
    LoginExpiredCacheValue cacheValue = new LoginExpiredCacheValue();
    cacheValue.setTimestamp(timestamp);
    cacheValue.setCode(code);
    return cacheValue;
  }
}
