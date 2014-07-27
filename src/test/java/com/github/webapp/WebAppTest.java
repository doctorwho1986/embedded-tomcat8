package com.github.webapp;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import com.github.filter.MyFilter;
import com.github.servlet.MyServlet;


public class WebAppTest {

	public static void main(String[] args) throws LifecycleException, ServletException {
		// TODO Auto-generated method stub
		String prjectPath = new File("").getAbsolutePath();
		System.out.println(prjectPath);
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		tomcat.setHostname("localhost");
		tomcat.setBaseDir(prjectPath+"/temp");

		Context context = tomcat.addWebapp("/webapp", prjectPath +"/src/test/resources/webapps/webapp");
		
		
		FilterDef filterDef = new FilterDef();
		filterDef.setFilter( new MyFilter());
		filterDef.setFilterName(MyFilter.class.toString());
		
		FilterMap filterMap = new FilterMap();
		filterMap.setFilterName(MyFilter.class.toString());
		filterMap.addURLPattern("/myfilter");
		context.addFilterDef(filterDef );
		
		context.addFilterMap(filterMap);
		
		
		Tomcat.addServlet(context, MyServlet.class.getName(), new MyServlet());
		context.addServletMapping("/myservlet", MyServlet.class.getName());
		tomcat.start();
		tomcat.getServer().await();
	}

}
