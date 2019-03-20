package com.JHlogistics.logistics.sales.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.common.servlet.ModelAndView;
import com.JHlogistics.common.servlet.controller.MultiActionController;
import com.JHlogistics.logistics.sales.serviceFacade.SalesServiceFacade;
import com.JHlogistics.logistics.sales.serviceFacade.SalesServiceFacadeImpl;
import com.JHlogistics.logistics.sales.to.ContractTO;
import com.JHlogistics.logistics.sales.to.ContractDetailTO;
import com.JHlogistics.logistics.sales.to.ContractInfoTO;
import com.JHlogistics.logistics.sales.to.EstimateTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ContractController extends MultiActionController {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(ContractController.class);

	// serviceFacade 참조변수 선언
	SalesServiceFacade salesSF = SalesServiceFacadeImpl.getInstance();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 변환

	public ModelAndView searchContract(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : searchContract 시작");
		}

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String customerCode = request.getParameter("customerCode");

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {

			out = response.getWriter();

			ArrayList<ContractInfoTO> contractInfoTOList = null;

			if (searchCondition.equals("searchByDate")) {

				String[] paramArray = { startDate, endDate };
				contractInfoTOList = salesSF.getContractList("searchByDate", paramArray);

			} else if (searchCondition.equals("searchByCustomer")) {

				String[] paramArray = { customerCode };
				contractInfoTOList = salesSF.getContractList("searchByCustomer", paramArray);

			}

			map.put("gridRowJson", contractInfoTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());

		} finally {
			out.println(gson.toJson(map));
			out.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : searchContract 종료");
		}
		return null;
	}

	public ModelAndView searchContractDetail(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : searchContractDetail 시작");
		}

		String contractNo = request.getParameter("contractNo");

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {

			out = response.getWriter();

			ArrayList<ContractDetailTO> contractDetailTOList = salesSF.getContractDetailList(contractNo);

			map.put("gridRowJson", contractDetailTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());

		} finally {
			out.println(gson.toJson(map));
			out.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : searchContractDetail 종료");
		}
		return null;
	}

	public ModelAndView searchEstimateInContractAvailable(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : searchEstimateInContractAvailable 시작");
		}

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {

			out = response.getWriter();

			ArrayList<EstimateTO> estimateListInContractAvailable = salesSF
					.getEstimateListInContractAvailable(startDate, endDate);

			map.put("gridRowJson", estimateListInContractAvailable);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());

		} finally {
			out.println(gson.toJson(map));
			out.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : searchEstimateInContractAvailable 종료");
		}
		return null;
	}

	public ModelAndView addNewContract(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : addNewContract 시작");
		}
		String batchList = request.getParameter("batchList");
		String contractDate = request.getParameter("contractDate");
		String personCodeInCharge = request.getParameter("personCodeInCharge");

		HashMap<String, Object> map = new HashMap<>();
		PrintWriter out = null;

		try {
			out = response.getWriter();

			EstimateTO previousEstimateTO = gson.fromJson(batchList, EstimateTO.class);

			ContractTO workingContractTO = gson.fromJson(batchList, ContractTO.class);

			HashMap<String, Object> resultList = salesSF.addNewContract(contractDate, personCodeInCharge,
					workingContractTO, previousEstimateTO);

			map.put("result", resultList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");

		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());

		} finally {
			out.println(gson.toJson(map));
			out.close();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : addNewContract 종료");
		}
		return null;
	}

	public ModelAndView cancleEstimate(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : cancleEstimate 시작");
		}
		String estimateNo = request.getParameter("estimateNo");

		HashMap<String, Object> map = new HashMap<>();
		PrintWriter out = null;

		try {

			out = response.getWriter();

			salesSF.changeContractStatusInEstimate(estimateNo, "N");

			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			map.put("cancledEstimateNo", estimateNo);

		} catch (IOException e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());

		} catch (DataAccessException e2) {
			e2.printStackTrace();
			map.put("errorCode", -2);
			map.put("errorMsg", e2.getMessage());

		} finally {
			out.println(gson.toJson(map));
			out.close();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ContractController : cancleEstimate 종료");
		}
		return null;
	}

}
