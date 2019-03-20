package com.JHlogistics.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.exception.DataAccessException;
import com.JHlogistics.logistics.production.dao.MpsDAO;
import com.JHlogistics.logistics.production.dao.MpsDAOImpl;
import com.JHlogistics.logistics.production.dao.MrpDAO;
import com.JHlogistics.logistics.production.dao.MrpDAOImpl;
import com.JHlogistics.logistics.production.dao.MrpGatheringDAO;
import com.JHlogistics.logistics.production.dao.MrpGatheringDAOImpl;
import com.JHlogistics.logistics.production.to.MrpTO;
import com.JHlogistics.logistics.production.to.MrpGatheringTO;
import com.JHlogistics.logistics.production.to.OpenMrpTO;

public class MrpApplicationServiceImpl implements MrpApplicationService {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(MrpApplicationServiceImpl.class);

	// 싱글톤
	private static MrpApplicationService instance = new MrpApplicationServiceImpl();

	private MrpApplicationServiceImpl() {
	}

	public static MrpApplicationService getInstance() {

		if (logger.isDebugEnabled()) {
			logger.debug("@ MrpApplicationServiceImpl 객체접근");
		}

		return instance;
	}

	// DAO 참조변수 선언
	private static MpsDAO mpsDAO = MpsDAOImpl.getInstance();
	private static MrpDAO mrpDAO = MrpDAOImpl.getInstance();
	private static MrpGatheringDAO mrpGatheringDAO = MrpGatheringDAOImpl.getInstance();

