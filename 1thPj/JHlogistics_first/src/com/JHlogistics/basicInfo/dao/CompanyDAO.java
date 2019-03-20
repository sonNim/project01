package com.JHlogistics.basicInfo.dao;

import java.util.ArrayList;

import com.JHlogistics.basicInfo.to.CompanyTO;

public interface CompanyDAO {
	
	public ArrayList<CompanyTO> selectCompanyList();
	
	public void insertCompany(CompanyTO TO);
	
	public void updateCompany(CompanyTO TO);

	public void deleteCompany(CompanyTO TO);
	
}
