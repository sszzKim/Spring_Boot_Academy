package com.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.board.dto.AddressDTO;
import com.board.dto.BoardDTO;
import com.board.dto.MemberDTO;


@Mapper
public interface MemberMapper {
	
	//멤버 등록 하기
	public void signUpMember(MemberDTO member) throws Exception; 
	
	//id Check
	public int idCheck(String userid) throws Exception; 
	
	//마지막 로그인 날짜 update
	public void lastlogindateUpdate(String userid) throws Exception; 
	
	//마지막 로그아웃 날짜 update
	public void lastlogoutdateUpdate(String userid) throws Exception; 
	
	//memberInfo
	public MemberDTO memberInfo(String userid) throws Exception; 
	
	//authkey update
	public void authkeyUpdate(MemberDTO member) throws Exception; 
	
	//authkey Exists
	public MemberDTO authkeyExists(String authkey) throws Exception; 
	
	//주소검색
	public List<AddressDTO> addrSearch(Map<String, Object> data) throws Exception;
	
	//주소 totalcount
	public int addrTotalCount(String keyword) throws Exception;
	
	//비밀번호 수정
	public void pwdModify(MemberDTO member) throws Exception;
	
	//아이디 찾기
	public String searchID(MemberDTO member) throws Exception;
	
	//비밀번호 찾기 중 userid, email로 멤버 찾기
	public MemberDTO findMemberByUseridEmail(MemberDTO member) throws Exception;
	
	//회원정보 수정
	public void memberInfoModify(MemberDTO member) throws Exception;
	
	//회원정보 삭제
	public void deleteMember(String userid) throws Exception;
	
	//패스워드 변경일 미루기
	public void pwkchkUpdate(String userid) throws Exception;
}
