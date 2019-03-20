package com.JHlogistics.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.JHlogistics.basicInfo.to.CompanyTO;

public interface CompanyApplicationService {

	public ArrayList<CompanyTO> getCompanyList();
	
	public String getNewCompanyCode();
	
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList);
	
	
	
}
