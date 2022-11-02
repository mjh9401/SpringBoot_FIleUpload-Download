package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
	
	@GetMapping("/main")
	public String main() {
		return "index";
	}
	
	// 파일 업로드
	@PostMapping("/upload")
	// 업로드하는 파일들을 MultipartFile 형태의 파라미터로 전달된다.
	public void fileUpload(List<MultipartFile> uploadfile) throws Exception {
		for(MultipartFile file : uploadfile) {
			String originfileName = file.getOriginalFilename();
			File dest = new File("D:\\test\\"+originfileName);
			file.transferTo(dest);
		}
	}
	
	// 파일 다운로드
	@RequestMapping("/download")
	public void download(String fileName, HttpServletResponse response) throws IOException {
		String path = "D:\\test\\";
		String fileNm = fileName+".txt";
		String fullPath = path + fileNm;
		byte[]fileByte = FileUtils.readFileToByteArray(new File(fullPath));
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileNm, "UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	// 체크박스 테스트
	@PostMapping("/check")
	public void check(boolean check) {
		System.out.println("체크박스 상태값 : "+check);
	}
	
	//DatePicker 테스트
	@GetMapping("/date")
	public void date(String date) {
		System.out.println("넘어온 날짜 값 : "+date);
	}
	
}
