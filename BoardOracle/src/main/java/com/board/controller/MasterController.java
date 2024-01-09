package com.board.controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.board.service.MasterService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

@Controller
public class MasterController {
	
	@Autowired
	MasterService service;
	
	@Autowired
    private ServletContext servletContext; //서버정보
	
	@Autowired
	private DataSource dataSource; //데이터베이스 정보
	
	@GetMapping("/master/userStatic")
	public void getUserStatic(Model model) {
		model.addAttribute("board", service.boardCountGroupbyUseridTOP5());
		model.addAttribute("reply", service.replyCountGroupbyUseridTOP5());
		model.addAttribute("likecnt", service.boardByLikecntTOP5());
		model.addAttribute("dislikecnt", service.boardByDislikecntTOP5());
		model.addAttribute("job", service.countGroupbyJobTOP5());
	}
	
	//파일관리 화면 GET
	@GetMapping("/master/fileManage")
	public void getFileManager(Model model) {
		model.addAttribute("delTrgtFileCnt",service.getDltTrgtFile().size());
	}
	
	//파일관리 화면 POST
	@ResponseBody
	@PostMapping("/master/fileManage")
	public Map<String, String> postFileManager() {
		
		Map<String, String> json = new HashMap<>();
		
		service.doDeleteFile(service.getDltTrgtFile());
		
		json.put("message", "GOOD");
		return json;
	}
	
	//시스템정보 GET
	@GetMapping("/master/systemInfo")
	public void getSystemInfo(Model model) throws SQLException {
		
		model.addAttribute("serverinfo",servletContext.getServerInfo()); //서버정보
		model.addAttribute("majorversion",servletContext.getMajorVersion()); //서블릿정보
		model.addAttribute("minorversion",servletContext.getMinorVersion()); //서블릿정보
		
		Connection connection = dataSource.getConnection();
		DatabaseMetaData metaData = connection.getMetaData();
		String databaseProductName = metaData.getDatabaseProductName();
		String databaseProductVersion = metaData.getDatabaseProductVersion();
		
		model.addAttribute("databaseProductName",databaseProductName); //데이터베이스 정보
		model.addAttribute("databaseProductVersion",databaseProductVersion); //데이터베이스 버전 정보
	}
	
	//게시판 관리 GET
	@GetMapping("/master/boardManage")
	public void getBoardManage(){
		
	}
	
	//게시판 관리 GET
	@ResponseBody
	@PostMapping("/master/boardManage")
	/* public Map<String, String> postBoardManage(@RequestBody(required = false) Map<String, String> map, 
										@RequestParam(value = "logoFile", required = false) MultipartFile file, 
										HttpSession session, Model model) throws SQLException { */
	public Map<String, String> postBoardManage(@RequestParam("selectBox") String selectBox, 
										@RequestParam(value = "logoFile", required = false) MultipartFile file, 
										HttpSession session, Model model) throws SQLException {
		
		Map<String, String> json = new HashMap<>();
		
		//행 처리 개수 셋팅
		if(!selectBox.equals("") && selectBox != null)
		{
			session.setMaxInactiveInterval(3600*24*7); //세션유지기간 = 한시간*24시간*7일
			session.setAttribute("pageRowSize", selectBox);
        }
		
		//파일 처리
		System.out.println("selectBox: "+selectBox);
		System.out.println("file: "+file);
		
		String path = "C:/Repository/logo/";
		File targetFile;
		
		String org_filename="";
		String org_fileExtension="";
		String stored_filename="";
		
		if(!file.isEmpty()){ //empty가 아니면
			System.out.print("파일: ");
			org_filename = file.getOriginalFilename();
			org_fileExtension = org_filename.substring(org_filename.lastIndexOf(".")); //확장자 추출
			stored_filename = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	 //-들어왔을 경우 제거
			
			try {
				targetFile = new File(path + stored_filename);
				file.transferTo(targetFile);
				json.put("logoSrc", stored_filename);
				json.put("fileFlag", "O");
			} catch (Exception e) { e.printStackTrace();}
		}else { json.put("fileFlag", "X"); }
		
		json.put("message", "GOOD");
		//json.put("logoSrc", stored_filename);
		return json;
	}

}
