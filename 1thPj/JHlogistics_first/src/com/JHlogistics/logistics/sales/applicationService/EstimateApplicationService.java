package com.JHlogistics.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.JHlogistics.logistics.sales.to.EstimateTO;
import com.JHlogistics.logistics.sales.to.EstimateDetailTO;

public interface EstimateApplicationService {
	
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate);

	public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo);
	
	// ApplicationService 안에서만 호출
	public String getNewEstimateNo(String estimateDate);

	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateTO);

	public HashMap<String, Object> batchEstimateDetailListProcess(ArrayList<EstimateDetailTO> estimateDetailTOList);	
	
}