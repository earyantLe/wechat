package com.ctsig.sys.privilege.controller.vo;

import com.ctsig.sys.privilege.domain.Privilege;

public class PrivilegeVo extends Privilege {
	private boolean open;
	private boolean checked;

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

}
