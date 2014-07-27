package com.github.host;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


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
	
	public static void main(String[] args) {
		Hosts.addHost("127.0.0.1", "www.cui.com");
	}
}
