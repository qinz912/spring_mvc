package com.mvc.bo;

public enum ResponseCode {
	SUCCESS(0,"SUCCESS"),
	ERROR(1,"ERROR"),
	ILLEGA_ARGUMENT(2,"ILLEGA_ARGUMENT");
	
	private final int code;
	private final String desc;
	
	ResponseCode(int code,String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public int getCode(){
		return code;
	}
	public String getDesc(){
		return desc;
	}
}

