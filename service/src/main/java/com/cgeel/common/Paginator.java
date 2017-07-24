package com.cgeel.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinwei.zhang
 * @version 2010-7-9 下午01:39:03
 */
public class Paginator implements Serializable {

	private int pageNum = 1;
	
	private int pageSize = 20;
	
	private int totalCount;
	
	@SuppressWarnings("rawtypes")
	private List results;

	public Paginator(){}
	
	public Paginator(int pageSize){
		this.pageSize = pageSize;
	}

    /**
     * 得到下一页的页数
     * @return
     */
    @JsonIgnore
	public int getNextPage() {
		if(isHasNext())
			return pageNum + 1;
		return pageNum;
	}

    /**
     * 当前第几页
     * @return
     */
	public int getPageNum() {
		return this.pageNum;
	}

    /**
     * 得到上一页的页数
     * @return
     */
    @JsonIgnore
	public int getPrePage() {
		if(isHasPre())
			return pageNum - 1;
		return pageNum;
	}

    /**
     * 返回分页后的数据
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List getResults() {
		return results;
	}

    /**
     * 总条数
     * @return
     */
	public int getTotalCount() {
		return totalCount;
	}

    /**
     * 一共多少页数
     * @return
     */
	public int getTotalPages() {
        if (totalCount < 0)
            return -1;

        int count = (totalCount / pageSize);
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否有下一页
     * @return
     */
	public boolean isHasNext() {
		if(pageNum <getTotalPages())
			return true;
		return false;
	}

    /**
     * 是否有上一页
     * @return
     */
    @JsonIgnore
	public boolean isHasPre() {
		if(pageNum >1)
			return true;
		return false;
	}

    /**
     * 设置当前转到第几页
     * @param pageNum
     */
	public void setPageNum(int pageNum) {
		if(pageNum <= 0){
			pageNum = 1;
		}
		this.pageNum = pageNum;
	}

    /**
     * 设置一页显示多少行
     * @param pageSize
     */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

    /**
     * 设置总条数
     * @param totalCount
     */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

    /**
     * 一页显示多少行
     * @return
     */
	public int getPageSize() {
		return pageSize;
	}

    /**
     * 设置分页数据
     * @param results
     */
	@SuppressWarnings("rawtypes")
	public void setResults(List results) {
		this.results = results;
	}

    /**
     * 从哪一行开始
     * @return
     */
    @JsonIgnore
	public int getFirstResult() {
        int pageIndex = getPageNum() - 1;
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		return pageIndex * getMaxResults();
	}

    /**
     * 共显示多少条
     * @return
     */
    @JsonIgnore
	public int getMaxResults() {
		return pageSize;
	}

}
