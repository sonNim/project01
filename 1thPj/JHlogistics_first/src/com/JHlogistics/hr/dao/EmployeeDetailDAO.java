package com.JHlogistics.hr.dao;

import java.util.ArrayList;

import com.JHlogistics.hr.to.EmployeeDetailTO;

public interface EmployeeDetailDAO {

	public ArrayList<EmployeeDetailTO> selectEmployeeDetailList(String companyCode, String empCode);
	
	public ArrayList<EmployeeDetailTO> selectUserIdList(String companyCode);
	
	public void insertEmployeeDetail(EmployeeDetailTO TO);
	

}
