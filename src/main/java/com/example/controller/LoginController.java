package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginForm;
import com.example.service.UserService;

/**
 * 利用者情報を操作するコントローラー.
 * 
 * @author yuyayokoyama
 *
 */

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public LoginForm setUpForm() {
		return new LoginForm();
	}
	
	/**
	 * ログインする.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("")
	public String toLogin() {
		return "login_user";
	}
	
	/**
	 * ログインする.
	 * 
	 * @param form フォーム
	 * @param result エラー情報格納用オブジェクト 
	 * @param model モデル
	 * @return ログイン後のトップページ
	 */
	@RequestMapping("/login_user")
	public String login_user(@Validated LoginForm form, BindingResult result, Model model) {
		if(form.getEmail().equals("") || form.getPassword().equals("")) {
			return toLogin();
		}
		User user = userService.login(form.getEmail(), form.getPassword());
		if(user == null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}
		session.setAttribute("userName", user.getName());
		session.setAttribute("user", user);
		return "forward:/";
	}

}
