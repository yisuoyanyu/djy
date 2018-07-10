package com.frame.base.web.vo;

import com.frame.base.utils.StringUtil;
import com.frame.base.utils.NumberUtil;

public class PagingBean {

	public Integer toPage;           // 当前页码
	public Integer start;            // 当前页开始的索引号

	private Integer pageSize;        // 每页显示几条记录

	private Boolean isReturnTotal;   // 是否要返回总记录数
	private Long totalItems;         // 总记录数

	/**
	 * 跳转到第几页
	 * 
	 * @return
	 */
	public Integer getToPage() {
		return toPage;
	}

	/**
	 * 每页显示几条记录
	 * 
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 是否返回总记录数
	 * 
	 * @return
	 */
	public Boolean getIsReturnTotal() {
		return isReturnTotal;
	}

	public void setIsReturnTotal(Boolean isReturnTotal) {
		this.isReturnTotal = isReturnTotal;
	}

	/**
	 * 总记录数
	 * 
	 * @return
	 */
	public Long getTotalItems() {
		return totalItems;
	}

	/**
	 * 总记录数
	 * 
	 * @param totalItems
	 */
	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public PagingBean(PaginationVo pv) {
		this.pageSize = 12;
		if (StringUtil.isEmpty(pv.getToPage())) {
			this.toPage = 1;
		} else {
			this.toPage = NumberUtil.parseInt(pv.getToPage());
		}
		this.start = (this.toPage - 1) * this.pageSize;
		this.isReturnTotal = true;
	}

	public PagingBean() {
		this.toPage = 1;
		this.start = 0;
		this.pageSize = 10;
		this.isReturnTotal = true;
	}

	public PagingBean(Integer toPage) {
		this.toPage = toPage;
		this.pageSize = 10;
		if (toPage == null) {
			this.start = 0;
		} else {
			this.start = (toPage - 1) * 10;
		}
		this.isReturnTotal = true;
	}
	
	public PagingBean(Integer start, Integer pageSize) {
		this.pageSize = pageSize;
		this.start = start;
		this.toPage = this.start / this.pageSize + 1;
		this.isReturnTotal = true;
	}
	
	public PagingBean(Boolean isMall,Integer start, Integer pageSize) {
		if (isMall) {
			this.pageSize = pageSize;
			this.start = start;
			this.toPage = this.start;
			this.isReturnTotal = true;
		}else {
			this.pageSize = pageSize;
			this.start = start;
			this.toPage = this.start / this.pageSize + 1;
			this.isReturnTotal = true;
		}
		
	}
	
	public PagingBean(Integer start, Integer pageSize, Boolean isReturnTotal) {
		this.start = start;
		this.pageSize = pageSize;
		this.toPage = this.start / this.pageSize + 1;
		this.isReturnTotal = isReturnTotal;
	}
	
	
}