package com.shy.docverify.util;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConvertUtil {

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
	
	public String convertSqlToString(String classPath) {
		String result = "";
		
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("classpath:" + classPath);
		
		try (Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8")) {
			 result = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
		 
		return result;
	}
}
