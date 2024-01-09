package com.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.board.dto.FileDTO;
import com.board.dto.MemberDTO;
import com.board.service.BoardService;
import com.board.service.MemberService;
import com.board.util.Page;

@Controller
public class MemberController {
	
	@Autowired
	MemberService service;
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	//로그인 화면 보기
	@GetMapping("/member/login")
	public void getLogin(HttpSession session){
		System.out.println("getLogin session: "+session.getAttribute("userid"));
	}
	
	//로그인 
	@ResponseBody
	@PostMapping("/member/login")
	public Map<String, String> postLogin(MemberDTO member, HttpSession session, @RequestParam("autologin") String autologin) throws Exception{
		
		Map<String, String> json = new HashMap<String, String>();
		//json.put("message", "GOOD");
		
		String authkey = "";
		
		//PASS 처리 / 쿠키 존재 시 로그인 과정 없이 세션 생성 후 게시판 목록 페이지로 이동
		if(autologin.equals("PASS")){
			MemberDTO member1 = service.authkeyExists(member.getAuthkey());
			System.out.print("------sdfjkfjdsRIRIRIIRIRlkfsd!!!!-----"+member1);
			if (member1!=null) {
				
				//패스워드 확인 후 마지막 패스워드 변경일이 30일이 경과 되었을 경우
				//비밀번호 30일 경과 체크
				LocalDate lastpwdate = service.memberInfo(member1.getUserid()).getLastpwdate();
				int pwdchk = service.memberInfo(member1.getUserid()).getPwdchk();
				
				if(LocalDate.now().isAfter(lastpwdate.plusDays(30*pwdchk))) {
					System.out.println("isAfter");
					//model.addAttribute("pwchangeflag", 30*pwdchk);
					json.put("pwchangeflag", Integer.toString(30*pwdchk)); 
				}else {
					json.put("pwchangeflag", Integer.toString(0)); 
				} 
				
				//세션 생성
				session.setMaxInactiveInterval(3600*24*7); //세션유지기간 = 한시간*24시간*7일
				session.setAttribute("userid", service.memberInfo(member1.getUserid()).getUserid());
				session.setAttribute("username", service.memberInfo(member1.getUserid()).getUsername());
				session.setAttribute("role", service.memberInfo(member1.getUserid()).getRole());
				//return "{\"message\":\"GOOD\"}"; 
				json.put("message", "GOOD");
				return json;
			}
		}
		
		//Authkey 생성
		if(autologin.equals("NEW")) {
			authkey = UUID.randomUUID().toString().replaceAll("-", ""); //랜덤으로 하나를 만들어
			member.setAuthkey(authkey);
			service.authkeyUpdate(member);
		}
		
		MemberDTO selected_member = service.memberInfo(member.getUserid());
		
		//id가 있는지 check
		if(service.idCheck(member.getUserid()) == 0){ 
			json.put("message", "ID_NOT_FOUND");
			return json;
		}
		
		//pwd check
		if(!pwdEncoder.matches(member.getPassword(), selected_member.getPassword())){ //일치 시 TRUE
			 json.put("message", "PASSWORD_NOT_FOUND");
			 return json;
		}else
		{
			//마지막 로그인 날짜 등록
			//LocalDate.now()
			service.lastlogindateUpdate(member.getUserid());
			
			//패스워드 확인 후 마지막 패스워드 변경일이 30일이 경과 되었을 경우
			//비밀번호 30일 경과 체크
			LocalDate lastpwdate = selected_member.getLastpwdate();
			int pwdchk = selected_member.getPwdchk();
			
			if(LocalDate.now().isAfter(lastpwdate.plusDays(30*pwdchk))) {
				System.out.println("isAfter");
				//model.addAttribute("pwchangeflag", 30*pwdchk);
				json.put("pwchangeflag", Integer.toString(30*pwdchk)); 
			}else {
				json.put("pwchangeflag", Integer.toString(0)); 
			} 
				
			//세션 생성
			session.setMaxInactiveInterval(3600*24*7); //세션유지기간 = 한시간*24시간*7일
			session.setAttribute("userid", service.memberInfo(member.getUserid()).getUserid());
			session.setAttribute("username", service.memberInfo(member.getUserid()).getUsername());
			session.setAttribute("role", service.memberInfo(member.getUserid()).getRole());
			
			json.put("message", "GOOD"); 
			json.put("authkey", member.getAuthkey()); 
			
			return json; 
		}
	}
	
