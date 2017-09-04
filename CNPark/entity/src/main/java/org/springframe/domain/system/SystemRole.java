package org.springframe.domain.system;

import org.springframe.core.SerializableModel;
import org.springframework.data.annotation.Id;

public class SystemRole extends SerializableModel {
    /**
     * 编号
     */
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 角色名称
     */
    private ROLE name;

    /**
     * 获取编号
     *
     * @return id - 编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取编码
     *
     * @return code - 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     *
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public ROLE getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(ROLE name) {
        this.name = name == null ? null : name;
    }

    public enum ROLE {
        ROLE_ADMIN,
        ROLE_USER,
        ROLE_DBA,
        ROLE_ANON;
    }
}