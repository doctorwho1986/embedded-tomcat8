package com.github.embedtomcat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;


public class EmbeddedTomcat {
	private Tomcat tomcat;
	private String basedir = new File("").getAbsolutePath();
	private Map<String, Context> context = new HashMap<String, Context>();
	public  EmbeddedTomcat(int port, String hostName) {
		tomcat = new Tomcat();
		tomcat.setPort(port);
		tomcat.setHostname(hostName);
		tomcat.setBaseDir(basedir +"/temp");
	}
	
	public void addWebApp(String contextPath, String baseDir) throws ServletException {
		Context context = tomcat.addWebapp(contextPath, this.basedir + baseDir);
		this.context.put(contextPath, context);
	}
	
	public void addFilter(String contextPath,String filterName,String urlPattern,Filter filter) {
		FilterDef filterDef = new FilterDef();
		filterDef.setFilter( filter);
		filterDef.setFilterName(filterName);
		
		FilterMap filterMap = new FilterMap();
		filterMap.setFilterName(filterName);
		filterMap.addURLPattern(urlPattern);
		if (!context.containsKey(contextPath)) {
			throw new IllegalArgumentException(contextPath + " is not exist ");
		}
		Context cxt = context.get(contextPath);
		cxt.addFilterDef(filterDef );
		
		cxt.addFilterMap(filterMap);
	}
	
	public void addServlet(String contextPath, String servletName,String urlPattern, Servlet servlet) {
		if (!context.containsKey(contextPath)) {
			throw new IllegalArgumentException(contextPath + " is not exist ");
		}
		Context cxt = context.get(contextPath);
		Tomcat.addServlet(cxt, servletName, servlet);
		cxt.addServletMapping(urlPattern, servletName);
	}
	
	public void startAndAwait(){
		try {
			tomcat.start();
		} catch (LifecycleException e) {
			throw new RuntimeException("tomcat start error");
		}
		tomcat.getServer().await();
	}
	
	public void stop(){
		try {
			tomcat.stop();
		} catch (LifecycleException e) {
			throw new RuntimeException("tomcat stop error");
		}
	}
}