	//회원등록 화면 보기	
	@GetMapping("/member/signup")
	public void getSignup() {}
	
	//회원등록
	@ResponseBody
	@PostMapping("/member/signup")
	public String postSignup(MemberDTO member, @RequestParam("fileUpload") MultipartFile multipartFile) throws Exception{
		
		String path = "C:/Repository/profile/";
		File targetFile;
		
		String org_filename="";
		String org_fileExtension="";
		String stored_filename="";
		
		if(!multipartFile.isEmpty()){ //empty가 아니면
			System.out.print("파일: ");
			org_filename = multipartFile.getOriginalFilename();
			org_fileExtension = org_filename.substring(org_filename.lastIndexOf(".")); //확장자 추출
			stored_filename = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	 //-들어왔을 경우 제거	
			
		}
		
		try {
			System.out.print("파일1: "+org_filename);
			System.out.print("파일2: "+org_fileExtension);
			System.out.print("파일3: "+stored_filename);
			targetFile = new File(path + stored_filename);
			multipartFile.transferTo(targetFile);
			member.setOrg_filename(org_filename);
			member.setStored_filename(stored_filename);
			member.setFilesize(multipartFile.getSize());
			
			System.out.print("파일4ise: "+member.getFilesize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//패스워드 암호화
		String inputPassword = member.getPassword();
		String encPwd = pwdEncoder.encode(inputPassword); //단방향 암호화, 복구불가 
		member.setPassword(encPwd);
		member.setLastpwdate(LocalDate.now());
		
		member.setRole("USER");
		member.setPwdchk(1);
		
		System.out.println("회원가입:" +member.toString());
		service.signUpMember(member);
		
		//Map<String, String> data = new HashMap<String, String>();
		//data.put("message", "GOOD");
		//data.put("username", URLEncoder.encode(member.getUsername(),"UTF-8"));
		//return data;
		// Map -> JSON 으로 변환해주는 라이브러리는 jackson.databind
		
		return "{\"message\":\"GOOD\",\"username\":\""+ URLEncoder.encode(member.getUsername(),"UTF-8") +"\"}"; 
				
	}
	
	//회원 가입 시 아이디 중복 확인
	@ResponseBody
	@PostMapping("/member/idCheck")
	public int  postIdCheck(@RequestBody String userid) throws Exception {
		int result1 = service.idCheck(userid);
		//System.out.print("■□▢▣▤▥▦▧▨▩▪▫▬▭▮▯▰▱▲△▴: "+result1);
		return result1;
	}
	
	//주소검색
	@GetMapping("/member/addrSearch")
	public void addrSearch( @RequestParam("page") int pageNum,
			@RequestParam(name="keyword", defaultValue="", required=false) String keyword, Model model) throws Exception{
		
		int postNum = 10; //한 화면에 보여지는 게시물 행의 갯수
		int startPoint = (pageNum-1)*postNum+1; //오라클은 1부터
		int endPoint = pageNum*postNum;
		int pageListCount = 10; //화면 하단에 보여지는 페이지리스트의 페이지 갯수
		int totalCount = service.addrTotalCount(keyword);
		
		
		model.addAttribute("list",service.addrSearch(startPoint, endPoint, keyword));
		
		System.out.print("service.addrSearch.list: "+service.addrSearch(startPoint, endPoint, keyword));
		System.out.print("service.addrSearch.list: "+service.addrSearch(startPoint, endPoint, keyword).get(0).getZipcode());
		
		Page page = new Page();
		model.addAttribute("page", pageNum);
		model.addAttribute("pageList", page.getPageAddress(pageNum, postNum, pageListCount, totalCount, keyword));
		
	}
	
	//로그아웃
	@GetMapping("/member/logout")
	public String getLogout(HttpSession session) throws Exception {
		//마지막 로그아웃 날짜 등록
		service.lastlogoutdateUpdate((String)session.getAttribute("userid"));
		session.invalidate();//모든 세션 종료
		
		return "redirect:/";
	}
	
	//회원정보 화면 보기
	@GetMapping("/member/memberinfo")
	public void getMemberinfo(Model model, HttpSession session) throws Exception{
		MemberDTO member = service.memberInfo((String)session.getAttribute("userid"));
		model.addAttribute("view", member);
	}
	
	//회원 정보 수정
	@GetMapping("/member/memberinfoModify")
	public void getMemberinfoModify(Model model, HttpSession session) throws Exception{
		MemberDTO member = service.memberInfo((String)session.getAttribute("userid"));
		model.addAttribute("user", member);
	}
	
	//회원 정보 수정
	@ResponseBody
	@PostMapping("/member/memberinfoModify")
	public String postMemberinfoModify(MemberDTO member, Model model, HttpSession session, @RequestParam("fileUpload") MultipartFile multipartFile) throws Exception{
		
		MemberDTO memberInfo = service.memberInfo((String)session.getAttribute("userid"));
		
		String path = "C:/Repository/profile/";
		File targetFile;
		
		String org_filename="";
		String org_fileExtension="";
		String stored_filename="";
		Long filesize = 0L;
		
		if(!multipartFile.isEmpty()){ //empty가 아니면
			org_filename = multipartFile.getOriginalFilename();
			org_fileExtension = org_filename.substring(org_filename.lastIndexOf(".")); //확장자 추출
			stored_filename = UUID.randomUUID().toString().replaceAll("-", "") + org_fileExtension;	 //-들어왔을 경우 제거	
			filesize = multipartFile.getSize();
			targetFile = new File(path + stored_filename);
			multipartFile.transferTo(targetFile);
			//기존 프로필 삭제
			File file = new File(path+memberInfo.getStored_filename()); //파일 생성
			file.delete(); //disk에서 삭제
		}else {
			//사진변경이 없으면 기존 데이터 가져오기
			org_filename = memberInfo.getOrg_filename();
			stored_filename = memberInfo.getStored_filename();
			filesize = memberInfo.getFilesize();
		}
		
		try {	
				member.setOrg_filename(org_filename);
				member.setStored_filename(stored_filename);
				member.setFilesize(filesize);
		}catch(Exception e) {e.printStackTrace();}
		
	
		//회원정보 수정
		member.setUserid((String)session.getAttribute("userid"));
		System.out.println("membermember: "+member.toString());
		service.memberInfoModify(member);
		
		return "{\"message\":\"GOOD\"}";
		
	}
	
	//패스워드변경 화면 보기
	@GetMapping("/member/memberPasswordModify")
	public void getMemberPasswordModify(Model model, HttpSession session) throws Exception{
		//MemberDTO member = service.memberInfo((String)session.getAttribute("userid"));
		//model.addAttribute("view", member);
	}
	
	//패스워드변경
	@ResponseBody
	@PostMapping("/member/memberPasswordModify")
	public Map<String, String> postMemberPasswordModify(Model model, HttpSession session,
			@RequestBody Map<String, String> pwdDataMap) throws Exception{
		
		Map<String, String> json = new HashMap<String, String>();
		
		//기존 패스워드 가져오기
		MemberDTO selected_member = service.memberInfo((String)session.getAttribute("userid"));
		
		if(!pwdEncoder.matches(pwdDataMap.get("oldpwd"), selected_member.getPassword())){ //일치 시 TRUE
			System.out.println("패스워드 불일치"); 
			//return "{\"message\":\"PASSWORD_NOT_FOUND\"}";
			json.put("message", "MISMATCH");
		}else{
			System.out.println("패스워드 일치"); 
			//패스워드 암호화
			String inputPassword = pwdDataMap.get("newpwd1");
			String encPwd = pwdEncoder.encode(inputPassword); //단방향 암호화, 복구불가 
			MemberDTO member = new MemberDTO();
			member.setUserid((String)session.getAttribute("userid"));
			member.setPassword(encPwd);
			member.setLastpwdate(LocalDate.now());
			service.pwdModify(member); //aukey 삭제도 같이 함
			
			//마지막 로그아웃 시간 업데이트
			//모든 세션 종료
			service.lastlogoutdateUpdate((String)session.getAttribute("userid"));
			session.invalidate();
			json.put("message", "GOOD");
		}
		
		return json;
	}
	
	//아이디 찾기
	@GetMapping("/member/searchID")
	public void getSearchID() throws Exception {}
	
	//아이디 찾기
	@ResponseBody
	@PostMapping("/member/searchID")
	public Map<String, String>  postSearchID(@RequestBody Map<String, String> map, Model model) throws Exception {
		
		Map<String, String> json = new HashMap<String, String>();
		
		MemberDTO member = new MemberDTO();
		member.setUsername(map.get("username"));
		member.setTelno(map.get("telno"));
		
		if(service.searchID(member)!=null) {
			json.put("message", "GOOD");
			json.put("userid",service.searchID(member));
		}else {
			json.put("message", "MISMATCH");
		}
		
		return json;
	}
	
	//비밀번호 찾기 보기
	@GetMapping("/member/searchPassword")
	public void getSearchPassword() throws Exception {}
	
	//비밀번호 찾기
	@ResponseBody
	@PostMapping("/member/searchPassword")
	public Map<String, String>  postSearchPassword(@RequestBody Map<String, String> map, Model model) throws Exception {
		
		Map<String, String> json = new HashMap<String, String>();
		
		//입력값이 데이터베이스에 있는지 확인
		MemberDTO member = new MemberDTO();
		member.setUserid(map.get("userid"));
		member.setEmail(map.get("email"));
		
		if(service.findMemberByUseridEmail(member) != null) {
			
			System.out.println("객체 있다.");
			
			//난수발생
			StringBuffer tempPW = new StringBuffer();
		    Random rnd = new Random();
		    for(int i=0; i<7; i++){
		        int rIndex = rnd.nextInt(3);
		        switch (rIndex) {
		            case 0:
		                tempPW.append((char)((int)(rnd.nextInt(26))+97));
		                break;
		            case 1:
		                tempPW.append((char)((int)(rnd.nextInt(26))+65));
		                break;
		            case 2:
		                tempPW.append(rnd.nextInt(10));
		                break;
		        }
		    }
		    
		    //난수 발생 임시 암호
		    String tempPWD = tempPW.toString();
		    
		    //패스워드 암호화
		    String encPwd = pwdEncoder.encode(tempPWD); //단방향 암호화, 복구불가 
			
		   MemberDTO member1 = new MemberDTO();
			member.setUserid(service.findMemberByUseridEmail(member).getUserid());
			member.setPassword(encPwd);
			member.setLastpwdate(LocalDate.now());
			service.pwdModify(member);
			
			json.put("message", "GOOD");
			json.put("tempPWD", tempPWD);
			
		}else {
			System.out.println("객체 없다.");
			json.put("message","MISMATCH" );
		}
		return json;
	}
	
	//pwdcheck update
	@GetMapping("/member/getPwCheckNotice")
	public String getPwCheckNotice(HttpSession session) throws Exception{
		service.pwkchkUpdate((String)session.getAttribute("userid"));
		return "redirect:/board/list?page=1";
	}
	
	//회원탈퇴
	@GetMapping("/member/resign")
	public void getResign() throws Exception{}
	
	//회원탈퇴
	@Transactional
	@ResponseBody
	@PostMapping("/member/resign")
	public String postResign(@RequestBody Map<String, String> map, HttpSession session) throws Exception{
		
		String userid = (String)session.getAttribute("userid");
		
		//비밀번호 check
		if(!pwdEncoder.matches(map.get("password"), service.memberInfo(userid).getPassword())){
			return "{\"message\":\"MISMATCH\"}";
		}
		
		//게시글 이미지 filecheck = 'X' 처리
		List<FileDTO>  fileList =  boardService.getFileListByUserid(userid);
		fileList.forEach(f -> {
			try {
				boardService.deleteFileList(f.getFileseqno());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		//프로필 삭제
		String path = "C:/Repository/profile/";
		File file = new File(path+service.memberInfo(userid).getStored_filename()); //파일 생성
		file.delete(); //disk에서 삭제
		
		//member삭제 -> Board 삭제 -> reply 삭제
		service.deleteMember(userid);
		
		//세션 및 쿠키 삭제
		session.invalidate();//모든 세션 종료
		
		
		return "{\"message\":\"GOOD\"}";
	}
	
	
	
}
