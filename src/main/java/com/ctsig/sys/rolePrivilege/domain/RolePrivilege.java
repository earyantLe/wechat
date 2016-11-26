package com.ctsig.sys.rolePrivilege.domain;

public class RolePrivilege {
    private Integer id;

    private String pid;

    private String privilegeid;

    private Integer roleid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrivilegeid() {
        return privilegeid;
    }

    public void setPrivilegeid(String privilegeid) {
        this.privilegeid = privilegeid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }
}