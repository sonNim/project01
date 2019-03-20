package com.JHlogistics.common.servlet.view;

import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.JHlogistics.common.servlet.view.InternalResourceViewResolver;
import com.JHlogistics.common.servlet.ModelAndView;

public class InternalResourceViewResolver {
	private String prefix, postfix;
	private static InternalResourceViewResolver instance;
	public static InternalResourceViewResolver getInstance(ServletContext application){
		if(instance ==null){
			instance = new InternalResourceViewResolver(application);
			System.out.println("		@ InternalResourceViewResolver접근");
		}
		return instance;
	}
	private InternalResourceViewResolver(ServletContext application){
		String path = application.getInitParameter("pathFile");
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
		prefix = properties.getProperty("prefix");
		postfix = properties.getProperty("postfix");
	}
	public void resolverView(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String viewName = modelAndView.getViewName();
		if(viewName.lastIndexOf("redirect:")==-1){
			System.out.println("		@ 리다이렉트 있음");
			HashMap<String,Object> modelObject = modelAndView.getModelObject();
			if(modelObject!=null){
				for(String key:modelObject.keySet()){
					request.setAttribute(key, modelObject.get(key));
					System.out.println("		@ 모델오브젝트의 Key: "+key);
				}
			}
			RequestDispatcher rd = request.getRequestDispatcher(prefix+viewName+postfix);
			System.out.println("		@ 뷰네임: "+viewName);
			rd.forward(request, response);
		}else{
			System.out.println("		@ 리다이렉트 없음");
			int index = viewName.indexOf(":");
			String path = viewName.substring(index+1);
			response.sendRedirect(path);
			System.out.println("		@ 패스: "+path);
		}
	}	
}
