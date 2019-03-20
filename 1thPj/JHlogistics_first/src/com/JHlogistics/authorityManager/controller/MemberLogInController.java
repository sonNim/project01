package com.JHlogistics.authorityManager.controller;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.authorityManager.exception.*;
import com.JHlogistics.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import com.JHlogistics.authorityManager.serviceFacade.AuthorityManagerServiceFacadeImpl;
import com.JHlogistics.common.servlet.ModelAndView;
import com.JHlogistics.common.servlet.controller.MultiActionController;
import com.JHlogistics.hr.to.EmpInfoTO;

public class MemberLogInController extends MultiActionController {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(MemberLogInController.class);

	// serviceFacade 참조변수 선언
	private static AuthorityManagerServiceFacade authorityManagerSF = AuthorityManagerServiceFacadeImpl.getInstance();

	public ModelAndView LogInCheck(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("MemberLogInController :  LogInCheck 시작");
		}

		String viewName = null;
		HashMap<String, Object> model = new HashMap<String, Object>();

		HttpSession session = request.getSession();

		String companyCode = request.getParameter("companyCode");
		String workplaceCode = request.getParameter("workplaceCode");
		String inputId = request.getParameter("userId");
		String inputPassWord = request.getParameter("userPassWord");

		try {
			if (companyCode.equals("") || workplaceCode.equals("") || inputId.equals("") || inputPassWord.equals("")) {
				throw new DataNotInputException("입력하지 않은 값이 있습니다");
			}

			EmpInfoTO TO = authorityManagerSF.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);

			if (TO != null) {

				ServletContext application = request.getServletContext();

				session.setAttribute("userId", TO.getUserId());
				session.setAttribute("empCode", TO.getEmpCode());
				session.setAttribute("empName", TO.getEmpName());
				session.setAttribute("deptCode", TO.getDeptCode());
				session.setAttribute("deptName", TO.getDeptName());
				session.setAttribute("positionCode", TO.getPositionCode());
				session.setAttribute("positionName", TO.getPositionName());
				session.setAttribute("companyCode", TO.getCompanyCode());
				session.setAttribute("workplaceCode", workplaceCode);
				session.setAttribute("workplaceName", TO.getWorkplaceName());

				String menuCode = authorityManagerSF.getUserMenuCode(workplaceCode, TO.getDeptCode(), TO.getPositionCode(),
						application);
				session.setAttribute("menuCode", menuCode);

				viewName = "redirect:" + request.getContextPath() + "/hello.html";
				System.out.println("로그인 되었습니다");
			}

		} catch (DataNotInputException e1) {
			e1.printStackTrace();
			model.put("errorCode", -1);
			model.put("errorMsg", e1.getMessage());
			viewName = "loginform";
		} catch (IdNotFoundException e2) {
			e2.printStackTrace();
			model.put("errorCode", -2);
			model.put("errorMsg", e2.getMessage());
			viewName = "loginform";
		} catch (PwNotFoundException e3) {
			e3.printStackTrace();
			model.put("errorCode", -3);
			model.put("errorMsg", e3.getMessage());
			viewName = "loginform";
		} catch (PwMissMatchException e4) {
			e4.printStackTrace();
			model.put("errorCode", -4);
			model.put("errorMsg", e4.getMessage());
			viewName = "loginform";
		} catch (Exception e) {
			e.printStackTrace();
			model.put("errorCode", -5);
			model.put("errorMsg", e.getMessage());
			viewName = "loginform";
		}

		ModelAndView modelAndView = new ModelAndView(viewName, model);

		if (logger.isDebugEnabled()) {
			logger.debug("MemberLogInController :  LogInCheck 종료");
		}
		return modelAndView;

	}

}