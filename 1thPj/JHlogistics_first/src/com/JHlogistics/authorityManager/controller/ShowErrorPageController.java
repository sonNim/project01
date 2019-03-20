package com.JHlogistics.authorityManager.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.basicInfo.controller.CustomerController;
import com.JHlogistics.common.servlet.ModelAndView;
import com.JHlogistics.common.servlet.controller.MultiActionController;

public class ShowErrorPageController extends MultiActionController {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(CustomerController.class);

	// method 지정 없이 handleRequestInternal 메소드 오버라이딩
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("ShowErrorPageController :  handleRequestInternal 시작");
		}

		String viewName = "redirect:" + request.getContextPath() + "/hello.html";

		HashMap<String, Object> model = new HashMap<String, Object>();

		if (request.getRequestURI().contains("accessDenied")) {
			model.put("errorCode", -1);
			model.put("errorTitle", "Access Denied");
			model.put("errorMsg", "접근 권한이 없습니다. 관리자에게 문의하세요 ^^");
			viewName = "errorPage";
		}

		ModelAndView modelAndView = new ModelAndView(viewName, model);

		if (logger.isDebugEnabled()) {
			logger.debug("ShowErrorPageController :  handleRequestInternal 종료");
		}
		return modelAndView;
	}

}
