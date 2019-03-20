package com.JHlogistics.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import com.JHlogistics.logistics.purchase.to.BomTO;
import com.JHlogistics.logistics.purchase.to.BomDeployTO;
import com.JHlogistics.logistics.purchase.to.BomInfoTO;

public interface BomApplicationService {

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode);
	
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable();
	
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList);


}
