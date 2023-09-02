package com.randycasburn.thing.services;
public class DatabaseRequestResult {

	private int rowCount = -1;
	public DatabaseRequestResult(){}
	public DatabaseRequestResult(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getRowCount(){
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

}
