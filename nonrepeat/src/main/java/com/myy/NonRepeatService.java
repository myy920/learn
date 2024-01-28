package com.myy;

/**
 * 唯一令牌桶
 */
public interface NonRepeatService {

    String createToken();

    boolean deleteToken(String token);


}
