package com.github.webapp;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;


public class WebAppTest {

	public static void main(String[] args) throws LifecycleException, ServletException {
		// TODO Auto-generated method stub
		String prjectPath = new File("").getAbsolutePath();
		System.out.println(prjectPath);
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		tomcat.setHostname("localhost");
		tomcat.setBaseDir(prjectPath+"/temp");

		tomcat.addWebapp("/webapp", prjectPath +"/src/test/resources/webapps/webapp");
		tomcat.start();
		tomcat.getServer().await();
	}

}
