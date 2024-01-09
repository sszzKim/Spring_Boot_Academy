package com.board;

import org.springframework.beans.BeanMetadataAttribute;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

	// 스프링시큐리티에서 암호화 관련 객체를 가져다가 스프링빈으로 등록
	@Bean
	public BCryptPasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	//spring security 로그인 화면 사용비활성화, csrf, cors 공격 방어용 보안 설정 비활
	//같은 세션기반 보안 레벨이기때문에 스프링 시큐리티를 쓴다고 해서 보안 레벨이 높아지지는 않는다.
	@Bean
	public SecurityFilterChain filer(HttpSecurity http) throws Exception {
		//http.formLogin().disable().csrf().disable().cors.disable();

		http.formLogin((login)-> login.disable())
		.csrf((csrf)->csrf.disable())
		.cors((cors)->cors.disable());
		
		return http.build();

	}
	 

}
