package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.bo.LiliProject;
import com.mvc.bo.ServerResponse;
import com.mvc.service.IMvcService;


@Controller
@RequestMapping("/courses")
public class coursesController {
	
	@Autowired
	private IMvcService iMvcService;
	
	@RequestMapping("/upload")
	public String upload(){
		return "file";
	}
	
	@RequestMapping("/uploadDocx")
	public String uploadDocx(){
		return "DocxFile";
	}
	
	@RequestMapping("/doUpload")
	public String doUpload(@RequestParam("file")MultipartFile file) {
		String result = iMvcService.doUpload(file);
		return result;
	}
	
	@RequestMapping("/readXml")
	public String readXml(HttpServletRequest request) {
		List<LiliProject> list = iMvcService.readXml();
		for(LiliProject l : list){
			System.out.println(l.toString());
		}
		 return "success";
	}
	
	@RequestMapping("/doUploadDoc")
	@ResponseBody
	public ServerResponse<String> readExcelFromDoc(@RequestParam("file")MultipartFile file){
		 return iMvcService.readExcelFromDoc(file);
	}
}
