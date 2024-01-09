package com.board.service;

import java.util.HashMap;
import java.util.List;

import com.board.dto.FileDTO;

public interface MasterService {

	//checkfile = 'X' 리스트가져오기
	public List<FileDTO> getDltTrgtFile();
	
	//checkfile = 'X' 파일 삭제
	public void  doDeleteFile(List<FileDTO> fileList);
	
	public List<HashMap<String, Object>> boardCountGroupbyUseridTOP5();
	
	public List<HashMap<String, Object>> replyCountGroupbyUseridTOP5();
	
	public List<HashMap<String, Object>> boardByLikecntTOP5();
	
	public List<HashMap<String, Object>> boardByDislikecntTOP5();
	
	public List<HashMap<String, Object>> countGroupbyJobTOP5();
}
