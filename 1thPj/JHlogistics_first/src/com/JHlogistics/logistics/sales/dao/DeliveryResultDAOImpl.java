package com.JHlogistics.logistics.sales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.db.DataSourceTransactionManager;
import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.logistics.sales.to.DeliveryResultTO;

public class DeliveryResultDAOImpl implements DeliveryResultDAO {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(DeliveryResultDAOImpl.class);

	// 싱글톤
	private static DeliveryResultDAO instance = new DeliveryResultDAOImpl();

	private DeliveryResultDAOImpl() {
	}

	public static DeliveryResultDAO getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ DeliveryResultDAOImpl 객체접근");
		}

		return instance;
	}

	// 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();

	public void insertDeliveryResult(DeliveryResultTO bean) {

		if (logger.isDebugEnabled()) {
			logger.debug("DeliveryResultDAOImpl : insertDeliveryResult 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * Insert into Delivery_Result (DELIVERY_NO, CONTRACT_DETAIL_NO, WAREHOUSE_CODE,
			 * ITEM_CODE, ITEM_NAME, UNIT_OF_DELIVERY_RESULT, DELIVERY_DATE,
			 * DELIVERY_AMOUNT, DESCRIPTION) values (?, ?, ?, ?, ?, ?, ?, ?, ?)
			 */
			query.append("Insert into Delivery_Result\r\n" + "(DELIVERY_NO, CONTRACT_DETAIL_NO, WAREHOUSE_CODE,\r\n"
					+ "ITEM_CODE, ITEM_NAME, UNIT_OF_DELIVERY_RESULT,\r\n"
					+ "DELIVERY_DATE, DELIVERY_AMOUNT, DESCRIPTION) \r\n" + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getDeliveryNo());
			pstmt.setString(2, bean.getContractDetailNo());
			pstmt.setString(3, bean.getWarehouseCode());
			pstmt.setString(4, bean.getItemCode());
			pstmt.setString(5, bean.getItemName());
			pstmt.setString(6, bean.getUnitOfDeliveryResult());
			pstmt.setString(7, bean.getDeliveryDate());
			pstmt.setInt(8, bean.getDeliveryAmount());
			pstmt.setString(9, bean.getDescription());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}

	}

	public void updateDeliveryResult(DeliveryResultTO bean) {

		if (logger.isDebugEnabled()) {
			logger.debug("DeliveryResultDAOImpl : updateDeliveryResult 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * UPDATE Delivery_Result SET CONTRACT_DETAIL_NO = ? , WAREHOUSE_CODE = ?,
			 * ITEM_CODE = ? , ITEM_NAME = ? , UNIT_OF_DELIVERY_RESULT = ?, DELIVERY_DATE =
			 * ? , DELIVERY_AMOUNT = ? , DESCRIPTION = ? WHERE DELIVERY_NO = ?
			 */
			query.append("UPDATE Delivery_Result SET \r\n" + "CONTRACT_DETAIL_NO = ? , WAREHOUSE_CODE = ?,\r\n"
					+ "ITEM_CODE = ? , ITEM_NAME = ? , UNIT_OF_DELIVERY_RESULT = ?,\r\n"
					+ "DELIVERY_DATE = ? , DELIVERY_AMOUNT = ? , DESCRIPTION = ?\r\n" + "WHERE DELIVERY_NO = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getContractDetailNo());
			pstmt.setString(2, bean.getWarehouseCode());
			pstmt.setString(3, bean.getItemCode());
			pstmt.setString(4, bean.getItemName());
			pstmt.setString(5, bean.getUnitOfDeliveryResult());
			pstmt.setString(6, bean.getDeliveryDate());
			pstmt.setInt(7, bean.getDeliveryAmount());
			pstmt.setString(8, bean.getDescription());
			pstmt.setString(9, bean.getDeliveryNo());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}

	}

	public void deleteDeliveryResult(DeliveryResultTO bean) {

		if (logger.isDebugEnabled()) {
			logger.debug("DeliveryResultDAOImpl : deleteDeliveryResult 시작");
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();

			query.append("DELETE FROM DELIVERY_RESULT WHERE DELIVERY_NO = ?");
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getDeliveryNo());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}

}
