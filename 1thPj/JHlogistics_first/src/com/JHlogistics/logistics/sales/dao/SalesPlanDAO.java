package com.JHlogistics.logistics.sales.dao;

import java.util.ArrayList;

import com.JHlogistics.logistics.production.to.SalesPlanInMpsAvailableTO;
import com.JHlogistics.logistics.sales.to.SalesPlanTO;

public interface SalesPlanDAO {
	public ArrayList<SalesPlanTO> selectSalesPlanList(String dateSearchCondition, String startDate, String endDate);
			
	public int selectSalesPlanCount(String salesPlanDate);
	
	public ArrayList<SalesPlanInMpsAvailableTO>
		selectSalesPlanListInMpsAvailable(String searchCondition, String startDate, String endDate);
	
	public void insertSalesPlan(SalesPlanTO TO);

	public void updateSalesPlan(SalesPlanTO TO);
	
	public void changeMpsStatusOfSalesPlan(String salesPlanNo, String mpsStatus);	
	
	public void deleteSalesPlan(SalesPlanTO TO);
	
}