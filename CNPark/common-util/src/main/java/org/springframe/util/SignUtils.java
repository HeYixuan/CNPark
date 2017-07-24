package org.springframe.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframe.constans.SignType;
import org.springframe.constans.WXPayConstants;


import java.util.*;

/**
 * 签名相关工具类
 * @author: HeYixuan
 * @create: 2017-05-27 18:16
 */
public class SignUtils {


    /**
     * 微信公众号支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3)
     *
     * @param xmlBean Bean需要标记有XML注解
     * @param signKey 签名Key
     * @return 签名字符串
     */
    public static String createSign(Object xmlBean, String signKey) {
        return createSign(BeanUtils.xmlBean2Map(xmlBean), signKey);
    }

    /**
     * 微信公众号支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3)
     *
     * @param params  参数信息
     * @param signKey 签名Key
     * @return 签名字符串
     */
    public static String createSign(final Map<String, String> params, String signKey) {

        SortedMap<String, String> sortedMap = new TreeMap<>(params);

        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key + "=" + value + "&");
            }
        }

        toSign.append("key=" + signKey);
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String createSign(final Map<String, String> data, String key, SignType signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(WXPayConstants.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return DigestUtils.md5Hex(sb.toString()).toUpperCase();
        }
        else if (SignType.HMACSHA256.equals(signType)) {
            return DigestUtils.sha256Hex(sb.toString()).toUpperCase();
            //return HMACSHA256(sb.toString(), key);
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }

    /**
     * 校验签名是否正确
     *
     * @param xmlBean Bean需要标记有XML注解
     * @param signKey 校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     * @see #checkSign(Map, String)
     */
    public static boolean checkSign(Object xmlBean, String signKey) {
        return checkSign(BeanUtils.xmlBean2Map(xmlBean), signKey);
    }

    /**
     * 校验签名是否正确
     *
     * @param params  需要校验的参数Map
     * @param signKey 校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     * @see #checkSign(Map, String)
     */
    public static boolean checkSign(Map<String, String> params, String signKey) {
        String sign = createSign(params, signKey);
        return sign.equals(params.get("sign"));
    }
}
