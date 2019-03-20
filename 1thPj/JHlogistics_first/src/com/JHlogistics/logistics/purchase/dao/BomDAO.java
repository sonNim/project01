package com.JHlogistics.logistics.purchase.dao;

import java.util.ArrayList;

import com.JHlogistics.logistics.purchase.to.BomTO;
import com.JHlogistics.logistics.purchase.to.BomDeployTO;
import com.JHlogistics.logistics.purchase.to.BomInfoTO;

public interface BomDAO {

	public ArrayList<BomDeployTO> selectBomDeployList(String deployCondition, String itemCode);
	
	public ArrayList<BomInfoTO> selectBomInfoList(String parentItemCode);
	
	public ArrayList<BomInfoTO> selectAllItemWithBomRegisterAvailable();
	
	public void insertBom(BomTO TO);
	
	public void updateBom(BomTO TO);
	
	public void deleteBom(BomTO TO);
	
}
