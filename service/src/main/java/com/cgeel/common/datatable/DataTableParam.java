package com.cgeel.common.datatable;

/**
 * Created by ZXW on 2014/8/20.
 */
public class DataTableParam {
    private int iDisplayStart;
    private int iDisplayLength;
    private int iColumns;
    private String sEcho;

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public int getiColumns() {
        return iColumns;
    }

    public void setiColumns(int iColumns) {
        this.iColumns = iColumns;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

	@Override
	public String toString() {
		return "DataTableParam [iDisplayStart=" + iDisplayStart
				+ ", iDisplayLength=" + iDisplayLength + ", iColumns="
				+ iColumns + ", sEcho=" + sEcho + "]";
	}
    
}
