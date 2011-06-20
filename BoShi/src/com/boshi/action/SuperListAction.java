package com.boshi.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.boshi.db.datacenter.DataCenterInterface;
import com.opensymphony.xwork2.ActionSupport;

public class SuperListAction extends ActionSupport implements ServletRequestAware {

	private static final long		serialVersionUID	= 1L;
	protected HttpServletRequest	servletRequest		= null;
	private int[]					pagesList			= null; // 页列表
	private int						howManyOnePage		= 0;	// 一页显示多少个结果
	private long					resulthAmount		= -1;	// 总共有多少个结果
	private int						pagesAmount			= 0;	// 总共分了多少个页
	private int						whichPage			= 1;	// 当前是第几页
	private List<Object>			resultList			= null; // 页面内显示的结果列表
	private DataCenterInterface		dataCenterInterface	= null;

	public void superExecute(String param[]) {
		this.resulthAmount = this.dataCenterInterface.amount(param);
		this.pagesAmount = this.howManyPages(this.howManyOnePage, this.resulthAmount);
		this.whichPage = 1;
		this.resultList = this.dataCenterInterface.list(this.startNo(this.whichPage, this.howManyOnePage), this.howManyOnePage, param);
	}

	public void superPagination(String param[]) {
		if (resulthAmount == -1) {
			this.resulthAmount = this.dataCenterInterface.amount(param);
			this.pagesAmount = this.howManyPages(this.howManyOnePage, this.resulthAmount);
		}
		whichPage = whichPageNo(whichPage);
		this.resultList = this.dataCenterInterface.list(this.startNo(this.whichPage, this.howManyOnePage), this.howManyOnePage, param);
	}

	public int whichPageNo(int whichPage) {
		String param = (String) servletRequest.getParameter("whichPage");
		if (param != null) {
			try {
				whichPage = Integer.parseInt(param);
				if (whichPage < 1)
					whichPage = 1;
				else if (whichPage > pagesAmount && pagesAmount == 0)
					whichPage = 1;
				else if (whichPage > pagesAmount && pagesAmount != 0)
					whichPage = pagesAmount;
			} catch (Exception ex) {
			}
		}
		return whichPage;
	}

	public int howManyPages(int howManyOnePage, long resulthAmount) {
		int pagesAmount = (int) (resulthAmount / howManyOnePage);
		if (resulthAmount % howManyOnePage != 0)
			pagesAmount++;
		pagesList = new int[pagesAmount];
		for (int i = 0; i < pagesList.length; i++)
			pagesList[i] = i + 1;
		return pagesAmount;
	}

	public int startNo(int whichPage, int howManyOnePage) {
		return (whichPage - 1) * howManyOnePage;
	}

	public int getHowManyOnePage() {
		return howManyOnePage;
	}

	public void setHowManyOnePage(int howManyOnePage) {
		this.howManyOnePage = howManyOnePage;
	}

	public List<Object> getResultList() {
		return resultList;
	}

	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}

	public int[] getPagesList() {
		return pagesList;
	}

	public void setPagesList(int[] pagesList) {
		this.pagesList = pagesList;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public int getPagesAmount() {
		return pagesAmount;
	}

	public void setPagesAmount(int pagesAmount) {
		this.pagesAmount = pagesAmount;
	}

	public long getResulthAmount() {
		return resulthAmount;
	}

	public void setResulthAmount(long resulthAmount) {
		this.resulthAmount = resulthAmount;
	}

	public int getWhichPage() {
		return whichPage;
	}

	public void setWhichPage(int whichPage) {
		this.whichPage = whichPage;
	}

	public DataCenterInterface getDataCenterInterface() {
		return dataCenterInterface;
	}

	public void setDataCenterInterface(DataCenterInterface dataCenterInterface) {
		this.dataCenterInterface = dataCenterInterface;
	}
}
