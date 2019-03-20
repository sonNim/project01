package com.JHlogistics.logistics.logisticsInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.db.DataSourceTransactionManager;
import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.logistics.logisticsInfo.applicationService.ItemApplicationService;
import com.JHlogistics.logistics.logisticsInfo.applicationService.ItemApplicationServiceImpl;
import com.JHlogistics.logistics.logisticsInfo.to.ItemTO;
import com.JHlogistics.logistics.logisticsInfo.to.ItemInfoTO;

public class LogisticsInfoServiceFacadeImpl implements LogisticsInfoServiceFacade {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(LogisticsInfoServiceFacadeImpl.class);

	// 싱글톤
	private static LogisticsInfoServiceFacade instance = new LogisticsInfoServiceFacadeImpl();

	private LogisticsInfoServiceFacadeImpl() {
	}

	public static LogisticsInfoServiceFacade getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ LogisticsInfoServiceFacadeImpl 객체접근");
		}

		return instance;
	}

	// 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();
	private static ItemApplicationService itemAS = ItemApplicationServiceImpl.getInstance();

	@Override
	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {

		if (logger.isDebugEnabled()) {
			logger.debug("LogisticsInfoServiceFacadeImpl : getItemInfoList 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<ItemInfoTO> itemInfoList = null;

		try {

			itemInfoList = itemAS.getItemInfoList(searchCondition, paramArray);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("LogisticsInfoServiceFacadeImpl : getItemInfoList 시작");
		}

		return itemInfoList;
	}

	@Override
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {
		if (logger.isDebugEnabled()) {
			logger.debug("LogisticsInfoServiceFacadeImpl : batchItemListProcess 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		HashMap<String, Object> resultMap = null;

		try {

			resultMap = itemAS.batchItemListProcess(itemTOList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("LogisticsInfoServiceFacadeImpl : batchItemListProcess 시작");
		}
		return resultMap;
	}

}
