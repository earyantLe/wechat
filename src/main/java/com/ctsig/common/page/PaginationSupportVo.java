package com.ctsig.common.page;

import java.util.List;
import java.io.Serializable;
/**
 * 分页查询bean
 * 
 * @author duming
 * @date 2013-10-9
 */
public class PaginationSupportVo implements Serializable{

	private static final long serialVersionUID = 4729935873120913308L;

	private int DEF_PAGE_VIEW_SIZE = 10;

	/** 当前页 */
	private int curPage;
	
	/** 当前页显示记录条数 */
	private int pageSize;
	
	/**总页数*/
	private int pageCount;
	
	/** 取得查询总记录数 */
	private int count;
	
	/** 结果集*/
	private List<?> items;

	public int getDEF_PAGE_VIEW_SIZE() {
		return DEF_PAGE_VIEW_SIZE;
	}

	public void setDEF_PAGE_VIEW_SIZE(int dEF_PAGE_VIEW_SIZE) {
		DEF_PAGE_VIEW_SIZE = dEF_PAGE_VIEW_SIZE;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
