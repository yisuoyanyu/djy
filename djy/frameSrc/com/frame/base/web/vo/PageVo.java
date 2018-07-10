package com.frame.base.web.vo;


public class PageVo {
	
	public int pageSize = 12;	// 每页显示的行数
	private int startRow;		// 数据库中查询的起始位置
	private long totalRow;		// 该页面对应类在数据库中的总数
	private int nowPage;		// 现在需要显示的页面数
	private int totalPage;		// 可以显示的总页面数

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public PageVo() {
	}

	public PageVo(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageVo(long totalRow, int pageSize) {
		if (totalRow == 0) {
			this.pageSize = pageSize;
			this.totalRow = totalRow;
			startRow = 0;
			nowPage = 1;
			totalPage = 1;
		} else {
			this.pageSize = pageSize;
			this.totalRow = totalRow;
			startRow = 0;
			nowPage = 1;
			totalPage = (int) ((totalRow % pageSize == 0) ? totalRow / pageSize : totalRow / pageSize + 1);
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		this.totalPage = (int)((totalRow % pageSize == 0) ? totalRow / pageSize : totalRow / pageSize + 1);
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public long getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public void toNextPage() {
		if (nowPage >= totalPage) {
			this.nowPage = totalPage;
		} else {
			nowPage++;
		}
	}

	public void toBackPage() {
		if (nowPage <= 1) {
			this.nowPage = 1;
		} else {
			nowPage--;
		}
	}

	public void toFirstPage() {
		this.nowPage = 1;
	}

	public void toLastPage() {
		this.nowPage = this.totalPage;
	}

	public void toPage(int topage) {
		this.nowPage = topage;
	}

	public void refreshRow() {
		this.startRow = (this.nowPage - 1) * this.pageSize;
		if (startRow < 0) {
			startRow = 0;
		}
	}
}
