package com.shy.docverify.util;



import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConvertFile {

	public File multipartToFile(MultipartFile mFile) {
		File file = new File(mFile.getOriginalFilename());
		
		try {
			
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(mFile.getBytes());
			fos.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return file;
	}
}
