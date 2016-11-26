package com.ctsig.common.page;

import java.util.List;
import java.io.Serializable;
/**
 * 分页查询bean
 * 
 * @author duming
 * @date 2013-10-9
 */
public class PaginationSupport implements Serializable{

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
	

	/**
	 * 动作类型 
	 * <li>0：无动作</li> 
	 * <li>1：首页</li> 
	 * <li>2：前一页</li> 
	 * <li>3：后一页</li> 
	 * <li>4：末页</li>
	 * <li>5：跳转页</li> 
	 * <li>6：重新设定每页记录数</li>
	 */
	private int actionType;
	

	public PaginationSupport() {
		this.curPage = 0;
		this.pageSize = DEF_PAGE_VIEW_SIZE;
	}

	public PaginationSupport(int curPage, int pageSize) {
		this.curPage = (curPage <= 0) ? 1 : curPage;
		this.pageSize = (pageSize <= 0) ? DEF_PAGE_VIEW_SIZE : pageSize;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public int getCurPage() {
		return (curPage <= 0) ? 1 : curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage =(curPage<=0)?1:curPage;
	}

	public int getPageSize() {
		return (pageSize <= 0) ? DEF_PAGE_VIEW_SIZE : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = (count < 0) ? 0 : count;
		if (this.count == 0) {
			this.curPage = 0;
			return;
		}
	}

	public void setPageCount(){
		pageCount = (count + getPageSize() - 1) / getPageSize();
	}

	public int getPageCount() {
		return pageCount;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

	public int getStartNo() {
		return ((getCurPage() - 1) * getPageSize() + 1);
	}


	public int getEndNo() {
		return Math.min(getCurPage() * getPageSize(), count);
	}


	public int getPrePage() {
		return Math.max(getCurPage() - 1, 1);
	}


	public int getNextPage() {
		return Math.min(getCurPage() + 1, getPageCount());
	}

}
