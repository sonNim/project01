package com.JHlogistics.logistics.purchase.controller;

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
import com.JHlogistics.logistics.purchase.serviceFacade.PurchaseServiceFacade;
import com.JHlogistics.logistics.purchase.serviceFacade.PurchaseServiceFacadeImpl;
import com.JHlogistics.logistics.purchase.to.BomTO;
import com.JHlogistics.logistics.purchase.to.BomDeployTO;
import com.JHlogistics.logistics.purchase.to.BomInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class BomController extends MultiActionController {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(BomController.class);

	// serviceFacade 참조변수 선언
	PurchaseServiceFacade purchaseSF = PurchaseServiceFacadeImpl.getInstance();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	public ModelAndView searchBomDeploy(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("BomController : searchBomDeploy 시작");
		}

		String deployCondition = request.getParameter("deployCondition");
		String itemCode = request.getParameter("itemCode");

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {
			out = response.getWriter();

			ArrayList<BomDeployTO> bomDeployList = purchaseSF.getBomDeployList(deployCondition, itemCode);

			map.put("gridRowJson", bomDeployList);
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
			logger.debug("BomController : searchBomDeploy 종료");
		}
		return null;
	}

	public ModelAndView searchBomInfo(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("BomController : searchBomInfo 시작");
		}

		String parentItemCode = request.getParameter("parentItemCode");

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {
			out = response.getWriter();

			ArrayList<BomInfoTO> bomInfoList = purchaseSF.getBomInfoList(parentItemCode);

			map.put("gridRowJson", bomInfoList);
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
			logger.debug("BomController : searchBomInfo 종료");
		}
		return null;

	}

	public ModelAndView searchAllItemWithBomRegisterAvailable(HttpServletRequest request,
			HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("BomController : searchAllItemWithBomRegisterAvailable 시작");
		}

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {
			out = response.getWriter();

			ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = purchaseSF.getAllItemWithBomRegisterAvailable();

			map.put("gridRowJson", allItemWithBomRegisterAvailable);
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
			logger.debug("BomController : searchAllItemWithBomRegisterAvailable 종료");
		}
		return null;

	}

	public ModelAndView batchBomListProcess(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("BomController : batchBomListProcess 시작");
		}

		String batchList = request.getParameter("batchList");

		ArrayList<BomTO> batchBomList = gson.fromJson(batchList, new TypeToken<ArrayList<BomTO>>() {
		}.getType());

		HashMap<String, Object> map = new HashMap<>();

		PrintWriter out = null;

		try {
			out = response.getWriter();

			HashMap<String, Object> resultList = purchaseSF.batchBomListProcess(batchBomList);

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
			logger.debug("BomController : batchBomListProcess 종료");
		}
		return null;

	}

}
