package com.shy.docverify.util;



import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConvertFile {

	public File multipartToFile(MultipartFile mFile) {
		File file = new File(mFile.getOriginalFilename());
		
		try {
			mFile.transferTo(file);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return file;
	}
}
