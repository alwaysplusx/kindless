package com.harmony.kindless.jwt;

/**
 * @author wuxii@foxmail.com
 */
public interface JwtTokenService extends JwtTokenVerifier {

    /**
     * 验证token的签名
     *
     * @param token
     *            jwt token
     * @return true 签名正确, false签名错误
     */
    boolean verifySignature(JwtToken token);

    /**
     * 验证token的源信息
     *
     * @param token
     *            jwt token
     * @return true原与目标token一致, false与目标token不一直
     */
    boolean verifyOrigin(JwtToken token);

    /**
     * 将token与session id进行绑定
     * 
     * @param token
     *            json web token
     * @param sessionId
     *            session id
     */
    void bindSessionId(JwtToken token, String sessionId);

    /**
     * 通过json web token获取session id
     * 
     * @param token
     *            json web token
     * @return session id
     */
    String getSessionId(JwtToken token);

}
