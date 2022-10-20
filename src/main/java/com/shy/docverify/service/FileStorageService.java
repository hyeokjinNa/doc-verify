package com.shy.docverify.service;


public interface FileStorageService {
	
	public String selectUploadPath();
	public byte[] selectOneFile(String filename);
	
}
