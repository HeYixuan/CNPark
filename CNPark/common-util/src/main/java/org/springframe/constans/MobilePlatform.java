package org.springframe.constans;

/**
 * 
 * @fileName MobilePlatform.java
 * @package com.jd.ofit.app.common.constant
 * @description 移动端平台枚举
 * @author HeYixuan
 * @date 2017年8月2日 下午3:57:58
 * @version V1.0
 */
public enum MobilePlatform {
    
    ANDROID(11, "android"),
    IOS(21, "iphone");

    private MobilePlatform(){}

    private MobilePlatform(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    private Integer value;
    private String description;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 
     * </p>根据平台序号获取平台枚举</p>
     * @param value 平台序号
     * @return
     * @author yujianliang@igetwell.cn
     * @date 2017年8月2日
     */
    public static MobilePlatform getMobilePlatform(int value) {
        for (MobilePlatform mp : MobilePlatform.values()) {
            if (mp.getValue() == value) {
                return mp;
            }
        }
        return null;
    }
    
    /**
     * 
     * </p>根据平台序号获取平台描述</p>
     * @param value 平台序号
     * @return
     * @author yujianliang@igetwell.cn
     * @date 2017年8月2日
     */
    public static String getDescription(int value) {
        for (MobilePlatform mp : MobilePlatform.values()) {
            if (mp.getValue() == value) {
                return mp.getDescription();
            }
        }
        return "-";
    }

    public static boolean contains(String value) {
        try {
            return contains(Integer.parseInt(value));
        } catch (Exception e){
            return false;
        }
    }
    public static boolean contains(Integer value) {
        if(value == null){
            return false;
        }
        for (MobilePlatform mp : MobilePlatform.values()) {
            if (mp.getValue().intValue() == value.intValue()) {
                return true;
            }
        }
        return false;
    }

}
