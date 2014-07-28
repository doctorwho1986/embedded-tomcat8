package com.github.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class MyFilter implements Filter {


	
	

	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("------init  MyFilter");
		
	}



	public void destroy() {
		System.out.println("------destroy  MyFilter");
		
	}



	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("------doFilter  MyFilter");
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		Enumeration<String> headerNames = requestAttributes.getRequest().getHeaderNames();
		HttpServletResponse response2 = (HttpServletResponse) response;
		while (headerNames.hasMoreElements()) {
			String string = (String) headerNames.nextElement();
			System.out.println(string + "----" + requestAttributes.getRequest().getHeader(string));
			response2.setHeader(string, requestAttributes.getRequest().getHeader(string));
		}
		chain.doFilter(request, response2);
		
	}

}
