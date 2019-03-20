package com.JHlogistics.base.applicationService;

import java.util.ArrayList;

import com.JHlogistics.base.to.AddressTO;

public interface AddressApplicationService {
		
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber);
	
}
