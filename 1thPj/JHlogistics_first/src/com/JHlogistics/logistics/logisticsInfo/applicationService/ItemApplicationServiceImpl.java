package com.JHlogistics.logistics.logisticsInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.base.dao.CodeDetailDAO;
import com.JHlogistics.base.dao.CodeDetailDAOImpl;
import com.JHlogistics.base.to.CodeDetailTO;
import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.logistics.logisticsInfo.dao.ItemDAO;
import com.JHlogistics.logistics.logisticsInfo.dao.ItemDAOImpl;
import com.JHlogistics.logistics.logisticsInfo.to.ItemTO;
import com.JHlogistics.logistics.logisticsInfo.to.ItemInfoTO;
import com.JHlogistics.logistics.purchase.dao.BomDAO;
import com.JHlogistics.logistics.purchase.dao.BomDAOImpl;
import com.JHlogistics.logistics.purchase.to.BomTO;

public class ItemApplicationServiceImpl implements ItemApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(ItemApplicationServiceImpl.class);

	// 싱글톤
	private static ItemApplicationService instance = new ItemApplicationServiceImpl();

	private ItemApplicationServiceImpl() {
	}

	public static ItemApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ ItemApplicationService 객체접근");
		}

		return instance;

	}

	// DAO 참조변수 선언
	private static ItemDAO itemDAO = ItemDAOImpl.getInstance();
	private static CodeDetailDAO codeDetailDAO = CodeDetailDAOImpl.getInstance();
	private static BomDAO bomDAO = BomDAOImpl.getInstance();

	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {

		if (logger.isDebugEnabled()) {
			logger.debug("ItemApplicationServiceImpl : getItemInfoList 시작");
		}

		ArrayList<ItemInfoTO> itemInfoList = null;

		try {

			switch (searchCondition) {

			case "ALL":

				itemInfoList = itemDAO.selectAllItemList();

				break;

			case "ITEM_CLASSIFICATION":

				itemInfoList = itemDAO.selectItemList("ITEM_CLASSIFICATION", paramArray);

				break;

			case "ITEM_GROUP_CODE":

				itemInfoList = itemDAO.selectItemList("ITEM_GROUP_CODE", paramArray);

				break;

			case "STANDARD_UNIT_PRICE":

				itemInfoList = itemDAO.selectItemList("STANDARD_UNIT_PRICE", paramArray);

				break;

			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ItemApplicationServiceImpl : getItemInfoList 종료");
		}
		return itemInfoList;
	}

	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {

		if (logger.isDebugEnabled()) {
			logger.debug("ItemApplicationServiceImpl : batchItemListProcess 시작");
		}

		HashMap<String, Object> resultMap = new HashMap<>();


		try {

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			CodeDetailTO detailCodeTO = new CodeDetailTO();
			BomTO bomTO = new BomTO();
			
			for (ItemTO TO : itemTOList) {

				String status = TO.getStatus();

				switch (status) {

				case "INSERT":

					itemDAO.insertItem(TO);
					insertList.add(TO.getItemCode());

					// CODE_DETAIL 테이블에 Insert
					detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
					detailCodeTO.setDetailCode(TO.getItemCode());
					detailCodeTO.setDetailCodeName(TO.getItemName());
					detailCodeTO.setDescription(TO.getDescription());

					codeDetailDAO.insertDetailCode(detailCodeTO);

					
					// 새로운 품목이 완제품 (ItemClassification : "IT-CI") , 반제품 (ItemClassification : "IT-SI") 일 경우 BOM 테이블에 Insert
					if( TO.getItemClassification().equals("IT-CI") || TO.getItemClassification().equals("IT-SI") ) {
						
						bomTO.setNo(1);
						bomTO.setParentItemCode("NULL");
						bomTO.setItemCode( TO.getItemCode() );
						bomTO.setNetAmount(1);
						
						bomDAO.insertBom(bomTO);
					}
					
					
					break;

				case "UPDATE":

					itemDAO.updateItem(TO);

					updateList.add(TO.getItemCode());

					// CODE_DETAIL 테이블에 Update
					detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
					detailCodeTO.setDetailCode(TO.getItemCode());
					detailCodeTO.setDetailCodeName(TO.getItemName());
					detailCodeTO.setDescription(TO.getDescription());

					codeDetailDAO.updateDetailCode(detailCodeTO);

					break;

				case "DELETE":

					itemDAO.deleteItem(TO);

					deleteList.add(TO.getItemCode());

					// CODE_DETAIL 테이블에 Delete
					detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
					detailCodeTO.setDetailCode(TO.getItemCode());
					detailCodeTO.setDetailCodeName(TO.getItemName());
					detailCodeTO.setDescription(TO.getDescription());

					codeDetailDAO.deleteDetailCode(detailCodeTO);

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ItemApplicationServiceImpl : batchItemListProcess 종료");
		}
		return resultMap;

	}

}
