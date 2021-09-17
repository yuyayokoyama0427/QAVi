package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 利用者情報を操作するコントローラー.
 * 
 * @author yuyayokoyama
 *
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/toLogout")
	public String toLogout() {
		// セッションの切断
		session.invalidate();
		return "forward:/";
	}

}
