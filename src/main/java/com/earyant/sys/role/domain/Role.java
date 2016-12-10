package com.earyant.sys.role.domain;

/**
 * 角色实体类
 *
 * @author zhiwei
 */
public class Role {
    private Integer id; //角色ID

    private String rolename; //角色名称

    private String rolecode; //角色代码

    private String roledesc; //角色描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRolecode() {
        return rolecode;
    }

    public void setRolecode(String rolecode) {
        this.rolecode = rolecode;
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }
}