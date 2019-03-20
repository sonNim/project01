package com.JHlogistics.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.logistics.purchase.dao.BomDAO;
import com.JHlogistics.logistics.purchase.dao.BomDAOImpl;
import com.JHlogistics.logistics.purchase.to.BomTO;
import com.JHlogistics.logistics.purchase.to.BomDeployTO;
import com.JHlogistics.logistics.purchase.to.BomInfoTO;

public class BomApplicationServiceImpl implements BomApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(BomApplicationServiceImpl.class);

	// 싱글톤
	private static BomApplicationService instance = new BomApplicationServiceImpl();

	private BomApplicationServiceImpl() {
	}

	public static BomApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ BomApplicationServiceImpl 객체접근");
		}

		return instance;
	}

	// DAO 참조변수 선언
	private static BomDAO bomDAO = BomDAOImpl.getInstance();

	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode) {

		if (logger.isDebugEnabled()) {
			logger.debug("BomApplicationServiceImpl : getBomDeployList 시작");
		}

		ArrayList<BomDeployTO> bomDeployList = null;

		try {

			bomDeployList = bomDAO.selectBomDeployList(deployCondition, itemCode);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BomApplicationServiceImpl : getBomDeployList 종료");
		}
		return bomDeployList;
	}

	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {

		if (logger.isDebugEnabled()) {
			logger.debug("BomApplicationServiceImpl : getBomInfoList 시작");
		}

		ArrayList<BomInfoTO> bomInfoList = null;

		try {

			bomInfoList = bomDAO.selectBomInfoList(parentItemCode);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BomApplicationServiceImpl : getBomInfoList 종료");
		}
		return bomInfoList;
	}

	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {

		if (logger.isDebugEnabled()) {
			logger.debug("BomApplicationServiceImpl : getAllItemWithBomRegisterAvailable 시작");
		}

		ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = null;

		try {

			allItemWithBomRegisterAvailable = bomDAO.selectAllItemWithBomRegisterAvailable();

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BomApplicationServiceImpl : getAllItemWithBomRegisterAvailable 종료");
		}
		return allItemWithBomRegisterAvailable;

	}

	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {

		if (logger.isDebugEnabled()) {
			logger.debug("BomApplicationServiceImpl : batchBomListProcess 시작");
		}

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			int insertCount = 0;
			int updateCount = 0;
			int deleteCount = 0;

			for (BomTO TO : batchBomList) {

				String status = TO.getStatus();

				switch (status) {

				case "INSERT":

					bomDAO.insertBom(TO);

					insertCount++;

					break;

				case "UPDATE":

					bomDAO.updateBom(TO);

					updateCount++;

					break;

				case "DELETE":

					bomDAO.deleteBom(TO);

					deleteCount++;

					break;

				}

			}

			resultMap.put("INSERT", insertCount);
			resultMap.put("UPDATE", updateCount);
			resultMap.put("DELETE", deleteCount);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("BomApplicationServiceImpl : batchBomListProcess 종료");
		}
		return resultMap;
	}

}
