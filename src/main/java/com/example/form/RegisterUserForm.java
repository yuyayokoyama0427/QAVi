package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * ユーザー登録時に使用するフォーム.
 * 
 * @author yuyayokoyama
 *
 */
public class RegisterUserForm {
	
	/** 名前 */
	@NotBlank(message="名前を入力してください。")
	private String name;
	@NotBlank(message="メールアドレスを入力してください。")
	@Email(message = "Emailの形式が不正です")
	/** Eメール */
	private String email;
	@NotBlank(message="郵便番号を入力して下さい")
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$",message="郵便番号は半角で入力してください")
	/** 郵便番号 */
	private String zipcode;
	@NotBlank(message="住所を入力して下さい")
	/** 住所 */
	private String address;
	@NotBlank(message="電話番号を入力して下さい")
	@Pattern(regexp="^0\\d{2,3}-\\d{1,4}-\\d{4}$",message="電話番号は半角で入力してください")
	/** 電話番号 */
	private String telephone;
	@Size(min = 8, max = 16, message = "パスワードは8文字以上16文字以内で設定してください")
	/** パスワード */
	private String password;
	@NotBlank(message="確認用パスワードを入力して下さい")
	/** 確認用パスワード */
	private String confirmPassword;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Override
	public String toString() {
		return "RegisterUserForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", password=" + password + ", confirmPassword=" + confirmPassword + "]";
	}

}
