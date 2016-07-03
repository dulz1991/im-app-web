package com.im.app.base.util;

public class PageUtil {
	
	private Integer pageNum = 1;
	private Integer pageSize = 10;
	private Integer pageCount;
	private Integer currentPage;
	private boolean hasNextPage;
	
	public PageUtil(Integer pageNum, Integer pageSize) {
		this.currentPage = pageNum;
		if (pageNum >= 1) {
			this.pageNum = (pageNum - 1) * pageSize;	
		} else {
			this.pageNum = pageNum * pageSize;
		}
		this.pageSize = pageSize;
	}
	public PageUtil(Integer pageNum, Integer pageSize, Integer totalCount) {
		this.currentPage = pageNum;
		if (pageNum >= 1) {
			this.pageNum = (pageNum - 1) * pageSize;	
		} else {
			this.pageNum = pageNum * pageSize;
		}
		this.pageSize = pageSize;
		if (totalCount % this.pageSize == 0) {
			this.pageCount = totalCount / this.pageSize;
		} else {
			this.pageCount = totalCount / this.pageSize + 1;
		}
		if (pageNum == pageCount || totalCount==0) {
			 this.hasNextPage = false;
		} else {
			this.hasNextPage = true;
		}
	}
	
	public PageUtil() {
	}

	/**
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}
	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageCount
	 */
	public Integer getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount the pageCount to set
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return the hasNextPage
	 */
	public boolean isHasNextPage() {
		return hasNextPage;
	}

	/**
	 * @param hasNextPage the hasNextPage to set
	 */
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

}
