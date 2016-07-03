package com.im.app.frontend.enums;

public enum ChatTypeEnum {
	
	  SINGLE(1, "Single chat"),
	  GROUP(2, "Group chat");
	  
	  private ChatTypeEnum(int type, String remark) {
          this.type = type;
          this.remark = remark;
      }
	  
	  private int type;
	  private String remark;
	
	  public int getType() {
		return type;
	  }

	  public void setType(int type) {
		this.type = type;
	  }

	  public String getRemark() {
		return remark;
	  }
	
	  public void setRemark(String remark) {
		this.remark = remark;
	  }

}
