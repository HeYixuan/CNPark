package org.springframe.util;

/**
 * WeChat Cache.
 *
 * cache common interface access token
 * cache js api ticket
 *
 * @author zhanghua on 4/20/16.
 */
public class WeChatCache {

    /**
     * 接口访问令牌
     */
    private String accessToken;

    /**
     * 接口访问令牌有效期, 毫秒
     */
    private long accessTokenExpires;

    /**
     * JS API 调用凭证
     */
    private String jsApiTicket;

    /**
     * JS API 调用凭证有效期, 毫秒
     */
    private long jsApiTicketExpires;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getAccessTokenExpires() {
        return accessTokenExpires;
    }

    public void setAccessTokenExpires(long accessTokenExpires) {
        this.accessTokenExpires = accessTokenExpires;
    }

    public String getJsApiTicket() {
        return jsApiTicket;
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }

    public long getJsApiTicketExpires() {
        return jsApiTicketExpires;
    }

    public void setJsApiTicketExpires(long jsApiTicketExpires) {
        this.jsApiTicketExpires = jsApiTicketExpires;
    }
}
