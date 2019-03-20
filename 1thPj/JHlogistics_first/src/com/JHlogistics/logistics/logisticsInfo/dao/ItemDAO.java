package com.JHlogistics.logistics.logisticsInfo.dao;

import java.util.ArrayList;

import com.JHlogistics.logistics.logisticsInfo.to.ItemTO;
import com.JHlogistics.logistics.logisticsInfo.to.ItemInfoTO;

public interface ItemDAO {

	public ArrayList<ItemInfoTO> selectAllItemList();
	
	public ArrayList<ItemInfoTO> selectItemList(String searchCondition, String paramArray[]);
	
	public void insertItem(ItemTO TO);
	
	public void updateItem(ItemTO TO);
	
	public void deleteItem(ItemTO TO);
	
}
