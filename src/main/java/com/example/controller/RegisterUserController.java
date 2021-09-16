package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterUserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}
	
	
	@RequestMapping("")
	public String toRegister() {
		return "register_user";
	}
	
	@RequestMapping("/register-user")
	public String registerUser(@Validated RegisterUserForm form, BindingResult result) {
		// パスワード確認
		if(!(form.getConfirmPassword().equals(form.getPassword()))) {
			result.rejectValue("confirmPassword", "", "パスワードと確認用パスワードが一致しません。");
		}
		
		// メールアドレス重複チェック
		User duplicateUser = userService.findByMailAddress(form.getEmail());
		if (duplicateUser != null) {
			result.rejectValue("email", "", "既に登録済みのメールアドレスです。");
		}
		
		if (result.hasErrors()) {
			return toRegister();
		}
		
		// フォームからドメインにプロパティ値をコピー
		User user = new User();
		BeanUtils.copyProperties(form, user);
		userService.insert(user);
		return "redirect:/login";
	}

}