	public ArrayList<MrpTO> searchMrpList(String mrpGatheringStatusCondition) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : searchMrpList 시작");
		}

		ArrayList<MrpTO> mrpList = null;

		try {

			mrpList = mrpDAO.selectMrpList(mrpGatheringStatusCondition);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : searchMrpList 종료");
		}
		return mrpList;
	}

	public ArrayList<MrpTO> searchMrpList(String dateSearchCondtion, String startDate, String endDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : searchMrpList 시작");
		}

		ArrayList<MrpTO> mrpList = null;

		try {

			mrpList = mrpDAO.selectMrpList(dateSearchCondtion, startDate, endDate);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : searchMrpList 종료");
		}

		return mrpList;

	}

	public ArrayList<MrpTO> searchMrpListAsMrpGatheringNo(String mrpGatheringNo) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : searchMrpListAsMrpGatheringNo 시작");
		}

		ArrayList<MrpTO> mrpList = null;

		try {

			mrpList = mrpDAO.selectMrpListAsMrpGatheringNo(mrpGatheringNo);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : searchMrpListAsMrpGatheringNo 종료");
		}
		return mrpList;

	}

	public ArrayList<MrpGatheringTO> searchMrpGatheringList(String dateSearchCondtion, String startDate,
			String endDate) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : searchMrpGatheringList 시작");
		}

		ArrayList<MrpGatheringTO> mrpGatheringList = null;

		try {

			mrpGatheringList = mrpGatheringDAO.selectMrpGatheringList(dateSearchCondtion, startDate, endDate);

			for(MrpGatheringTO bean : mrpGatheringList) 	{
				
				bean.setMrpTOList(  mrpDAO.selectMrpListAsMrpGatheringNo( bean.getMrpGatheringNo()) );
			
			}
			
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : searchMrpGatheringList 종료");
		}
		return mrpGatheringList;

	}

	public HashMap<String, Object> openMrp(ArrayList<String> mpsNoArr) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : openMrp 시작");
		}

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			String mpsNoList = mpsNoArr.toString().replace("[", "").replace("]", "");

			ArrayList<OpenMrpTO> openMrpList = mrpDAO.openMrp(mpsNoList);

			resultMap.put("openMrpList", openMrpList);

			/*
			 * for(OpenMrpTO TO : openMrpList) {
			 * 
			 * 
			 * }
			 */

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : openMrp 종료");
		}
		return resultMap;

	}

	public HashMap<String, Object> registerMrp(String mrpRegisterDate, ArrayList<MrpTO> newMrpList) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : registerMrp 시작");
		}

		HashMap<String, Object> resultMap = null;

		try {

			int i = mrpDAO.selectMrpCount(mrpRegisterDate);

			// 새로운 MRP 일련번호 양식 생성 : 등록일자 '2018-01-01' => 일련번호 'RP20180101-'
			StringBuffer newMrpNo = new StringBuffer();
			newMrpNo.append("RP");
			newMrpNo.append(mrpRegisterDate.replace("-", ""));
			newMrpNo.append("-");

			// 주생산계획번호를 담을 HashSet : 중복된 번호도 하나만 입력됨
			HashSet<String> mpsNoList = new HashSet<>();

			for (MrpTO bean : newMrpList) {

				bean.setMrpNo(newMrpNo.toString() + String.format("%03d", i++));
				bean.setStatus("INSERT");
				mpsNoList.add(bean.getMpsNo());

			}

			// 새로운 MRP 빈을 batchListProcess 로 테이블에 저장
			resultMap = batchMrpListProcess(newMrpList);

			// 생성된 MRP 일련번호를 저장할 TreeSet
			TreeSet<String> mrpNoSet = new TreeSet<>();

			// "INSERT" 목록에 새로 생성된 MRP 일련번호들이 있음, ArrayList 로 형변환
			@SuppressWarnings("unchecked")
			ArrayList<String> mrpNoArr = (ArrayList<String>) resultMap.get("INSERT");

			for (String mrpNo : mrpNoArr) {
				mrpNoSet.add(mrpNo); // ArrayList 의 MRP 일련번호들을 TreeSet 에 저장

			}

			resultMap.put("firstMrpNo", mrpNoSet.pollFirst()); // 최초 MRP 일련번호를 결과 Map 에 저장
			resultMap.put("lastMrpNo", mrpNoSet.pollLast()); // 마지막 MRP 일련번호 결과 Map 에 저장

			// MPS 테이블에서 해당 mpsNo 의 MRP 적용상태를 "Y" 로 변경
			for (String mpsNo : mpsNoList) {

				mpsDAO.changeMrpApplyStatus(mpsNo, "Y");

			}

			// MRP 적용상태를 "Y" 로 변경한 주생산계획번호들을 결과 Map 에 저장
			resultMap.put("changeMrpApplyStatus", mpsNoList.toString().replace("[", "").replace("]", ""));

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : registerMrp 종료");
		}
		return resultMap;
	}

	public HashMap<String, Object> batchMrpListProcess(ArrayList<MrpTO> mrpTOList) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : batchMrpListProcess 시작");
		}

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			for (MrpTO bean : mrpTOList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					// dao 파트 시작
					mrpDAO.insertMrp(bean);
					// dao 파트 끝

					insertList.add(bean.getMrpNo());

					break;

				case "UPDATE":

					// dao 파트 시작
					mrpDAO.updateMrp(bean);
					// dao 파트 끝

					updateList.add(bean.getMrpNo());

					break;

				case "DELETE":

					// dao 파트 시작
					mrpDAO.deleteMrp(bean);
					// dao 파트 끝

					deleteList.add(bean.getMrpNo());

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : batchMrpListProcess 종료");
		}

		return resultMap;
	}

	public ArrayList<MrpGatheringTO> getMrpGathering(ArrayList<String> mrpNoArr) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : getMrpGathering 시작");
		}

		ArrayList<MrpGatheringTO> mrpGatheringList = null;

		try {

			// mrp번호 배열 [mrp번호,mrp번호, ...] => "mrp번호,mrp번호, ..." 형식의 문자열로 변환
			String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");

			mrpGatheringList = mrpGatheringDAO.getMrpGathering(mrpNoList);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : getMrpGathering 종료");
		}
		return mrpGatheringList;

	}

	public HashMap<String, Object> registerMrpGathering(String mrpGatheringRegisterDate,
			ArrayList<MrpGatheringTO> newMrpGatheringList, HashMap<String, String> mrpNoAndItemCodeMap) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : registerMrpGathering 시작");
		}

		HashMap<String, Object> resultMap = null;

		try {

			// 소요량 취합일자로 새로운 소요량 취합번호 확인
			int i = mrpGatheringDAO.selectMrpGatheringCount(mrpGatheringRegisterDate);

			/*
			 * ( itemCode : 새로운 mrpGathering 일련번호 ) 키/값 Map => itemCode 로 mrpNo 와
			 * mrpGatheringNo 를 매칭
			 */
			HashMap<String, String> itemCodeAndMrpGatheringNoMap = new HashMap<>();

			// 새로운 mrpGathering 일련번호 양식 생성 : 등록일자 '2018-01-01' => 일련번호 'MG20180101-'
			StringBuffer newMrpGatheringNo = new StringBuffer();
			newMrpGatheringNo.append("MG");
			newMrpGatheringNo.append(mrpGatheringRegisterDate.replace("-", ""));
			newMrpGatheringNo.append("-");

			// 새로운 mrpGathering 빈에 일련번호 입력 / status 를 "INSERT" 로 변경
			for (MrpGatheringTO bean : newMrpGatheringList) {

				bean.setMrpGatheringNo(newMrpGatheringNo.toString() + String.format("%03d", i++));
				bean.setStatus("INSERT");

				// mrpGathering 빈의 itemCode 와 mrpGatheringNo 를 map 에 저장
				itemCodeAndMrpGatheringNoMap.put(bean.getItemCode(), bean.getMrpGatheringNo());

			}

			// 새로운 mrpGathering 빈을 batchListProcess 로 테이블에 저장, 결과 Map 반환
			resultMap = batchMrpGatheringListProcess(newMrpGatheringList);

			// 생성된 mrp 일련번호를 저장할 TreeSet
			TreeSet<String> mrpGatheringNoSet = new TreeSet<>();

			// "INSERT_LIST" 목록에 "itemCode - mrpGatheringNo" 키/값 Map 이 있음
			@SuppressWarnings("unchecked")
			HashMap<String, String> mrpGatheringNoList = (HashMap<String, String>) resultMap.get("INSERT_MAP");

			for (String mrpGatheringNo : mrpGatheringNoList.values()) {
				mrpGatheringNoSet.add(mrpGatheringNo); // ArrayList 의 mrpGathering 일련번호들을 TreeSet 에 저장

			}

			resultMap.put("firstMrpGatheringNo", mrpGatheringNoSet.pollFirst()); // 최초 mrpGathering 일련번호를 결과 Map 에 저장
			resultMap.put("lastMrpGatheringNo", mrpGatheringNoSet.pollLast()); // 마지막 mrpGathering 일련번호를 결과 Map 에 저장

			// MRP 테이블에서 해당 mrpNo 의 mrpGatheringNo 저장, 소요량취합 적용상태를 "Y" 로 변경
			// itemCode 로 mrpNo 와 mrpGatheringNo 를 매칭시킨다
			for (String mrpNo : mrpNoAndItemCodeMap.keySet()) {

				String itemCode = mrpNoAndItemCodeMap.get(mrpNo);
				String mrpGatheringNo = itemCodeAndMrpGatheringNoMap.get(itemCode);
				mrpDAO.changeMrpGatheringStatus(mrpNo, mrpGatheringNo, "Y");

			}

			// MRP 적용상태를 "Y" 로 변경한 MRP 번호들을 결과 Map 에 저장
			resultMap.put("changeMrpGatheringStatus",
					mrpNoAndItemCodeMap.keySet().toString().replace("[", "").replace("]", ""));

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : registerMrpGathering 종료");
		}
		return resultMap;
	}

	public HashMap<String, Object> batchMrpGatheringListProcess(ArrayList<MrpGatheringTO> mrpGatheringTOList) {

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : batchMrpGatheringListProcess 시작");
		}

		HashMap<String, Object> resultMap = new HashMap<>();

		try {

			HashMap<String, String> insertListMap = new HashMap<>(); // "itemCode : mrpGatheringNo" 의 맵
			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			for (MrpGatheringTO bean : mrpGatheringTOList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					mrpGatheringDAO.insertMrpGathering(bean);

					insertList.add(bean.getMrpGatheringNo());

					insertListMap.put(bean.getItemCode(), bean.getMrpGatheringNo());

					break;

				case "UPDATE":

					mrpGatheringDAO.updateMrpGathering(bean);

					updateList.add(bean.getMrpGatheringNo());

					break;

				case "DELETE":

					mrpGatheringDAO.deleteMrpGathering(bean);

					deleteList.add(bean.getMrpGatheringNo());

					break;

				}

			}

			resultMap.put("INSERT_MAP", insertListMap);
			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw e;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("MrpApplicationServiceImpl : batchMrpGatheringListProcess 종료");
		}
		return resultMap;
	}

}
