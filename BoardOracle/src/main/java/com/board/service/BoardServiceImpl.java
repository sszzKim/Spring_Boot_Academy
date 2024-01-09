package com.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.board.dto.BoardDTO;
import com.board.dto.FileDTO;
import com.board.dto.LikeDTO;
import com.board.dto.ReplyDTO;
import com.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper mapper;
	
	@Override
	public List<BoardDTO> list(int startPoint, int endPoint, String keyword) throws Exception {
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("startPoint", startPoint);
		data.put("endPoint", endPoint);
		data.put("keyword", keyword);
		
		return mapper.list(data);
	}

	@Override
	public BoardDTO view(int seqno) throws Exception {
		return mapper.view(seqno);
	}

	@Override
	public void write(BoardDTO board) throws Exception {
		mapper.write(board);
	}
	
	@Override
	public int getSeqnoNextval() throws Exception {
		return mapper.getSeqnoNextval();
	}

	@Override
	public void modify(BoardDTO board) throws Exception {
		mapper.modify(board);
	}

	@Override
	public void delete(int seqno) throws Exception {
		mapper.delete(seqno);
	}

	@Override
	public void hitNoUpdate(int seqno) throws Exception {
		mapper.hitNoUpdate(seqno);
	}

	@Override
	public int pre_seqno(int seqno, String keyword) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>(); 
		data.put("seqno", seqno);
		data.put("keyword", keyword);
		return mapper.pre_seqno(data);
	}

	@Override
	public int next_seqno(int seqno, String keyword) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>(); 
		data.put("seqno", seqno);
		data.put("keyword", keyword);
		return mapper.next_seqno(data);
	}

	@Override
	public int totalcount(int startPoint, int postNum, String keyword) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("startPoint", startPoint);
		data.put("postNum", postNum);
		data.put("keyword", keyword);
		return mapper.totalcount(data);
	}

	@Override
	public void fileUpload(Map<String, Object> map) throws Exception {
		mapper.fileUpload(map);
	}
	
	@Override
	public FileDTO getFile(int fileseqno) throws Exception {
		return mapper.getFile(fileseqno);
	}
	
	@Override
	public List<FileDTO> getFileList(int seqno) throws Exception {
		return mapper.getFileList(seqno);
	}

	@Override
	public void checkfileUpdate(int seqno) throws Exception {
		mapper.checkfileUpdate(seqno);
	}

	@Override
	public List<ReplyDTO> replyView(int seqno) throws Exception {
		return mapper.replyView(seqno);
	}

	@Override
	public void replyRegistry(ReplyDTO reply) throws Exception {
		mapper.replyRegistry(reply);
	}

	@Override
	public void replyUpdate(ReplyDTO reply) throws Exception {
		mapper.replyUpdate(reply);
	}

	@Override
	public void replyDelete(int replyseqno) throws Exception {
		mapper.replyDelete(replyseqno);
	}

	@Override
	public LikeDTO likeCheckView(LikeDTO like) throws Exception {
		return mapper.likeCheckView(like);
	}

	@Override
	public void likeCheckRegistry(LikeDTO like) throws Exception {
		mapper.likeCheckRegistry(like);
	}

	@Override
	public void likeCheckUpdate(LikeDTO like) throws Exception {
		mapper.likeCheckUpdate(like);
	}

	@Override
	public void boardLikeUpdate(BoardDTO board) throws Exception {
		mapper.boardLikeUpdate(board);
	}

	@Override
	public void deleteFileList(int fileseqno) throws Exception {
		mapper.deleteFileList(fileseqno);
	}

	@Override
	public List<FileDTO> getFileListByUserid(String userid) throws Exception {
		return mapper.getFileListByUserid(userid);
	}

}
