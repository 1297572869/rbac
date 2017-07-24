package com.cgeel.common.datatable;

import java.util.List;

/**
 * Created by ZXW on 2014/8/20.
 */
public class DataTablePaginator {
    private int iTotalRecords;
    private int iTotalDisplayRecords;
    private String sEcho;
    private List<? extends Object> aaData;

    private DataTablePaginator(){}

    public DataTablePaginator(DataTableParam param){
        this.sEcho = param.getsEcho();
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public List<? extends Object> getAaData() {
        return aaData;
    }

    public void setAaData(List<? extends Object> aaData) {
        this.aaData = aaData;
    }

}
