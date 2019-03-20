package com.JHlogistics.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.logistics.sales.dao.ContractDAO;
import com.JHlogistics.logistics.sales.dao.ContractDAOImpl;
import com.JHlogistics.logistics.sales.dao.ContractDetailDAO;
import com.JHlogistics.logistics.sales.dao.ContractDetailDAOImpl;
import com.JHlogistics.logistics.sales.dao.EstimateDAO;
import com.JHlogistics.logistics.sales.dao.EstimateDAOImpl;
import com.JHlogistics.logistics.sales.to.ContractTO;
import com.JHlogistics.logistics.sales.to.ContractDetailTO;
import com.JHlogistics.logistics.sales.to.ContractInfoTO;
import com.JHlogistics.logistics.sales.to.EstimateTO;
import com.JHlogistics.logistics.sales.to.EstimateDetailTO;

public class ContractApplicationServiceImpl implements ContractApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(ContractApplicationServiceImpl.class);

	// 싱글톤
	private static ContractApplicationService instance = new ContractApplicationServiceImpl();

	private ContractApplicationServiceImpl() {
	}

	public static ContractApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ ContractApplicationServiceImpl 객체접근");
		}

		return instance;
	}

	// 참조변수 선언
	private static ContractDAO contractDAO = ContractDAOImpl.getInstance();
	private static ContractDetailDAO contractDetailDAO = ContractDetailDAOImpl.getInstance();
	private static EstimateDAO estimateDAO = EstimateDAOImpl.getInstance();

	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getContractList 시작");
		}

		ArrayList<ContractInfoTO> contractInfoTOList = null;

		try {

			switch (searchCondition) {

			case "searchByDate":

				String startDate = paramArray[0];
				String endDate = paramArray[1];

				contractInfoTOList = contractDAO.selectContractListByDate(startDate, endDate);

				break;

			case "searchByCustomer":

				String customerCode = paramArray[0];

				contractInfoTOList = contractDAO.selectContractListByCustomer(customerCode);

				break;

			}

			for (ContractInfoTO bean : contractInfoTOList) {

				bean.setContractDetailTOList(contractDetailDAO.selectContractDetailList(bean.getContractNo()));

			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getContractList 종료");
		}
		return contractInfoTOList;

	}

	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String contractNo) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getContractDetailList 시작");
		}

		ArrayList<ContractDetailTO> contractDetailTOList = null;

		try {

			contractDetailTOList = contractDetailDAO.selectContractDetailList(contractNo);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getContractDetailList 종료");
		}
		return contractDetailTOList;
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getEstimateListInContractAvailable 시작");
		}

		ArrayList<EstimateTO> estimateListInContractAvailable = null;

		try {

			estimateListInContractAvailable = contractDAO.selectEstimateListInContractAvailable(startDate, endDate);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getEstimateListInContractAvailable 종료");
		}
		return estimateListInContractAvailable;
	}

	@Override
	public String getNewContractNo(String contractDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getNewContractNo 시작");
		}

		StringBuffer newContractNo = null;

		try {

			int i = contractDAO.selectContractCount(contractDate);

			newContractNo = new StringBuffer();
			newContractNo.append("CO");
			newContractNo.append(contractDate.replace("-", ""));
			newContractNo.append(String.format("%02d", i));

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : getNewContractNo 종료");
		}
		return newContractNo.toString();
	}

	@Override
	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge,
			ContractTO workingContractBean, EstimateTO previousEstimateBean) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : addNewContracto 시작");
		}

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			// 새로운 수주일련번호 생성
			String newContractNo = getNewContractNo(contractDate);

			workingContractBean.setContractNo(newContractNo); // 새로운 수주일련번호 세팅
			workingContractBean.setContractDate(contractDate); // 뷰에서 전달한 수주일자 세팅
			workingContractBean.setPersonCodeInCharge(personCodeInCharge); // 뷰에서 전달한 수주담당자코드 세팅

			// DAO 파트 시작
			contractDAO.insertContract(workingContractBean);
			// DAO 파트 끝

			// 견적 테이블에 수주여부 "Y" 로 수정
			changeContractStatusInEstimate(workingContractBean.getEstimateNo(), "Y");

			/*
			 * 뷰로부터 받은 견적상세 목록을 수주상세 목록으로 변환 시작 : 견적상세 정보를 바로 수주상세 Bean 으로 만들어도 itemCode,
			 * itemName, customerCode, description 은 남음 => 속성명이 같으므로..
			 */

			/*
			 * ( 견적상세 => 수주상세 ) 로 옮겨야 할 속성 : unitOfEstimate => unitOfContract ,
			 * dueDateOfEstimate => dueDateOfContract , estimateAmount => contractAmount ,
			 * unitPriceOfEstimate => unitPriceOfContract , sumPriceOfEstimate =>
			 * sumPriceOfContract
			 */

			/*
			 * ( 견적상세 => 수주상세 ) 를 어떻게 매칭시킬까? =? "itemCode" !! 견적상세 / 수주상세 리스트 각각에서 itemCode
			 * 는 중복되지 않음
			 * 
			 * ( itemCode , 그 품목에 해당하는 견적상세 TO ) 키 / 값을 담을 HashMap 을 하나 생성해서 반복문을 돌리며 값을
			 * 전달한다
			 */

			ArrayList<EstimateDetailTO> previousEstimateDetailTOList = previousEstimateBean.getEstimateDetailTOList();
			ArrayList<ContractDetailTO> contractDetailTOList = workingContractBean.getContractDetailTOList();

			HashMap<String, EstimateDetailTO> estimteDetailMap = new HashMap<>();

			for (EstimateDetailTO bean : previousEstimateDetailTOList) {

				estimteDetailMap.put(bean.getItemCode(), bean);

			}

			// 새로운 수주상세일련번호를 담을 Integer, StringBuffer

			int newNo = 1;

			StringBuffer newContractDetailNo = new StringBuffer();

			// 수주상세 리스트로 반복문 돌리면서 일련번호 부여, ( 견적상세 => 수주상세 ) 로 값 옮겨담기,
			// "NORMAL" 로 되어있는 수주상세 Bean 들의 status 를 "INSERT" 로 변경
			for (ContractDetailTO bean : contractDetailTOList) {

				// 새로운 수주상세일련번호 생성
				newContractDetailNo = new StringBuffer();
				newContractDetailNo.append(newContractNo);
				newContractDetailNo.append("-");
				newContractDetailNo.append(String.format("%02d", newNo++)); // 두자리 숫자, 번호 생성 후 하나 증가

				// 수주일련번호, 수주상세일련번호 세팅
				bean.setContractNo(newContractNo);
				bean.setContractDetailNo(newContractDetailNo.toString());

				// itemCode 로 ( 견적상세 => 수주상세 ) 로 값 옮겨담기
				String itemCode = bean.getItemCode();

				bean.setUnitOfContract(estimteDetailMap.get(itemCode).getUnitOfEstimate());
				bean.setDueDateOfContract(estimteDetailMap.get(itemCode).getDueDateOfEstimate());
				bean.setContractAmount(estimteDetailMap.get(itemCode).getEstimateAmount());
				bean.setUnitPriceOfContract(estimteDetailMap.get(itemCode).getUnitPriceOfEstimate());
				bean.setSumPriceOfContract(estimteDetailMap.get(itemCode).getSumPriceOfEstimate());

				bean.setStatus("INSERT");
			}

			// batchListProcess 로 수주상세 Insert, 결과 맵 반환
			resultMap = batchContractDetailListProcess(contractDetailTOList);

			resultMap.put("newContractNo", newContractNo);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : addNewContracto 종료");
		}
		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : batchContractDetailListProcess 시작");
		}

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			for (ContractDetailTO bean : contractDetailTOList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					contractDetailDAO.insertContractDetail(bean);
					insertList.add(bean.getContractDetailNo());

					break;

				case "UPDATE":

					contractDetailDAO.updateContractDetail(bean);
					updateList.add(bean.getContractDetailNo());

					break;

				case "DELETE":

					contractDetailDAO.deleteContractDetail(bean);
					deleteList.add(bean.getContractDetailNo());

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
			logger.debug("ContractApplicationServiceImpl : batchContractDetailListProcess 종료");
		}
		return resultMap;
	}

	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : changeContractStatusInEstimate 시작");
		}

		try {

			estimateDAO.changeContractStatusOfEstimate(estimateNo, contractStatus);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractApplicationServiceImpl : changeContractStatusInEstimate 종료");
		}
	}

}
