package com.board.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {
	
	private int fileseqno;
	private int seqno;
	private String org_filename;
	private String stored_filename;
	private long filesize;
	private String userid;
	private String checkfile;
	
	public FileDTO(int fileseqno, int seqno, String org_filename, String stored_filename, long filesize, String userid,
			String checkfile) {
		//super();
		this.fileseqno = fileseqno;
		this.seqno = seqno;
		this.org_filename = org_filename;
		this.stored_filename = stored_filename;
		this.filesize = filesize;
		this.userid = userid;
		this.checkfile = checkfile;
	}
	
}
