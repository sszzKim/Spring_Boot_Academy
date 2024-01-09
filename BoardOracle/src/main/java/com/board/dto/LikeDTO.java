package com.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDTO {
	private int seqno; 
	private String userid;
	private String mylikecheck;
	private String mydislikeckeck ;
	private String likedate;
	private String dislikedate;
	
	public LikeDTO() {}
	
}
