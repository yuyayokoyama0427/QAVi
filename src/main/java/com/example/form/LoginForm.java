package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ログイン時に使用するフォーム.
 * 
 * @author yuyayokoyama
 *
 */
public class LoginForm {

	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力してください。")
	@Email(message = "Emailの形式が不正です")
	private String email;
	
	/** パスワード */
	@NotBlank(message="パスワードを入力して下さい")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", password=" + password + "]";
	}
	
	
}
