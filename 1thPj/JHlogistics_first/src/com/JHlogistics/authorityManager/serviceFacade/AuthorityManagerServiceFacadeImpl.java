package com.JHlogistics.authorityManager.serviceFacade;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.authorityManager.applicationService.LogInApplicationService;
import com.JHlogistics.authorityManager.applicationService.LogInApplicationServiceImpl;
import com.JHlogistics.authorityManager.applicationService.UserMenuApplicationService;
import com.JHlogistics.authorityManager.applicationService.UserMenuApplicationServiceImpl;
import com.JHlogistics.authorityManager.exception.IdNotFoundException;
import com.JHlogistics.authorityManager.exception.PwMissMatchException;
import com.JHlogistics.authorityManager.exception.PwNotFoundException;
import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.hr.to.EmpInfoTO;
import com.JHlogistics.common.db.DataSourceTransactionManager;

public class AuthorityManagerServiceFacadeImpl implements AuthorityManagerServiceFacade {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(AuthorityManagerServiceFacadeImpl.class);

	// 싱글톤
	private static AuthorityManagerServiceFacade instance = new AuthorityManagerServiceFacadeImpl();

	private AuthorityManagerServiceFacadeImpl() {
	}

	public static AuthorityManagerServiceFacade getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ AuthorityManagerServiceFacadeImpl 객체접근");
		}

		return instance;
	}

	// AS 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();

	private static LogInApplicationService logInAS = LogInApplicationServiceImpl.getInstance();
	private static UserMenuApplicationService userMenuAS = UserMenuApplicationServiceImpl.getInstance();

	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {

		if (logger.isDebugEnabled()) {
			logger.debug("LogInServiceFacadeImpl : accessToAuthority 시작");
		}

		dataSourceTransactionManager.beginTransaction();
		EmpInfoTO TO = null;

		try {

			TO = logInAS.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("LogInServiceFacadeImpl : accessToAuthority 종료");
		}
		return TO;
	}

	@Override
	public String getUserMenuCode(String workplaceCode, String deptCode, String positionCode,
			ServletContext application) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("LogInServiceFacadeImpl : getUserMenuCode 시작");
		}
		
		dataSourceTransactionManager.beginTransaction();
		System.out.println("		@ DB 접근 : getUserMenuCode");

		String userMenuCode = null;

		try {
		
			userMenuCode = userMenuAS.getUserMenuCode(workplaceCode, deptCode, positionCode, application);
			dataSourceTransactionManager.commitTransaction();

		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			dataSourceTransactionManager.rollbackTransaction();
			throw e;
		} 
		
		if (logger.isDebugEnabled()) {
			logger.debug("LogInServiceFacadeImpl : getUserMenuCode 종료");
		}
		return userMenuCode;
	}

}
