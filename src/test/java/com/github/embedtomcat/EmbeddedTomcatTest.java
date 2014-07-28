package com.github.embedtomcat;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

public class EmbeddedTomcatTest {

	@Test
	public void testEmbeddedTomcat() throws Throwable {
		int port = 8080;
		String hostName = "localhost";
		EmbeddedTomcat embeddedTomcat = new EmbeddedTomcat(port, hostName);
		String contextPath = "/tomcat";
		embeddedTomcat.addWebApp(contextPath, "/src/test/resources/webapps/webapp");
		embeddedTomcat.addFilter(contextPath, "f", "/f", new Filter() {

			public void init(FilterConfig filterConfig) throws ServletException {
				System.out.println("Filter init ");
				
			}

			public void doFilter(ServletRequest request,
					ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
				System.out.println("Filter doFilter ");
				
			}

			public void destroy() {
				System.out.println("Filter destroy ");
				
			}
			
		});
		
		embeddedTomcat.addServlet(contextPath, "s", "/f/s", new HttpServlet() {

			private static final long serialVersionUID = -8872478105856463152L;

			@Override
			protected void doGet(HttpServletRequest req,
					HttpServletResponse resp) throws ServletException,
					IOException {
				doPost(req, resp);
			}

			@Override
			protected void doPost(HttpServletRequest req,
					HttpServletResponse resp) throws ServletException,
					IOException {
				System.out.println("HttpServlet doPost" );
			}
			
			
		});
		embeddedTomcat.start();
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpUriRequest request = RequestBuilder.get().setUri("http://localhost:8080/tomcat/f").build();
		CloseableHttpResponse response = httpClient.execute(request);
		System.out.println(response.getStatusLine());
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
		embeddedTomcat.stop();
		
	}

	@Test
	public void testAddWebApp() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFilter() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddServlet() {
		fail("Not yet implemented");
	}

	@Test
	public void testStartAndAwait() {
		fail("Not yet implemented");
	}

	@Test
	public void testStop() {
		fail("Not yet implemented");
	}

}
