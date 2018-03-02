package com.harmony.kindless.shiro;

/**
 * @author wuxii@foxmail.com
 */
public interface TokenVerifier {

    boolean verify(JwtToken token);

}
