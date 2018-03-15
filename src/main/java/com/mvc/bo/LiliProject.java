package com.mvc.bo;

public class LiliProject {

	private String kinds;
	private String touchTime;
	private String modelTime;
	private String amount;
	private String handler;
	private String way;
	private String num;
	public synchronized String getKinds() {
		return kinds;
	}
	public synchronized void setKinds(String kinds) {
		this.kinds = kinds;
	}
	public synchronized String getHandler() {
		return handler;
	}
	public synchronized void setHandler(String handler) {
		this.handler = handler;
	}
	public synchronized String getWay() {
		return way;
	}
	public synchronized void setWay(String way) {
		this.way = way;
	}
	
	
	public synchronized String getNum() {
		return num;
	}
	public synchronized void setNum(String num) {
		this.num = num;
	}
	
	
	public synchronized String getTouchTime() {
		return touchTime;
	}
	public synchronized void setTouchTime(String touchTime) {
		this.touchTime = touchTime;
	}
	public synchronized String getModelTime() {
		return modelTime;
	}
	public synchronized void setModelTime(String modelTime) {
		this.modelTime = modelTime;
	}
	public synchronized String getAmount() {
		return amount;
	}
	public synchronized void setAmount(String amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "LiliProject [kinds=" + kinds + ", touchTime=" + touchTime + ", modelTime=" + modelTime + ", amount="
				+ amount + ", handler=" + handler + ", way=" + way + ", num=" + num + "]";
	}
	
	
}
