package com.JHlogistics.logistics.production.controller;

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
import com.JHlogistics.logistics.production.serviceFacade.ProductionServiceFacade;
import com.JHlogistics.logistics.production.serviceFacade.ProductionServiceFacadeImpl;
import com.JHlogistics.logistics.production.to.MrpTO;
import com.JHlogistics.logistics.production.to.MrpGatheringTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MrpController extends MultiActionController {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(MrpController.class);
	
	// serviceFacade 참조변수 선언
	ProductionServiceFacade productionSF = ProductionServiceFacadeImpl.getInstance();
	
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
	
	
	
	public ModelAndView getMrpList(HttpServletRequest request, HttpServletResponse response) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("MrpController : getMrpList 시작");
		}
		
		String mrpGatheringStatusCondition = request.getParameter("mrpGatheringStatusCondition");
		
		String dateSearchCondition = request.getParameter("dateSearchCondition");
		
		String mrpStartDate = request.getParameter("mrpStartDate");
		String mrpEndDate = request.getParameter("mrpEndDate");

		String mrpGatheringNo = request.getParameter("mrpGatheringNo");
		
		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;
		
		try {
			out = response.getWriter();

			ArrayList<MrpTO> mrpList = null;
			
			if(mrpGatheringStatusCondition != null ) {
				
				mrpList = productionSF.searchMrpList(mrpGatheringStatusCondition);
				
			} else if (dateSearchCondition != null) {
				
				mrpList = productionSF.searchMrpList(dateSearchCondition, mrpStartDate, mrpEndDate);
				
			} else if (mrpGatheringNo != null) {
				
				mrpList = productionSF.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
				
			}
			
			map.put("gridRowJson", mrpList);
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
			logger.debug("MrpController : getMrpList 종료");
		}
		return null;
	}
	
	
	public ModelAndView openMrp(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpController : openMrp 시작");
		}
		
		String mpsNoListStr = request.getParameter("mpsNoList");

		ArrayList<String> mpsNoArr = gson.fromJson(mpsNoListStr,
				new TypeToken<ArrayList<String>>() { }.getType());		

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {
			out = response.getWriter();

			HashMap<String, Object> openMrpResultMap = productionSF.openMrp(mpsNoArr);
						
			map.put("gridRowJson", openMrpResultMap.get("openMrpList"));
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
			logger.debug("MrpController : openMrp 종료");
		}
		return null;
	}

	
	public ModelAndView registerMrp(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpController : registerMrp 시작");
		}
		
		String batchList = request.getParameter("batchList");
		String mrpRegisterDate = request.getParameter("mrpRegisterDate");
		
		ArrayList<MrpTO> newMrpList 
			= gson.fromJson(batchList,
					new TypeToken<ArrayList<MrpTO>>() { }.getType());	

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {
			out = response.getWriter();

			HashMap<String, Object> resultMap = productionSF.registerMrp(mrpRegisterDate, newMrpList);	 
			
			map.put("result", resultMap);
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
			logger.debug("MrpController : registerMrp 종료");
		}
		return null;
	}
	
	
	
	public ModelAndView getMrpGatheringList(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpController : getMrpGatheringList 시작");
		}
		
		String mrpNoList = request.getParameter("mrpNoList");
		
		ArrayList<String> mrpNoArr = gson.fromJson(mrpNoList,
				new TypeToken<ArrayList<String>>() { }.getType());		

		
		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {
			out = response.getWriter();

			ArrayList<MrpGatheringTO> mrpGatheringList = productionSF.getMrpGathering(mrpNoArr);
			
			map.put("gridRowJson", mrpGatheringList);
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
			logger.debug("MrpController : getMrpGatheringList 종료");
		}
		return null;
	}
	
	

	public ModelAndView registerMrpGathering(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpController : registerMrpGathering 종료");
		}
		
		String mrpGatheringRegisterDate = request.getParameter("mrpGatheringRegisterDate");
		String batchList = request.getParameter("batchList");
		String mrpNoAndItemCodeList = request.getParameter("mrpNoAndItemCodeList");
		
		ArrayList<MrpGatheringTO> newMrpGatheringList
			= gson.fromJson(batchList,
					new TypeToken<ArrayList<MrpGatheringTO>>() { }.getType());	
		
		HashMap<String, String> mrpNoAndItemCodeMap 
			=  gson.fromJson(mrpNoAndItemCodeList,
					new TypeToken<HashMap<String, String>>() { }.getType());	
			
		
		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {
			out = response.getWriter();

			HashMap<String, Object> resultMap 
				= productionSF.registerMrpGathering(mrpGatheringRegisterDate, newMrpGatheringList, mrpNoAndItemCodeMap);	 
			
			map.put("result", resultMap);
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
			logger.debug("MrpController : registerMrpGathering 시작");
		}
		return null;
	}
	

	public ModelAndView searchMrpGathering(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpController : searchMrpGathering 시작");
		}
		
		String searchDateCondition = request.getParameter("searchDateCondition");
		String startDate = request.getParameter("mrpGatheringStartDate");
		String endDate = request.getParameter("mrpGatheringEndDate");
		
		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {
			out = response.getWriter();

			ArrayList<MrpGatheringTO> mrpGatheringList = 
					productionSF.searchMrpGatheringList(searchDateCondition, startDate, endDate);
			
			map.put("gridRowJson", mrpGatheringList);
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
			logger.debug("MrpController : searchMrpGathering 종료");
		}
		return null;
	}
	
	
}
