package com.JHlogistics.common.servlet.mapper;

import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.JHlogistics.common.servlet.mapper.SimpleUrlHandlerMapping;
import com.JHlogistics.common.servlet.context.ApplicationContext;
import com.JHlogistics.common.servlet.controller.Controller;

public class SimpleUrlHandlerMapping {
	private HashMap<String, String> map;
	private static SimpleUrlHandlerMapping instance;
	public static SimpleUrlHandlerMapping getInstance(ServletContext application){
		if(instance == null){
			instance = new SimpleUrlHandlerMapping(application);
			System.out.println("		@ SimpleUrlHandlerMapping접근");
		}
			return instance;
	}
	private SimpleUrlHandlerMapping(ServletContext application){
		map=new HashMap<String, String>();
		String path = application.getInitParameter("urlmappingFile");
		String rPath = application.getRealPath(path);
		System.out.println("		@ 파일경로: "+path);
		System.out.println("		@ 실제경로: "+rPath);
		Properties properties = new Properties();
		try{
			properties.load(new FileReader(rPath));
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		Set<String> set = properties.stringPropertyNames();
		for(String key:set){
			String value = properties.getProperty(key);
			map.put(key,value);
		}
	}
	public Controller getController(ApplicationContext applicationContext, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String uri=request.getRequestURI();
		String contextPath = request.getContextPath();
		int sIndex = contextPath.length();
		String key = uri.substring(sIndex);
		String TOName = map.get(key);
		System.out.println("		@ 빈네임: "+TOName);
		return (Controller)(applicationContext.getTO(TOName));
	}
	
	
}
