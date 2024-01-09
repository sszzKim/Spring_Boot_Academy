package com.board.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
	
	private int seq;
	private int seqno;
	private String writer;
	private String title;
	private String content;
	private Date regdate;
	private String userid;
	private int hitno;
	private int likecnt;
	private int dislikecnt;
	
	public BoardDTO() {}

	public BoardDTO(int seq, int seqno, String writer, String title, String content, Date regdate, String userid,
			int hitno, int likecnt, int dislikecnt) {
		super();
		this.seq = seq;
		this.seqno = seqno;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.regdate = regdate;
		this.userid = userid;
		this.hitno = hitno;
		this.likecnt = likecnt;
		this.dislikecnt = dislikecnt;
	}

}
