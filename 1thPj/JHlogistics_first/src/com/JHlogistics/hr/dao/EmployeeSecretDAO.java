package com.JHlogistics.hr.dao;

import java.util.ArrayList;

import com.JHlogistics.hr.to.EmployeeSecretTO;

public interface EmployeeSecretDAO {

	public ArrayList<EmployeeSecretTO> selectEmployeeSecretList(String companyCode, String empCode);

	public EmployeeSecretTO selectUserPassWord(String companyCode, String empCode);

	public void insertEmployeeSecret(EmployeeSecretTO TO);
	
	public int selectUserPassWordCount(String companyCode, String empCode);

}
