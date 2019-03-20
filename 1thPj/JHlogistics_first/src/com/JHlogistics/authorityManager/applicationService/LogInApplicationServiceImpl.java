package com.JHlogistics.authorityManager.applicationService;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.authorityManager.exception.IdNotFoundException;
import com.JHlogistics.authorityManager.exception.PwMissMatchException;
import com.JHlogistics.authorityManager.exception.PwNotFoundException;
import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.hr.dao.EmpSearchingDAO;
import com.JHlogistics.hr.dao.EmpSearchingDAOImpl;
import com.JHlogistics.hr.dao.EmployeeSecretDAO;
import com.JHlogistics.hr.dao.EmployeeSecretDAOImpl;
import com.JHlogistics.hr.to.EmpInfoTO;
import com.JHlogistics.hr.to.EmployeeSecretTO;

public class LogInApplicationServiceImpl implements LogInApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(LogInApplicationServiceImpl.class);

	// 싱글톤
	private static LogInApplicationService instance = new LogInApplicationServiceImpl();

	private LogInApplicationServiceImpl() {
	}

	public static LogInApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ LogInApplicationServiceImpl 객체접근");
		}

		return instance;
	}

	// DAO 참조변수 선언
	private static EmpSearchingDAO empSearchDAO = EmpSearchingDAOImpl.getInstance();
	private static EmployeeSecretDAO empSecretDAO = EmployeeSecretDAOImpl.getInstance();

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException, DataAccessException {

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : accessToAuthority 시작");
		}

		EmpInfoTO bean = null;

		try {

			bean = checkEmpInfo(companyCode, workplaceCode, inputId);
			checkPassWord(companyCode, bean.getEmpCode(), inputPassWord);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : accessToAuthority 종료");
		}
		return bean;
	}

	private EmpInfoTO checkEmpInfo(String companyCode, String workplaceCode, String inputId)
			throws IdNotFoundException {

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : checkEmpInfo 시작");
		}

		EmpInfoTO bean = null;
		ArrayList<EmpInfoTO> empInfoTOList = null;

		try {

			empInfoTOList = empSearchDAO.getTotalEmpInfo(companyCode, workplaceCode, inputId);

			if (empInfoTOList.size() == 1) {

				for (EmpInfoTO e : empInfoTOList) {
					bean = e;
				}

			} else if (empInfoTOList.size() == 0) {
				throw new IdNotFoundException("입력된 정보에 해당하는 사원은 없습니다.");
			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : checkEmpInfo 종료");
		}
		return bean;
	}

	private void checkPassWord(String companyCode, String empCode, String inputPassWord)
			throws PwMissMatchException, PwNotFoundException {

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : checkPassWord 시작");
		}

		try {

			EmployeeSecretTO bean = empSecretDAO.selectUserPassWord(companyCode, empCode);

			StringBuffer userPassWord = new StringBuffer();
			if (bean != null) {
				userPassWord.append(bean.getUserPassword());

				// 회원ID 는 있으나 passWord Data 가 없는 경우
			} else if (bean == null || bean.getUserPassword().equals("") || bean.getUserPassword() == null) {
				throw new PwNotFoundException("비밀번호 정보를 찾을 수 없습니다.");
			}

			if (!inputPassWord.equals(userPassWord.toString())) {
				throw new PwMissMatchException("비밀번호가 가입된 정보와 같지 않습니다.");
			}

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("LogInApplicationServiceImpl : checkPassWord 종료");
		}

	}

}