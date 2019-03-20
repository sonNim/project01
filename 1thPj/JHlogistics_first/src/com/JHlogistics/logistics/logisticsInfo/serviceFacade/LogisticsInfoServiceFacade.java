package com.JHlogistics.logistics.logisticsInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import com.JHlogistics.logistics.logisticsInfo.to.ItemTO;
import com.JHlogistics.logistics.logisticsInfo.to.ItemInfoTO;

public interface LogisticsInfoServiceFacade {

	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray);
	
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList);
	
}
