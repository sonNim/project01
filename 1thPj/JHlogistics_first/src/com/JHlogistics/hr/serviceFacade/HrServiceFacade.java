package com.JHlogistics.hr.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.JHlogistics.hr.to.EmpInfoTO;
import com.JHlogistics.hr.to.EmployeeBasicTO;
import com.JHlogistics.hr.to.EmployeeDetailTO;
import com.JHlogistics.hr.to.EmployeeSecretTO;

public interface HrServiceFacade {

	public ArrayList<EmpInfoTO> getAllEmpList(String searchCondition, String[] paramArray);

	public EmpInfoTO getEmpInfo(String companyCode, String empCode);

	public String getNewEmpCode(String companyCode);
	
	public Boolean checkEmpCodeDuplication(String companyCode, String newEmpCode);
	
	public Boolean checkUserIdDuplication(String companyCode, String newUserId);
	
	public HashMap<String, Object> batchEmpBasicListProcess(ArrayList<EmployeeBasicTO> empBasicList);

	public HashMap<String, Object> batchEmpDetailListProcess(ArrayList<EmployeeDetailTO> empDetailList);
	
	public HashMap<String, Object> batchEmpSecretListProcess(ArrayList<EmployeeSecretTO> empSecretList);
	
	
}
