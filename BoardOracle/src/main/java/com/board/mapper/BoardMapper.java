package com.board.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.board.dto.BoardDTO;
import com.board.dto.FileDTO;
import com.board.dto.LikeDTO;
import com.board.dto.ReplyDTO;

@Mapper
public interface BoardMapper {
	//게시물 리스트
	public List<BoardDTO> list( Map<String, Object> data ) throws Exception;
	
	//게시물 보기
	public BoardDTO view(int seqno) throws Exception;
	
	//게시물 등록 하기
	public void write(BoardDTO board) throws Exception; 
	
	//게시물 tbl_board_seq NEXTVAL
	public int getSeqnoNextval() throws Exception; 
	
	//수정하기
	public void modify(BoardDTO board) throws Exception;
	
	//삭제하기
	public void delete(int seqno) throws Exception;
	
	//hit 올리기
	public void hitNoUpdate(int seqno) throws Exception;
	
	//이전 seqno 가져오기
	public int pre_seqno(Map<String, Object> data) throws Exception;
	
	//다음 seqno 가져오기
	public int next_seqno(Map<String, Object> data) throws Exception;
	
	//totalCount 가져오기
	public int totalcount(Map<String, Object> data) throws Exception;
	
	//파일업로드
	public void fileUpload(Map<String, Object> map) throws Exception; 
	
	//파일 select
	public FileDTO getFile(int fileseqno) throws Exception; 
	
	//파일 리스트
	public List<FileDTO> getFileList(int seqno) throws Exception;
	
	//seqn에 따른 파일체크 update
	public void checkfileUpdate(int seqno) throws Exception;
	
	//댓글 리스트 가져오기
	public List<ReplyDTO> replyView(int seqno) throws Exception;
	
	//댓글 등록
	public void replyRegistry(ReplyDTO reply) throws Exception;
		
	//댓글 수정
	public void replyUpdate(ReplyDTO reply) throws Exception;
		
	//댓글 삭제
	public void replyDelete(int replyseqno) throws Exception;
	
	//좋아요 처리
	public LikeDTO likeCheckView(LikeDTO like) throws Exception;
	
	public void likeCheckRegistry(LikeDTO like) throws Exception;
	
	public void likeCheckUpdate(LikeDTO like) throws Exception;
	
	//게시물 좋아요 싫어요 업데이트
	public void boardLikeUpdate(BoardDTO board) throws Exception;
	
	//게시물 수정 시 파일 정보 수정 checkfile을 X로 변경
	public void deleteFileList(int fileseqno) throws Exception;
	
	//Userid에 연관되어 있는 파일리스트 가져오기
		public List<FileDTO> getFileListByUserid(String userid) throws Exception;
}
