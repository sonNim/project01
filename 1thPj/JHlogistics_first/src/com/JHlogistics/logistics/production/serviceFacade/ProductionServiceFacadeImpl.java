package com.JHlogistics.logistics.production.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.db.DataSourceTransactionManager;
import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.logistics.production.applicationService.MpsApplicationService;
import com.JHlogistics.logistics.production.applicationService.MpsApplicationServiceImpl;
import com.JHlogistics.logistics.production.applicationService.MrpApplicationService;
import com.JHlogistics.logistics.production.applicationService.MrpApplicationServiceImpl;
import com.JHlogistics.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.JHlogistics.logistics.production.to.MpsTO;
import com.JHlogistics.logistics.production.to.MrpTO;
import com.JHlogistics.logistics.production.to.MrpGatheringTO;
import com.JHlogistics.logistics.production.to.SalesPlanInMpsAvailableTO;

public class ProductionServiceFacadeImpl implements ProductionServiceFacade {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(ProductionServiceFacadeImpl.class);

	// 싱글톤
	private static ProductionServiceFacade instance = new ProductionServiceFacadeImpl();

	private ProductionServiceFacadeImpl() {
	}

	public static ProductionServiceFacade getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ ProductionServiceFacadeImpl 객체접근");
		}

		return instance;
	}

	// 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();
	private static MpsApplicationService mpsAS = MpsApplicationServiceImpl.getInstance();
	private static MrpApplicationService mrpAS = MrpApplicationServiceImpl.getInstance();

	@Override
	public ArrayList<MpsTO> getMpsList(String startDate, String endDate, String includeMrpApply) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : getMpsList 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<MpsTO> mpsTOList = null;

		try {
			mpsTOList = mpsAS.getMpsList(startDate, endDate, includeMrpApply);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : getMpsList 종료");
		}

		return mpsTOList;
	}

	@Override
	public ArrayList<ContractDetailInMpsAvailableTO> getContractDetailListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : getContractDetailListInMpsAvailable 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = null;

		try {
			contractDetailInMpsAvailableList = mpsAS.getContractDetailListInMpsAvailable(searchCondition, startDate,
					endDate);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : getContractDetailListInMpsAvailable 종료");
		}

		return contractDetailInMpsAvailableList;

	}

	@Override
	public ArrayList<SalesPlanInMpsAvailableTO> getSalesPlanListInMpsAvailable(String searchCondition,
			String startDate, String endDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : getSalesPlanListInMpsAvailable 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = null;

		try {
			salesPlanInMpsAvailableList = mpsAS.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : getSalesPlanListInMpsAvailable 종료");
		}

		return salesPlanInMpsAvailableList;

	}

	@Override
	public HashMap<String, Object> convertContractDetailToMps(
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : convertContractDetailToMps 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		HashMap<String, Object> resultMap = null;

		try {
			resultMap = mpsAS.convertContractDetailToMps(contractDetailInMpsAvailableList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : convertContractDetailToMps 종료");
		}

		return resultMap;

	}

	@Override
	public HashMap<String, Object> convertSalesPlanToMps(
			ArrayList<SalesPlanInMpsAvailableTO> contractDetailInMpsAvailableList) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : convertSalesPlanToMps 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		HashMap<String, Object> resultMap = null;

		try {
			resultMap = mpsAS.convertSalesPlanToMps(contractDetailInMpsAvailableList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : convertSalesPlanToMps 종료");
		}

		return resultMap;

	}

	@Override
	public HashMap<String, Object> batchMpsListProcess(ArrayList<MpsTO> mpsTOList) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : batchMpsListProcess 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		HashMap<String, Object> resultMap = null;

		try {
			resultMap = mpsAS.batchMpsListProcess(mpsTOList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : batchMpsListProcess 종료");
		}

		return resultMap;

	}

	@Override
	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : searchMrpList 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<MrpTO> mrpList = null;

		try {

			mrpList = mrpAS.searchMrpList(mrpGatheringStatusCondition);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : searchMrpList 종료");
		}

		return mrpList;

	}

	@Override
	public ArrayList<MrpTO> searchMrpList(String dateSearchCondtion, String startDate, String endDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : searchMrpList 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<MrpTO> mrpList = null;

		try {

			mrpList = mrpAS.searchMrpList(dateSearchCondtion, startDate, endDate);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : searchMrpList 종료");
		}

		return mrpList;
	}

	@Override
	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : searchMrpListAsMrpGatheringNo 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<MrpTO> mrpList = null;

		try {
			mrpList = mrpAS.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : searchMrpListAsMrpGatheringNo 종료");
		}

		return mrpList;
	}

	@Override
	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
			String endDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : searchMrpGatheringList 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<MrpGatheringTO> mrpGatheringList = null;

		try {
			mrpGatheringList = mrpAS.searchMrpGatheringList(dateSearchCondtion, startDate, endDate);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : searchMrpGatheringList 종료");
		}

		return mrpGatheringList;
	}

	@Override
	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : openMrp 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		HashMap<String, Object> resultMap = null;

		try {
			resultMap = mrpAS.openMrp(mpsNoArr);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : openMrp 종료");
		}

		return resultMap;
	}

	@Override
	public HashMap<String, Object> registerMrp(String mrpRegisterDate, ArrayList<MrpTO> newMrpList) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : registerMrp 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		HashMap<String, Object> resultMap = null;

		try {
			resultMap = mrpAS.registerMrp(mrpRegisterDate, newMrpList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : registerMrp 종료");
		}

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : batchMrpListProcess 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		HashMap<String, Object> resultMap = null;

		try {
			resultMap = mrpAS.batchMrpListProcess(mrpTOList);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : batchMrpListProcess 종료");
		}

		return resultMap;
	}

	@Override
	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : getMrpGathering 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		ArrayList<MrpGatheringTO> mrpGatheringList = null;

		try {
			mrpGatheringList = mrpAS.getMrpGathering(mrpNoArr);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : getMrpGathering 종료");
		}

		return mrpGatheringList;
	}

	@Override
	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate,
			ArrayList<MrpGatheringTO> newMrpGatheringList, HashMap<String, String> mrpNoAndItemCodeMap) {

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : registerMrpGathering 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		HashMap<String, Object> resultMap = null;

		try {
			resultMap = mrpAS.registerMrpGathering(mrpGatheringRegisterDate, newMrpGatheringList, mrpNoAndItemCodeMap);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ProductionServiceFacadeImpl : registerMrpGathering 종료");
		}

		return resultMap;
	}

}
