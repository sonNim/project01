package com.JHlogistics.authorityManager.applicationService;

import com.JHlogistics.authorityManager.exception.IdNotFoundException;
import com.JHlogistics.authorityManager.exception.PwMissMatchException;
import com.JHlogistics.authorityManager.exception.PwNotFoundException;
import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.hr.to.EmpInfoTO;

public interface LogInApplicationService {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException, DataAccessException;

}
