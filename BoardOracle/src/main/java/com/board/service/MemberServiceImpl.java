package com.board.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.dto.AddressDTO;
import com.board.dto.MemberDTO;
import com.board.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberMapper mapper;
	
	@Override
	public void signUpMember(MemberDTO member1) throws Exception {
		mapper.signUpMember(member1);
	}
	
	@Override
	public int idCheck(String userid) throws Exception {
		return mapper.idCheck(userid);
	}
	

	@Override
	public void lastlogindateUpdate(String userid) throws Exception {
		mapper.lastlogindateUpdate(userid);
	}
	
	@Override
	public void lastlogoutdateUpdate(String userid) throws Exception {
		mapper.lastlogoutdateUpdate(userid);
	}
	
	@Override
	public MemberDTO memberInfo(String userid) throws Exception {
		return mapper.memberInfo(userid);
	}
	

	@Override
	public void authkeyUpdate(MemberDTO member1) throws Exception {
		mapper.authkeyUpdate(member1);
	}
	

	@Override
	public MemberDTO authkeyExists(String authkey) throws Exception {
		return mapper.authkeyExists(authkey);
	}
	
	@Override
	public List<AddressDTO> addrSearch(int startPoint, int endPoint, String keyword) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("startPoint", startPoint);
		data.put("endPoint", endPoint);
		data.put("keyword", keyword);
		
		List<AddressDTO>  dto = new ArrayList<AddressDTO>();
		
		
		//dto = mapper.addrSearch(data);
		//dto[1];
		//System.out.println(dto.get(0).getZipcode());
		return mapper.addrSearch(data);
	}

	@Override
	public int addrTotalCount(String keyword) throws Exception {
		return mapper.addrTotalCount(keyword);
	}

	@Override
	public void pwdModify(MemberDTO member1) throws Exception {
		mapper.pwdModify(member1);
	}

	@Override
	public String searchID(MemberDTO member1) throws Exception {
		return mapper.searchID(member1);
	}

	@Override
	public MemberDTO findMemberByUseridEmail(MemberDTO member1) throws Exception {
		return mapper.findMemberByUseridEmail(member1);
	}

	@Override
	public void memberInfoModify(MemberDTO member) throws Exception {
		mapper.memberInfoModify(member);
	}

	@Override
	public void deleteMember(String userid) throws Exception {
		mapper.deleteMember(userid);
	}

	@Override
	public void pwkchkUpdate(String userid) throws Exception {
		mapper.pwkchkUpdate(userid);
	}

}
