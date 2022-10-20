package com.shy.docverify.service;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shy.docverify.config.FileConfig;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	
	@Autowired
	private FileConfig config;
	
	
	@Override
	public String selectUploadPath() {
		return config.getPath();
	}
	
	@Override
	public byte[] selectOneFile(String filename) {
		
		URL resources = getClass().getClassLoader().getResource(this.selectUploadPath());
		String filePath = resources.getFile()+File.separator+filename;
		byte[] fileByte = null;
		File downloadFile = new File(filePath);
		try {
			fileByte = FileUtils.readFileToByteArray(downloadFile);
			
		} catch (Exception e) {
			throw new RuntimeException("Could Not Read The File!");
		}
		
		return fileByte;
	}
}
