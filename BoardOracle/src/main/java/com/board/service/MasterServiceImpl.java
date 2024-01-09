package com.board.service;

import java.util.HashMap;
import java.util.List;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.dto.FileDTO;
import com.board.mapper.MasterMapper;

@Service
public class MasterServiceImpl implements MasterService{
	
	@Autowired
	MasterMapper mapper;

	@Override
	public List<FileDTO> getDltTrgtFile() {
		return mapper.getDltTrgtFile();
	}

	@Override
	public void doDeleteFile(List<FileDTO> fileList) {
		
		for(FileDTO f : fileList) {
			String path = "C:/Repository/file/";
			File file = new File(path+f.getStored_filename()); //파일 생성
			file.delete(); //disk에서 삭제
			
			//tbl_file도 삭제
			mapper.deleteByFileseqno(f.getFileseqno());
		}
	}

	@Override
	public List<HashMap<String, Object>> boardCountGroupbyUseridTOP5() {
		return mapper.boardCountGroupbyUseridTOP5();
	}

	@Override
	public List<HashMap<String, Object>> replyCountGroupbyUseridTOP5() {
		return mapper.replyCountGroupbyUseridTOP5();
	}

	@Override
	public List<HashMap<String, Object>> boardByLikecntTOP5() {
		return mapper.boardByLikecntTOP5();
	}

	@Override
	public List<HashMap<String, Object>> boardByDislikecntTOP5() {
		return mapper.boardByDislikecntTOP5();
	}

	@Override
	public List<HashMap<String, Object>> countGroupbyJobTOP5() {
		return mapper.countGroupbyJobTOP5();
	}

	

}
