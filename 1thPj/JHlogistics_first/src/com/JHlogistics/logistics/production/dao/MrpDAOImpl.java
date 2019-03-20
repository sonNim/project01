package com.JHlogistics.logistics.production.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.db.DataSourceTransactionManager;
import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.logistics.production.to.MrpTO;
import com.JHlogistics.logistics.production.to.OpenMrpTO;

public class MrpDAOImpl implements MrpDAO {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(MrpDAOImpl.class);	
	
	// 싱글톤
	private static MrpDAO instance = new MrpDAOImpl();

	private MrpDAOImpl() {
	}

	public static MrpDAO getInstance() {
		
		if (logger.isDebugEnabled()) {
			logger.debug("@ MrpDAOImpl 객체접근");
		}
		
		return instance;
	}
	
	// 참조변수 선언
	private static DataSourceTransactionManager dataSourceTransactionManager = DataSourceTransactionManager
			.getInstance();

	
	
	public ArrayList<MrpTO> selectMrpList(String mrpGatheringStatusCondition) { 
		
		if (logger.isDebugEnabled()) {
			logger.debug("MrpDAOImpl : selectMrpList 시작");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<MrpTO> mrpList = new ArrayList<MrpTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
/*
SELECT * FROM MRP WHERE MRP_GATHERING_STATUS IS NULL
*/
			query.append("SELECT * FROM MRP ");
			
			if(mrpGatheringStatusCondition.equals("null") || mrpGatheringStatusCondition == null) {
				query.append(
						"WHERE MRP_GATHERING_STATUS IS NULL");
								
			} else if(mrpGatheringStatusCondition.equals("notNull")) {
				query.append(
						"WHERE MRP_GATHERING_STATUS IS NOT NULL");
				
			}
			
			pstmt = conn.prepareStatement(query.toString());

			rs = pstmt.executeQuery();

			MrpTO bean = null;
			
			while (rs.next()) {
				
				bean = new MrpTO();

				bean.setMrpNo(rs.getString("MRP_NO"));
				bean.setMpsNo(rs.getString("MPS_NO"));
				bean.setMrpGatheringNo(rs.getString("MRP_GATHERING_NO"));
				bean.setItemClassification(rs.getString("ITEM_CLASSIFICATION"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setOrderDate(rs.getString("ORDER_DATE"));
				bean.setRequiredDate(rs.getString("REQUIRED_DATE"));
				bean.setRequiredAmount(rs.getInt("REQUIRED_AMOUNT"));
				bean.setUnitOfMrp(rs.getString("UNIT_OF_MRP"));
				bean.setMrpGatheringStatus(rs.getString("MRP_GATHERING_STATUS"));

				mrpList.add(bean);
				
			}

			return mrpList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
		
	}
	
	
	
	public ArrayList<MrpTO> selectMrpList(String dateSearchCondtion, String startDate, String endDate) { 

		if (logger.isDebugEnabled()) {
			logger.debug("MrpDAOImpl : selectMrpList 시작");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<MrpTO> mrpList = new ArrayList<MrpTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
/*
SELECT * FROM MRP WHERE ( CASE ? WHEN 'orderDate' THEN
TO_DATE(ORDER_DATE, 'YYYY-MM-DD') WHEN 'requiredDate' THEN
TO_DATE(REQUIRED_DATE, 'YYYY-MM-DD') END ) 
BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD')
*/
			query.append(
					"SELECT * FROM MRP WHERE ( CASE ? WHEN 'orderDate' THEN\r\n" + 
					"TO_DATE(ORDER_DATE, 'YYYY-MM-DD') WHEN 'requiredDate' THEN\r\n" + 
					"TO_DATE(REQUIRED_DATE, 'YYYY-MM-DD') END ) \r\n" + 
					"BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD')");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, dateSearchCondtion);
			pstmt.setString(2, startDate);
			pstmt.setString(3, endDate);

			rs = pstmt.executeQuery();

			MrpTO bean = null;
			
			while (rs.next()) {
				
				bean = new MrpTO();

				bean.setMrpNo(rs.getString("MRP_NO"));
				bean.setMpsNo(rs.getString("MPS_NO"));
				bean.setMrpGatheringNo(rs.getString("MRP_GATHERING_NO"));
				bean.setItemClassification(rs.getString("ITEM_CLASSIFICATION"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setOrderDate(rs.getString("ORDER_DATE"));
				bean.setRequiredDate(rs.getString("REQUIRED_DATE"));
				bean.setRequiredAmount(rs.getInt("REQUIRED_AMOUNT"));
				bean.setUnitOfMrp(rs.getString("UNIT_OF_MRP"));
				bean.setMrpGatheringStatus(rs.getString("MRP_GATHERING_STATUS"));
				
				mrpList.add(bean);
				
			}

			return mrpList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
		
	}
	
	

	public ArrayList<MrpTO> selectMrpListAsMrpGatheringNo(String mrpGatheringNo) { 

		if (logger.isDebugEnabled()) {
			logger.debug("MrpDAOImpl : selectMrpListAsMrpGatheringNo 시작");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<MrpTO> mrpList = new ArrayList<MrpTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
/*
SELECT * FROM MRP WHERE MRP_GATHERING_NO =?
*/
			query.append(
					"SELECT * FROM MRP WHERE MRP_GATHERING_NO =?");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, mrpGatheringNo);

			rs = pstmt.executeQuery();

			MrpTO bean = null;
			
			while (rs.next()) {
				
				bean = new MrpTO();

				bean.setMrpNo(rs.getString("MRP_NO"));
				bean.setMpsNo(rs.getString("MPS_NO"));
				bean.setMrpGatheringNo(rs.getString("MRP_GATHERING_NO"));
				bean.setItemClassification(rs.getString("ITEM_CLASSIFICATION"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setOrderDate(rs.getString("ORDER_DATE"));
				bean.setRequiredDate(rs.getString("REQUIRED_DATE"));
				bean.setRequiredAmount(rs.getInt("REQUIRED_AMOUNT"));
				bean.setUnitOfMrp(rs.getString("UNIT_OF_MRP"));
				bean.setMrpGatheringStatus(rs.getString("MRP_GATHERING_STATUS"));
				
				mrpList.add(bean);
			}

			return mrpList;

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
		
	}
	
	
	public ArrayList<OpenMrpTO> openMrp(String mpsNoList) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpDAOImpl : openMrp 시작");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<OpenMrpTO> openMrpList = new ArrayList<OpenMrpTO>();

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();

/*

-- ? => "mrp번호,mrp번호, ..." / 'PS2018072703,PS2018072704,PS2018072705' 형식
-- MrpApplicationServiceImpl 에서 mrp 번호들이 담긴 ArrayList<string> mrpNoArr 을 매개변수로 받음
-- mrpNoArr.toString() => [mrp번호,mrp번호, ...] 형식으로 찍힘, "[" , "]" 만 지우면 됨
-- String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "") 하여 생성

WITH MPS_NO_STR AS ( SELECT ? FROM DUAL ) ,
 
-- 중요! : 위의 쉼표를 지우고 SELECT * FROM MPS_NO_STR; 
-- 이런 식으로 하나씩 실행해서 결과를 보면 이해가 쉬움

-- REGEXP_SUBSTR : 오라클 10g 이상에서 사용 가능한 정규표현식 함수
-- "mrp번호,mrp번호, ..." 형식의 문자열을 "," 로 잘라서 여러개의 행으로 만드는 기능

MPS_NO_LIST AS (
	SELECT TRIM( REGEXP_SUBSTR( (SELECT * FROM MPS_NO_STR ) ,'[^,]+', 1, LEVEL ) ) AS MPS_NO FROM DUAL 
	CONNECT BY REGEXP_SUBSTR( (SELECT * FROM MPS_NO_STR ) , '[^,]+', 1, LEVEL ) IS NOT NULL
) ,


-- 넘겨받은 mps번호에 해당하는 주생산계획 데이터만 가져오기

MPS_INFO AS (
	SELECT MPS_NO, ITEM_CODE AS MPS_ITEM_LIST, MPS_PLAN_AMOUNT, SCHEDULED_END_DATE 
	FROM MPS
	WHERE SCHEDULED_END_DATE IS NOT NULL
    AND MPS_NO IN ( SELECT MPS_NO FROM MPS_NO_LIST )  --여기서 위의 with 구문을 사용
) ,


-- 선택한 주생산계획의 여러 모품목들에 대해 동시에 BOM 전개 => BOM 정전개 쿼리와 거의 같음
-- 중요한 것은 반드시 모품목이 나와야 함 => 모품목의 손실율이 하위 품목들에 반영하기 때문..
-- BOM_NO 는 ROWNUM 으로 생성되는 일련번호로 실제 존재하는 컬럼 아님

-- CONNECT_BY_ROOT ITEM_CODE , SYS_CONNECT_BY_PATH ( ITEM_CODE, '/' ) , CONNECT_BY_ISLEAF
-- => 뇌라클 계층형쿼리 참조

BOM_INFO AS (
	SELECT BOM_NO, BOM_ITEM, PATH, BOM_LEVEL, ITEM_CODE, NET_AMOUNT, IS_LEAF, DESCRIPTION
	FROM
	( SELECT CONNECT_BY_ROOT ITEM_CODE || ' -' || TO_CHAR(ROWNUM, '000') AS BOM_NO, 
    	CONNECT_BY_ROOT ITEM_CODE AS BOM_ITEM, 
    	( LEVEL-1 ) AS BOM_LEVEL, SYS_CONNECT_BY_PATH ( ITEM_CODE, '/' ) AS PATH, ITEM_CODE, NET_AMOUNT,
    	( CASE CONNECT_BY_ISLEAF WHEN 0 THEN 'false' WHEN 1 THEN 'true' END ) AS IS_LEAF, B.DESCRIPTION
	FROM BOM B
	START WITH ITEM_CODE IN ( SELECT MPS_ITEM_LIST FROM MPS_INFO )  -- 선택한 주생산계획의 품목들
							AND PARENT_ITEM_CODE = 'NULL'   -- 부모품목이 없는 완제품/반제품 품목만 계층형 쿼리 시작
	CONNECT BY PARENT_ITEM_CODE = PRIOR ITEM_CODE
	ORDER SIBLINGS BY NO ) ) , 

-- B.ITEM_CODE = M.MPS_ITEM_LIST, 즉 주생산계획의 품목이면 주생산계획의 계획수량으로 바꿔치기
-- ITEM 테이블에서 품목명과 단위를, CODE_DETAI 테이블에서 품목분류를 가져옴

-- 중요! : 위의 BOM_TOTAL 을 셀프조인, INSTR( B1.PATH, B2.ITEM_CODE) > 0 ) 가 중요
-- B1. PATH 가 /DK-02/DK-AP02 이면 B2 에서는 ITEM_CODE 가 DK-02, DK-AP02 와 조인됨
-- 즉 자기 자신 + 자신의 부모품목과 조인됨 => BOM_TOTAL 로 수량, 손실율, 리드타임 을 계산함

BOM_TOTAL AS
( SELECT M.MPS_NO, B.BOM_NO AS BOM_NO, B.BOM_ITEM, B.PATH, B.BOM_LEVEL, 
	C.DETAIL_CODE_NAME AS ITEM_CLASSIFICATION, B.ITEM_CODE , I.ITEM_NAME, I.UNIT_OF_STOCK, 
	( CASE WHEN B.ITEM_CODE = M.MPS_ITEM_LIST THEN TO_NUMBER(M.MPS_PLAN_AMOUNT) 
				ELSE TO_NUMBER(B.NET_AMOUNT) END ) AS NET_AMOUNT, 
	I.LOSS_RATE, I.LEAD_TIME, M.SCHEDULED_END_DATE, B.IS_LEAF, B.DESCRIPTION
FROM MPS_INFO M, BOM_INFO B , ITEM I , CODE_DETAIL C
WHERE M.MPS_ITEM_LIST = B.BOM_ITEM 
	AND B.ITEM_CODE = I.ITEM_CODE 
	AND I.ITEM_CLASSIFICATION = C.DETAIL_CODE ),
	
BOM_JOIN AS 
( SELECT B1.MPS_NO, B1.BOM_NO, B1.BOM_ITEM, B1.BOM_LEVEL, B1.ITEM_CODE, B1.ITEM_NAME, B1.UNIT_OF_STOCK, 
	B2.ITEM_CODE AS SUB_ITEM_CODE, B2.ITEM_NAME AS SUB_ITEM_NAME, B2.NET_AMOUNT, B2.LOSS_RATE, 
    B2.LEAD_TIME, B2.SCHEDULED_END_DATE, B1.IS_LEAF, B1.DESCRIPTION
FROM BOM_TOTAL B1, BOM_TOTAL B2  -- 셀프조인
WHERE B1.MPS_NO = B2.MPS_NO   -- 셀프조인시 당연히 주생산계획이 같아야 하고
	AND INSTR( B1.PATH, B2.ITEM_CODE) > 0 ),  -- B2 에는 B1 의 품목 자신 + B1 의 부모 품목들이 조인됨


-- EXP( SUM( LN( NET_AMOUNT ) ) )  : 그룹핑 단위 내의 NET_AMOUNT 를 모두 곱하는 함수
-- 원리는 고등수학임 ( 자연로그, 지수) 
-- 계산결과가 소수점이 막 나오므로 ROUND 함수로 반올림

BOM_FOR_PLAN_AMOUNT AS 
( SELECT MPS_NO, BOM_NO, ROUND ( EXP( SUM( LN( NET_AMOUNT ) ) ) ) AS PLAN_AMOUNT
	FROM BOM_JOIN
	GROUP BY MPS_NO, BOM_NO ),


-- 손실율을 모두 곱함 : LOSS_RATE 가 null 이면 1 로 표시, 모품목의 손실율도 반영됨

BOM_FOR_LOSS_RATE AS 
( SELECT MPS_NO, BOM_NO, ROUND( EXP( SUM( LN( NVL( LOSS_RATE , 1 ) ) ) ) , 6 ) AS TOTAL_LOSS_RATE
	FROM BOM_JOIN
	GROUP BY MPS_NO, BOM_NO ),

-- 위에서 계산한 계획수량, 손실율 쿼리를 조인시켜서 필요수량 계산

BOM_FOR_REQUIRED_AMOUNT AS 
( SELECT MPS_NO, BOM_NO, PLAN_AMOUNT, TOTAL_LOSS_RATE, CACULATED_AMOUNT, CEIL(CACULATED_AMOUNT) AS REQUIRED_AMOUNT
	FROM 
	( SELECT B1.MPS_NO, B1.BOM_NO, B1.PLAN_AMOUNT, B2.TOTAL_LOSS_RATE,
    		TRUNC( B1.PLAN_AMOUNT * ( 1 + B2.TOTAL_LOSS_RATE ), 6 ) AS CACULATED_AMOUNT
	FROM BOM_FOR_PLAN_AMOUNT B1, BOM_FOR_LOSS_RATE B2
	WHERE B1.MPS_NO = B2.MPS_NO 
    	AND B1.BOM_NO = B2.BOM_NO ) ),

-- MPS_NO, BOM_ITEM, BOM_LEVEL 별로 더해져야할 추가 리드타임 계산
-- 분석함수 사용

PLUS_LEAD_TIME_BY_BOM_LEVEL AS 
( SELECT MPS_NO, BOM_ITEM, BOM_LEVEL, MAX_LEAD_TIME_BY_LEVEL,
		SUM(MAX_LEAD_TIME_OF_UPPER_LEVEL) 
        OVER ( PARTITION BY MPS_NO, BOM_ITEM ORDER BY BOM_LEVEL ) AS PLUS_LEAD_TIME
FROM 
	( SELECT MPS_NO, BOM_ITEM, BOM_LEVEL, MAX(LEAD_TIME) AS MAX_LEAD_TIME_BY_LEVEL, 
    	NVL( LAG(MAX(LEAD_TIME)) 
        OVER ( PARTITION BY MPS_NO, BOM_ITEM ORDER BY BOM_LEVEL ), 0 ) AS MAX_LEAD_TIME_OF_UPPER_LEVEL
	FROM BOM_JOIN
	GROUP BY MPS_NO, BOM_ITEM, BOM_LEVEL )
	ORDER BY BOM_LEVEL ) ,

-- 위에서 계산한 MPS_NO, BOM_ITEM, BOM_LEVEL 별 리드타임으로 발주일자, 소요일자 계산

BOM_FOR_ORDER_DATE AS 
( SELECT B.MPS_NO, B.BOM_NO, B.ITEM_CODE, ( B.LEAD_TIME + P.PLUS_LEAD_TIME ) AS TOTAL_LEAD_TIME, 
		TO_CHAR( TO_DATE(B.SCHEDULED_END_DATE, 'YYYY-MM-DD') - B.LEAD_TIME - P.PLUS_LEAD_TIME , 'YYYY-MM-DD') AS ORDER_DATE,
		TO_CHAR( TO_DATE(B.SCHEDULED_END_DATE, 'YYYY-MM-DD') - P.PLUS_LEAD_TIME , 'YYYY-MM-DD') AS REQUIRED_DATE
	FROM PLUS_LEAD_TIME_BY_BOM_LEVEL P, BOM_TOTAL B
	WHERE P.MPS_NO = B.MPS_NO 
    	AND P.BOM_ITEM = B.BOM_ITEM 
    	AND P.BOM_LEVEL = B.BOM_LEVEL ) ,

-- BOM_TOTAL 과 필요수량, 소요일자/발주일자 쿼리를 조인시킴

MRP_INFO AS 
( SELECT B1.MPS_NO, B1.BOM_NO, B1.ITEM_CLASSIFICATION, B1.ITEM_CODE, B1.ITEM_NAME, 
	B3.ORDER_DATE, B3.REQUIRED_DATE, B2.PLAN_AMOUNT, B2.TOTAL_LOSS_RATE, 
	B2.CACULATED_AMOUNT, B2.REQUIRED_AMOUNT, B1.UNIT_OF_STOCK AS UNIT_OF_MRP 
FROM BOM_TOTAL B1, BOM_FOR_REQUIRED_AMOUNT B2, BOM_FOR_ORDER_DATE B3
WHERE B1.BOM_NO = B2.BOM_NO 
    AND B1.MPS_NO = B2.MPS_NO 
    AND B2.BOM_NO = B3.BOM_NO 
    AND B2.MPS_NO = B3.MPS_NO
ORDER BY B1.MPS_NO, B1.BOM_NO )

-- 최종 결과
SELECT * FROM MRP_INFO

*/
			query.append("WITH MPS_NO_STR AS ( SELECT ? FROM DUAL ) ,\r\n" + "\r\n" + "MPS_NO_LIST AS (\r\n"
					+ "SELECT TRIM( REGEXP_SUBSTR( (SELECT * FROM MPS_NO_STR ) ,'[^,]+', 1, LEVEL ) ) AS MPS_NO FROM DUAL \r\n"
					+ "CONNECT BY REGEXP_SUBSTR( (SELECT * FROM MPS_NO_STR ) , '[^,]+', 1, LEVEL ) IS NOT NULL\r\n"
					+ ") ,\r\n" + "\r\n" + "MPS_INFO AS (\r\n"
					+ "SELECT MPS_NO, ITEM_CODE AS MPS_ITEM_LIST, MPS_PLAN_AMOUNT, SCHEDULED_END_DATE \r\n"
					+ "FROM MPS\r\n" + "WHERE SCHEDULED_END_DATE IS NOT NULL\r\n"
					+ "    AND MPS_NO IN ( SELECT MPS_NO FROM MPS_NO_LIST )\r\n" + ") ,\r\n" + "\r\n"
					+ "BOM_INFO AS (\r\n"
					+ "SELECT BOM_NO, BOM_ITEM, PATH, BOM_LEVEL, ITEM_CODE, NET_AMOUNT, IS_LEAF, DESCRIPTION\r\n"
					+ "FROM\r\n"
					+ "( SELECT CONNECT_BY_ROOT ITEM_CODE || ' -' || TO_CHAR(ROWNUM, '000') AS BOM_NO, \r\n"
					+ "    CONNECT_BY_ROOT ITEM_CODE AS BOM_ITEM, \r\n"
					+ "    ( LEVEL-1 ) AS BOM_LEVEL, SYS_CONNECT_BY_PATH ( ITEM_CODE, '/' ) AS PATH, ITEM_CODE, NET_AMOUNT,\r\n"
					+ "    ( CASE CONNECT_BY_ISLEAF WHEN 0 THEN 'false' WHEN 1 THEN 'true' END ) AS IS_LEAF, B.DESCRIPTION\r\n"
					+ "FROM BOM B\r\n" + "START WITH ITEM_CODE IN ( SELECT MPS_ITEM_LIST FROM MPS_INFO ) AND PARENT_ITEM_CODE = 'NULL' \r\n"
					+ "CONNECT BY PARENT_ITEM_CODE = PRIOR ITEM_CODE\r\n" + "ORDER SIBLINGS BY NO ) ) , \r\n" + "\r\n"
					+ "BOM_TOTAL AS\r\n"
					+ "( SELECT M.MPS_NO, B.BOM_NO AS BOM_NO, B.BOM_ITEM, B.PATH, B.BOM_LEVEL, \r\n"
					+ "    C.DETAIL_CODE_NAME AS ITEM_CLASSIFICATION, B.ITEM_CODE , I.ITEM_NAME, I.UNIT_OF_STOCK, \r\n"
					+ "    ( CASE WHEN B.ITEM_CODE = M.MPS_ITEM_LIST THEN TO_NUMBER(M.MPS_PLAN_AMOUNT) \r\n"
					+ "            ELSE TO_NUMBER(B.NET_AMOUNT) END ) AS NET_AMOUNT, \r\n"
					+ "    I.LOSS_RATE, I.LEAD_TIME, M.SCHEDULED_END_DATE, B.IS_LEAF, B.DESCRIPTION\r\n"
					+ "FROM MPS_INFO M, BOM_INFO B , ITEM I , CODE_DETAIL C\r\n"
					+ "WHERE M.MPS_ITEM_LIST = B.BOM_ITEM \r\n" + "    AND B.ITEM_CODE = I.ITEM_CODE \r\n"
					+ "    AND I.ITEM_CLASSIFICATION = C.DETAIL_CODE ),\r\n" + "\r\n" + "BOM_JOIN AS (\r\n"
					+ "SELECT B1.MPS_NO, B1.BOM_NO, B1.BOM_ITEM, B1.BOM_LEVEL, B1.ITEM_CODE, B1.ITEM_NAME, B1.UNIT_OF_STOCK, \r\n"
					+ "    B2.ITEM_CODE AS SUB_ITEM_CODE, B2.ITEM_NAME AS SUB_ITEM_NAME, B2.NET_AMOUNT, B2.LOSS_RATE, \r\n"
					+ "    B2.LEAD_TIME, B2.SCHEDULED_END_DATE, B1.IS_LEAF, B1.DESCRIPTION\r\n"
					+ "FROM BOM_TOTAL B1, BOM_TOTAL B2\r\n" + "WHERE B1.MPS_NO = B2.MPS_NO \r\n"
					+ "    AND INSTR( B1.PATH, B2.ITEM_CODE) > 0 ),\r\n" + "\r\n" + "BOM_FOR_PLAN_AMOUNT AS (\r\n"
					+ "SELECT MPS_NO, BOM_NO, ROUND ( EXP( SUM( LN( NET_AMOUNT ) ) ) ) AS PLAN_AMOUNT\r\n"
					+ "FROM BOM_JOIN\r\n" + "GROUP BY MPS_NO, BOM_NO ),\r\n" + "\r\n" + "BOM_FOR_LOSS_RATE AS (\r\n"
					+ "SELECT MPS_NO, BOM_NO, ROUND( EXP( SUM( LN( NVL( LOSS_RATE , 1 ) ) ) ) , 6 ) AS TOTAL_LOSS_RATE\r\n"
					+ "FROM BOM_JOIN\r\n" + "GROUP BY MPS_NO, BOM_NO ),\r\n" + "\r\n"
					+ "BOM_FOR_REQUIRED_AMOUNT AS (\r\n"
					+ "SELECT MPS_NO, BOM_NO, PLAN_AMOUNT, TOTAL_LOSS_RATE, CACULATED_AMOUNT, CEIL(CACULATED_AMOUNT) AS REQUIRED_AMOUNT\r\n"
					+ "FROM \r\n" + "( SELECT B1.MPS_NO, B1.BOM_NO, B1.PLAN_AMOUNT, B2.TOTAL_LOSS_RATE,\r\n"
					+ "    TRUNC( B1.PLAN_AMOUNT * ( 1 + B2.TOTAL_LOSS_RATE ), 6 ) AS CACULATED_AMOUNT\r\n"
					+ "FROM BOM_FOR_PLAN_AMOUNT B1, BOM_FOR_LOSS_RATE B2\r\n" + "WHERE B1.MPS_NO = B2.MPS_NO \r\n"
					+ "    AND B1.BOM_NO = B2.BOM_NO ) ),\r\n" + "\r\n" + "PLUS_LEAD_TIME_BY_BOM_LEVEL AS (\r\n"
					+ "SELECT MPS_NO, BOM_ITEM, BOM_LEVEL, MAX_LEAD_TIME_BY_LEVEL, \r\n"
					+ "    SUM(MAX_LEAD_TIME_OF_UPPER_LEVEL) \r\n"
					+ "        OVER ( PARTITION BY MPS_NO, BOM_ITEM ORDER BY BOM_LEVEL ) AS PLUS_LEAD_TIME\r\n"
					+ "FROM \r\n"
					+ "( SELECT MPS_NO, BOM_ITEM, BOM_LEVEL, MAX(LEAD_TIME) AS MAX_LEAD_TIME_BY_LEVEL, \r\n"
					+ "    NVL( LAG(MAX(LEAD_TIME)) \r\n"
					+ "        OVER ( PARTITION BY MPS_NO, BOM_ITEM ORDER BY BOM_LEVEL ), 0 ) AS MAX_LEAD_TIME_OF_UPPER_LEVEL\r\n"
					+ "FROM BOM_JOIN\r\n" + "GROUP BY MPS_NO, BOM_ITEM, BOM_LEVEL )\r\n" + "ORDER BY BOM_LEVEL ) ,\r\n"
					+ "\r\n" + "BOM_FOR_ORDER_DATE AS (\r\n"
					+ "SELECT B.MPS_NO, B.BOM_NO, B.ITEM_CODE, ( B.LEAD_TIME + P.PLUS_LEAD_TIME ) AS TOTAL_LEAD_TIME, \r\n"
					+ "    TO_CHAR( TO_DATE(B.SCHEDULED_END_DATE, 'YYYY-MM-DD') - B.LEAD_TIME - P.PLUS_LEAD_TIME , 'YYYY-MM-DD') AS ORDER_DATE,\r\n"
					+ "    TO_CHAR( TO_DATE(B.SCHEDULED_END_DATE, 'YYYY-MM-DD') - P.PLUS_LEAD_TIME , 'YYYY-MM-DD') AS REQUIRED_DATE\r\n"
					+ "FROM PLUS_LEAD_TIME_BY_BOM_LEVEL P, BOM_TOTAL B\r\n" + "WHERE P.MPS_NO = B.MPS_NO \r\n"
					+ "    AND P.BOM_ITEM = B.BOM_ITEM \r\n" + "    AND P.BOM_LEVEL = B.BOM_LEVEL ) ,\r\n" + "\r\n"
					+ "MRP_INFO AS (\r\n"
					+ "SELECT B1.MPS_NO, B1.BOM_NO, B1.ITEM_CLASSIFICATION, B1.ITEM_CODE, B1.ITEM_NAME, \r\n"
					+ "    B3.ORDER_DATE, B3.REQUIRED_DATE, B2.PLAN_AMOUNT, B2.TOTAL_LOSS_RATE, \r\n"
					+ "    B2.CACULATED_AMOUNT, B2.REQUIRED_AMOUNT, B1.UNIT_OF_STOCK AS UNIT_OF_MRP \r\n"
					+ "FROM BOM_TOTAL B1, BOM_FOR_REQUIRED_AMOUNT B2, BOM_FOR_ORDER_DATE B3\r\n"
					+ "WHERE B1.BOM_NO = B2.BOM_NO \r\n" + "    AND B1.MPS_NO = B2.MPS_NO \r\n"
					+ "    AND B2.BOM_NO = B3.BOM_NO \r\n" + "    AND B2.MPS_NO = B3.MPS_NO\r\n"
					+ "ORDER BY B1.MPS_NO, B1.BOM_NO )\r\n" + "\r\n" + "SELECT * FROM MRP_INFO");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, mpsNoList);

			rs = pstmt.executeQuery();

			OpenMrpTO bean = null;
			
			while (rs.next()) {

				bean = new OpenMrpTO();

				bean.setMpsNo(rs.getString("MPS_NO"));
				bean.setBomNo(rs.getString("BOM_NO"));
				bean.setItemClassification(rs.getString("ITEM_CLASSIFICATION"));
				bean.setItemCode(rs.getString("ITEM_CODE"));
				bean.setItemName(rs.getString("ITEM_NAME"));
				bean.setOrderDate(rs.getString("ORDER_DATE"));
				bean.setRequiredDate(rs.getString("REQUIRED_DATE"));
				bean.setPlanAmount(rs.getString("PLAN_AMOUNT"));
				bean.setTotalLossRate(rs.getString("TOTAL_LOSS_RATE"));
				bean.setCaculatedAmount(rs.getString("CACULATED_AMOUNT"));
				bean.setRequiredAmount(rs.getInt("REQUIRED_AMOUNT"));
				bean.setUnitOfMrp(rs.getString("UNIT_OF_MRP"));

				openMrpList.add(bean);
			}

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}

		return openMrpList;
	}
	
	
	public int selectMrpCount(String mrpRegisterDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpDAOImpl : selectMrpCount 시작");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();

			StringBuffer query = new StringBuffer();
			/*
			 * SELECT * FROM MRP WHERE INSTR(MRP_NO, REPLACE( ? , '-' , '' ) ) > 0
			 */
			query.append("SELECT * FROM MRP WHERE INSTR(MRP_NO, REPLACE( ? , '-' , '' ) ) > 0");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, mrpRegisterDate);

			rs = pstmt.executeQuery();

			TreeSet<Integer> intSet = new TreeSet<>();

			while (rs.next()) {
				String mrpNo = rs.getString("MRP_NO");

				// MRP 일련번호에서 마지막 3자리만 가져오기
				int no = Integer.parseInt(mrpNo.substring(mrpNo.length()-3, mrpNo.length()));

				intSet.add(no);
			}

			if (intSet.isEmpty()) {
				return 1;
			} else {
				return intSet.pollLast() + 1;   // 가장 높은 번호 + 1
			}

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
	}
	

	public void insertMrp(MrpTO bean) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("MrpDAOImpl : insertMrp 시작");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
/*
Insert into MRP
(MRP_NO, MPS_NO, MRP_GATHERING_NO, ITEM_CLASSIFICATION, 
ITEM_CODE, ITEM_NAME, UNIT_OR_MRP, 
REQUIRED_AMOUNT, LEAD_TIME, SCHEDULED_COMPLETE_DATE,
MRP_GATHERING_STATUS)
values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
*/
			query.append(
					"Insert into MRP\r\n" + 
					"(MRP_NO, MPS_NO, MRP_GATHERING_NO, ITEM_CLASSIFICATION, \r\n" + 
					"ITEM_CODE, ITEM_NAME, UNIT_OF_MRP, \r\n" + 
					"REQUIRED_AMOUNT, ORDER_DATE, REQUIRED_DATE,\r\n" + 
					"MRP_GATHERING_STATUS)\r\n" + 
					"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getMrpNo());
			pstmt.setString(2, bean.getMpsNo());
			pstmt.setString(3, bean.getMrpGatheringNo());
			pstmt.setString(4, bean.getItemClassification());
			pstmt.setString(5, bean.getItemCode());
			pstmt.setString(6, bean.getItemName());
			pstmt.setString(7, bean.getUnitOfMrp());
			pstmt.setInt(8, bean.getRequiredAmount());
			pstmt.setString(9, bean.getOrderDate());
			pstmt.setString(10, bean.getRequiredDate());
			pstmt.setString(11, bean.getMrpGatheringStatus());
			
			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
		
	}
	

	public void updateMrp(MrpTO bean) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpDAOImpl : updateMrp 시작");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			/*
			 * UPDATE MRP SET MPS_NO = ? , MRP_GATHERING_NO = ? , ITEM_CLASSIFICATION = ? ,
			 * ITEM_CODE = ? , ITEM_NAME = ? , UNIT_OF_MRP = ? , REQUIRED_AMOUNT = ? ,
			 * ORDER_DATE = ? , REQUIRED_DATE = ? , MRP_GATHERING_STATUS = ? WHERE
			 * MRP_NO = ?
			 */
			query.append("UPDATE MRP SET MPS_NO = ? , MRP_GATHERING_NO = ? ,\r\n"
					+ "ITEM_CLASSIFICATION = ? , ITEM_CODE = ? ,\r\n" + "ITEM_NAME = ? , UNIT_OR_MRP = ? ,\r\n"
					+ "REQUIRED_AMOUNT = ? , ORDER_DATE = ? ,\r\n"
					+ "REQUIRED_DATE = ? , MRP_GATHERING_STATUS = ? \r\n" + "WHERE MRP_NO = ?");
			
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getMpsNo());
			pstmt.setString(2, bean.getMrpGatheringNo());
			pstmt.setString(3, bean.getItemClassification());
			pstmt.setString(4, bean.getItemCode());
			pstmt.setString(5, bean.getItemName());
			pstmt.setString(6, bean.getUnitOfMrp());
			pstmt.setInt(7, bean.getRequiredAmount());
			pstmt.setString(8, bean.getOrderDate());
			pstmt.setString(9, bean.getRequiredDate());
			pstmt.setString(10, bean.getMrpGatheringStatus());
			pstmt.setString(11, bean.getMrpNo());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
		
	}
	


	public void changeMrpGatheringStatus(String mrpNo, String mrpGatheringNo, String mrpGatheringStatus) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpDAOImpl : changeMrpGatheringStatus 시작");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
/*
UPDATE MRP SET MRP_GATHERING_NO = ? , MRP_GATHERING_STATUS = ? WHERE MRP_NO = ?
*/
			query.append(
					"UPDATE MRP SET MRP_GATHERING_NO = ? , MRP_GATHERING_STATUS = ? WHERE MRP_NO = ?\r\n" + 
					"");
			
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, mrpGatheringNo);
			pstmt.setString(2, mrpGatheringStatus);
			pstmt.setString(3, mrpNo);
			
			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
		
	}

	
	
	public void deleteMrp(MrpTO bean) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("MrpDAOImpl : deleteMrp 시작");
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSourceTransactionManager.getConnection();
			StringBuffer query = new StringBuffer();
			
			query.append("DELETE FROM MRP WHERE MRP_NO = ?");
			
			pstmt = conn.prepareStatement(query.toString());

			pstmt.setString(1, bean.getMrpNo());

			rs = pstmt.executeQuery();

		} catch (Exception sqle) {

			throw new DataAccessException(sqle.getMessage());

		} finally {

			dataSourceTransactionManager.close(pstmt, rs);

		}
		
	}
	
}
