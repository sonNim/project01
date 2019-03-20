package com.JHlogistics.logistics.sales.dao;

import com.JHlogistics.logistics.sales.to.DeliveryResultTO;

public interface DeliveryResultDAO {

	public void insertDeliveryResult(DeliveryResultTO TO);

	public void updateDeliveryResult(DeliveryResultTO TO);

	public void deleteDeliveryResult(DeliveryResultTO TO);
}
