package com.JHlogistics.authorityManager.dao;

import java.util.HashMap;

import com.JHlogistics.authorityManager.to.UserMenuTO;

public interface UserMenuDAO {

	public HashMap<String, UserMenuTO> selectUserMenuCodeList(String workplaceCode, String deptCode, String positionCode);

	
}
