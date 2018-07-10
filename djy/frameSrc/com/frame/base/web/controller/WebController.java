package com.frame.base.web.controller;

import com.frame.base.web.vo.PageVo;
import com.frame.base.web.vo.PaginationVo;

public class WebController {
	
	
	protected PageVo refreshPage(PaginationVo pagination, Long totalRow) {
		
		String nowPage = pagination.getNowPage();
		String pageMethod = pagination.getPageMethod();
		String toPage = pagination.getToPage();
		
		if (toPage != null && toPage.equals(""))
			toPage = null;
		
		if (nowPage != null && nowPage.equals(""))
			nowPage = null;
		
		if (pageMethod != null && pageMethod.equals(""))
			pageMethod = null;
		
		PageVo page = new PageVo();
		page = new PageVo(totalRow, page.pageSize);
		
		if (pageMethod != null || toPage != null) {
			page.setNowPage(Integer.parseInt(nowPage));
			
			if (pageMethod != null && !pageMethod.equals("")) {
				if (pageMethod.equals("first"))
					page.toFirstPage();
				if (pageMethod.equals("back"))
					page.toBackPage();
				if (pageMethod.equals("next"))
					page.toNextPage();
				if (pageMethod.equals("last"))
					page.toLastPage();
				if (pageMethod.equals("all")) {
					page.setPageSize((int)page.getTotalRow());
					page.setTotalPage(1);
					page.setStartRow(1);
				}
			} else if (toPage != null && !toPage.equals("")) {
				int temp = Integer.parseInt(toPage);
				if (temp > 0) {
					if (temp <= page.getTotalPage())
						page.toPage(temp);
					else
						page.toPage(page.getTotalPage());
				} else {
					page.toPage(0);
				}
			}
		}
		page.refreshRow();
		return page;
	}

	
}
