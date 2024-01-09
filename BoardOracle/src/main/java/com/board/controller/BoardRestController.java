package com.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.board.service.BoardService;
import com.board.dto.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardRestController {
	
	private final BoardService service;
	
	@GetMapping("/rest/list")
	public List<BoardDTO> getList(Model model, @RequestParam("page") int pageNum, 
			@RequestParam(name="keyword", defaultValue="", required=false) String keyword) throws Exception{
		
		int postNum = 5; //한 화면에 보여지는 게시물 행의 갯수
		int startPoint = (pageNum-1)*postNum+1;
		int endPoint = pageNum*postNum;
		int pageListCount = 10; //화면 하단에 보여지는 페이지리스트의 페이지 갯수
		int totalCount = service.totalcount(startPoint,postNum,keyword);
		
		return service.list(startPoint, endPoint, keyword);
	}
	
	@GetMapping("/rest/view/{seqno}")
	public BoardDTO getView(@PathVariable("seqno") int seqno)throws Exception{
		return service.view(seqno);
	}

}
