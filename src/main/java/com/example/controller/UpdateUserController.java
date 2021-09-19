package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.repository.UserRepository;
import com.example.service.UserService;

/**
 * ユーザー情報の更新を行うコントローラー.
 * 
 * @author yuyayokoyama
 *
 */
@Controller
@RequestMapping("/update")
public class UpdateUserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserRepository userRepository;
	
	@ModelAttribute
	public RegisterUserForm setUpForm() {
		RegisterUserForm form = new RegisterUserForm();
		User user = (User) session.getAttribute("user");
		form.setName(user.getName());
		form.setEmail(user.getEmail());
		form.setZipcode(user.getZipcode());
		form.setAddress(user.getAddress());
		form.setTelephone(user.getTelephone());
		form.setPassword(user.getPassword());
		form.setConfirmPassword(user.getPassword());
		return form;
	}
	
	@RequestMapping("")
	public String index() {
		return "update-user-info";

	}
	
	/**
	 * ユーザー情報の更新.
	 * 
	 * @param form フォーム
	 * @param result リザルト
	 * @param model モデル
	 * @return 更新されたユーザー情報
	 */
	@RequestMapping("update-user")
	public String updateUser(@Validated RegisterUserForm form, BindingResult result, Model model) {

		// パスワード確認
		if (!(form.getConfirmPassword().equals(form.getPassword()))) {
			result.rejectValue("confirmPassword", "", "パスワードと確認用パスワードが不一致です。");
		}
		
		User user = (User) session.getAttribute("user");

		User user2 = userRepository.load(user.getId());
		
		if(!(user2.getEmail().equals(form.getEmail()))) {
			
			// メールアドレス重複チェック
			User dupulicateUser = userService.findByMailAddress(form.getEmail());
			
			if (dupulicateUser != null )  {
				result.rejectValue("email", "", "既に登録済みのメールアドレスです。");
			}
		}
		if (result.hasErrors()) {
			return index();
		}
		
		// フォームからドメインにプロパティ値をコピー
		BeanUtils.copyProperties(form, user);
		System.out.println(user);
		userService.update(user);
		return "redirect:/";
	}

}
