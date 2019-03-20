package com.JHlogistics.authorityManager.serviceFacade;

import javax.servlet.ServletContext;

import com.JHlogistics.authorityManager.exception.IdNotFoundException;
import com.JHlogistics.authorityManager.exception.PwMissMatchException;
import com.JHlogistics.authorityManager.exception.PwNotFoundException;
import com.JHlogistics.hr.to.EmpInfoTO;

public interface AuthorityManagerServiceFacade {

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException;

	public String getUserMenuCode(String workplaceCode, String deptCode, String positionCode,
			ServletContext application);

}
