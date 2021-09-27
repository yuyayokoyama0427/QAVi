package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.service.UserService;

@Controller
@RequestMapping("/user-detail")
public class UserDetailController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	
	/**
	 * ユーザー詳細ページを表示.
	 * 
	 * @return ユーザー詳細ページ
	 */
	@RequestMapping("")
	public String index() {
		return "/user_detail";
	}
	
	/**
	 * 退会する.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/quit")
	public String quit() {
		User user = (User) session.getAttribute("user");
		Integer userId = user.getId();
		// セッションの切断
		session.invalidate();
		userService.deleteById(userId);
		return "redirect:/login";
	}
	

}
