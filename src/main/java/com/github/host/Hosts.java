package com.github.host;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

public class Hosts {
	public static void addHost(String ip,String hostName) {
		if (Strings.isNullOrEmpty(ip.trim())) {
			throw new IllegalArgumentException("ip is blank");
		}
		
		if (Strings.isNullOrEmpty(hostName.trim())) {
			throw new IllegalArgumentException("host name is blank");
		}
		
		String fileName ="";
		if ("linux".equalsIgnoreCase(System.getProperty("os.name"))) {
			fileName = "/etc/hosts";
		}else {
			fileName = "C:/WINDOWS/system32/drivers/etc//hosts";
		}
		String temp = ip.trim()+hostName.trim();
		File file = new File(hostName);
		Path path = Paths.get(fileName);
		if (!java.nio.file.Files.isReadable(path) || !java.nio.file.Files.isWritable(path)) {
			throw new IllegalAccessError(fileName + "  can't read or write");
		}
		try {
			List<String> readLines = FileUtils.readLines(file, "utf-8");
			for (String host : readLines) {
				if (host.startsWith("#")) {
					continue;
				}
				if (temp.equalsIgnoreCase(StringUtils.deleteWhitespace(host))) {
					return;
				}
			}
			
			
			
			List<String> lines = new ArrayList<String>();
			lines.add(ip.trim() + "     " + hostName.trim());
			FileUtils.writeLines(file, lines, null, true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalAccessError(fileName + "access error");
		}
	}
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, UnknownHostException {
		Class<InetAddress> klass = InetAddress.class;
		String cacheName = "addressCache";
	    Field acf = klass.getDeclaredField(cacheName);
	    acf.setAccessible(true);
	    Object addressCache = acf.get(null);
	    Class cacheKlass = addressCache.getClass();
	    Field cf = cacheKlass.getDeclaredField("cache");
	    
	    cf.setAccessible(true);
	    Map<String, Object> cache = (Map<String, Object>) cf.get(addressCache);
	    InetAddress address2 = InetAddress.getByAddress("www.cui8.com", new byte[]{(byte)127,0,0,1} );
	    cache.put("www.cui8.com", address2);
	    System.out.println(cache.size());
	    for (Map.Entry<String, Object> hi : cache.entrySet()) {
//	        Object cacheEntry = hi.getValue();
//	        Class cacheEntryKlass = cacheEntry.getClass();
//	        Field expf = cacheEntryKlass.getDeclaredField("expiration");
//	        expf.setAccessible(true);
//	        long expires = (Long) expf.get(cacheEntry);
//	 
//	        Field af = cacheEntryKlass.getDeclaredField("address");
//	        af.setAccessible(true);
//	        InetAddress[] addresses = (InetAddress[]) af.get(cacheEntry);
//	        List<String> ads = new ArrayList<String>(addresses.length);
//	        for (InetAddress address : addresses) {
//	            ads.add(address.getHostAddress());
//	        }
//	 
//	        System.out.println(hi.getKey() + " "+new Date(expires) +" " +ads);
	    }
	}
}
