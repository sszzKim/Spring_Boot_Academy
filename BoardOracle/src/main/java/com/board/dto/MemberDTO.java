package com.board.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
	private String userid;
	private String username;
	private String password;
	private String job;
	private String gender;
	private String hobby;
	private String telno;
	private String email;
	private String zipcode;
	private String address; 
	private String description;
	private LocalDate regdate; 
	private LocalDate lastlogindate;
	private LocalDate lastpwdate; 
	private int pwdchk; 
	private String role; 
	private String org_filename; 
	private String stored_filename; 
	private long filesize;
	private LocalDate lastlogoutdate;
	private String authkey;
	
	public MemberDTO() {}

	public MemberDTO(String userid, String username, String password, String job, String gender, String hobby,
			String telno, String email, String zipcode, String address, String description, LocalDate regdate,
			LocalDate lastlogindate, LocalDate lastpwdate, int pwdchk, String role, String org_filename,
			String stored_filename, long filesize, LocalDate lastlogoutdate, String authkey) {
		
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.job = job;
		this.gender = gender;
		this.hobby = hobby;
		this.telno = telno;
		this.email = email;
		this.zipcode = zipcode;
		this.address = address;
		this.description = description;
		this.regdate = regdate;
		this.lastlogindate = lastlogindate;
		this.lastpwdate = lastpwdate;
		this.pwdchk = pwdchk;
		this.role = role;
		this.org_filename = org_filename;
		this.stored_filename = stored_filename;
		this.filesize = filesize;
		this.lastlogoutdate = lastlogoutdate;
		this.authkey = authkey;
	}

	@Override
	public String toString() {
		return "MemberDTO [userid=" + userid + ", username=" + username + ", password=" + password + ", job=" + job
				+ ", gender=" + gender + ", hobby=" + hobby + ", telno=" + telno + ", email=" + email + ", zipcode="
				+ zipcode + ", address=" + address + ", description=" + description + ", regdate=" + regdate
				+ ", lastlogindate=" + lastlogindate + ", lastpwdate=" + lastpwdate + ", pwdchk=" + pwdchk + ", role="
				+ role + ", org_filename=" + org_filename + ", stored_filename=" + stored_filename + ", filesize="
				+ filesize + ", lastlogoutdate=" + lastlogoutdate + ", authkey=" + authkey + "]";
	}
	

	

}
