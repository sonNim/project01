package com.JHlogistics.base.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.JHlogistics.common.servlet.ModelAndView;
import com.JHlogistics.common.servlet.controller.MultiActionController;
import com.JHlogistics.common.sl.ServiceLocator;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ReportController extends MultiActionController {

	// SLF4J logger
	private static Logger logger = LoggerFactory.getLogger(ReportController.class);

	// iReport jrxml , jasper 파일저장 경로
	private static String iReportFolderPath = "D:\\firstProject\\JHlogistics\\WebContent\\resources\\iReportForm\\";

	// 커넥션 풀 이름
	private static String connectionPoolName = "jdbc/logi";

	private ModelAndView modelAndView = null;

	public ModelAndView estimateReport(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("ReportController : estimateReport 시작");
		}

		HashMap<String, Object> parameters = new HashMap<>();

		// 레포트 이름
		String reportName = "Estimate.jrxml";

		try {
			response.setCharacterEncoding("utf-8");
			String orderDraftNo = request.getParameter("orderDraftNo");
			parameters.put("orderDraftNo", orderDraftNo);

			DataSource dataSource = ServiceLocator.getInstance().getDataSource(connectionPoolName);
			Connection conn = dataSource.getConnection();
			InputStream inputStream = new FileInputStream(iReportFolderPath + reportName);

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			out.println();
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ReportController : estimateReport 종료");
		}
		return modelAndView;
	}

	public ModelAndView contractReport(HttpServletRequest request, HttpServletResponse response) {

		if (logger.isDebugEnabled()) {
			logger.debug("ReportController : contractReport 시작");
		}

		HashMap<String, Object> parameters = new HashMap<>();

		// 레포트 이름
		String reportName = "Contract.jrxml";

		try {
			response.setCharacterEncoding("utf-8");
			String orderDraftNo = request.getParameter("orderDraftNo");
			parameters.put("orderDraftNo", orderDraftNo);

			DataSource dataSource = ServiceLocator.getInstance().getDataSource(connectionPoolName);
			Connection conn = dataSource.getConnection();
			InputStream inputStream = new FileInputStream(iReportFolderPath + reportName);

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			out.println();
			out.flush();

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ReportController : contractReport 종료");
		}
		return modelAndView;
	}
}
