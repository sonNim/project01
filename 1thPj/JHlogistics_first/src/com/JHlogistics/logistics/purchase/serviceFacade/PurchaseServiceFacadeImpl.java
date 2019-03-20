package com.JHlogistics.logistics.purchase.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.db.DataSourceTransactionManager;
import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.logistics.purchase.applicationService.BomApplicationService;
import com.JHlogistics.logistics.purchase.applicationService.BomApplicationServiceImpl;
import com.JHlogistics.logistics.purchase.to.BomTO;
import com.JHlogistics.logistics.purchase.to.BomDeployTO;
import com.JHlogistics.logistics.purchase.to.BomInfoTO;

public class PurchaseServiceFacadeImpl implements PurchaseServiceFacade {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(PurchaseServiceFacadeImpl.class);

	// 싱글톤
	private static PurchaseServiceFacade instance = new PurchaseServiceFacadeImpl();

	private PurchaseServiceFacadeImpl() {
	}

	public static PurchaseServiceFacade getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ PurchaseServiceFacadeImpl 객체접근");
		}

		return instance;
	}

	// 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();
	private static BomApplicationService bomAS = BomApplicationServiceImpl.getInstance();

	@Override
	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode) {

		if (logger.isDebugEnabled()) {
			logger.debug("PurchaseServiceFacadeImpl : getBomDeployList 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<BomDeployTO> bomDeployList = null;

		try {

			bomDeployList = bomAS.getBomDeployList(deployCondition, itemCode);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("PurchaseServiceFacadeImpl : getBomDeployList 시작");
		}

		return bomDeployList;
	}

	@Override
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {

		if (logger.isDebugEnabled()) {
			logger.debug("PurchaseServiceFacadeImpl : getBomInfoList 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<BomInfoTO> bomInfoList = null;

		try {

			bomInfoList = bomAS.getBomInfoList(parentItemCode);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("PurchaseServiceFacadeImpl : getBomInfoList 시작");
		}

		return bomInfoList;
	}

	@Override
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {

		if (logger.isDebugEnabled()) {
			logger.debug("PurchaseServiceFacadeImpl : getAllItemWithBomRegisterAvailable 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = null;

		try {

			allItemWithBomRegisterAvailable = bomAS.getAllItemWithBomRegisterAvailable();
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("PurchaseServiceFacadeImpl : getAllItemWithBomRegisterAvailable 시작");
		}

		return allItemWithBomRegisterAvailable;
	}

	@Override
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {

		if (logger.isDebugEnabled()) {
			logger.debug("PurchaseServiceFacadeImpl : batchBomListProcess 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		HashMap<String, Object> resultMap = null;

		try {

			resultMap = bomAS.batchBomListProcess(batchBomList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("PurchaseServiceFacadeImpl : batchBomListProcess 시작");
		}

		return resultMap;

	}

}
