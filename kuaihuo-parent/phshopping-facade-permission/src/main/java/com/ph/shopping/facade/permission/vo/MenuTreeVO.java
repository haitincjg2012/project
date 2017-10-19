package com.ph.shopping.facade.permission.vo;

import java.io.Serializable;

public class MenuTreeVO implements Serializable {

	private static final long serialVersionUID = -2240177336781489378L;

	private Long id;
	private Long pId;
	private String name;
    private Long btnId;
    private boolean checked = false;
	private boolean open = true;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}

    public Long getBtnId() {
        return btnId;
    }

    public void setBtnId(Long btnId) {
        this.btnId = btnId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
