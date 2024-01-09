package com.board.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.board.dto.BoardDTO;
import com.board.dto.FileDTO;

@Mapper
public interface MasterMapper {
	
	//checkfile = 'X' 리스트가져오기
	public List<FileDTO> getDltTrgtFile();

	public void deleteByFileseqno(int fileseqno);
	
	public List<HashMap<String, Object>> boardCountGroupbyUseridTOP5();
	
	public List<HashMap<String, Object>> replyCountGroupbyUseridTOP5();
	
	public List<HashMap<String, Object>> boardByLikecntTOP5();
	
	public List<HashMap<String, Object>> boardByDislikecntTOP5();
	
	public List<HashMap<String, Object>> countGroupbyJobTOP5();

}
