package com.mvc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mvc.bo.LiliProject;
import com.mvc.bo.ServerResponse;

public interface IMvcService {
	List<LiliProject> readXml();
	
	String doUpload(MultipartFile file);
	
	ServerResponse<String> readExcelFromDoc(MultipartFile file);
}
