package com.earyant.sys.privilege.domain;



public class Privilege {
    private String id;

    private String privilegename;

    private String page;

    private String pId;
    private String privilegecode;

 
	

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	private String ismenu;//1.是父菜单 ，0是子节点菜单

    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrivilegename() {
        return privilegename;
    }

    public void setPrivilegename(String privilegename) {
        this.privilegename = privilegename;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }



	public String getIsmenu() {
        return ismenu;
    }

    public void setIsmenu(String ismenu) {
        this.ismenu = ismenu;
    }

	public String getPrivilegecode() {
		return privilegecode;
	}

	public void setPrivilegecode(String privilegecode) {
		this.privilegecode = privilegecode;
	}
    
}