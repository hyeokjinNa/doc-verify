package com.shy.docverify.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("docfile")
public class FileConfig {
	
	private String path;
	
	public FileConfig() {
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "FileConfig [path=" + path + "]";
	}
	
	
}
