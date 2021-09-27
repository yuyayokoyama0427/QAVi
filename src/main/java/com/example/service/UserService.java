package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * usersテーブルを操作するサービスクラス.
 * 
 * @author yuyayokoyama
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * ユーザー登録.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		String password = user.getPassword();
		String digest = passwordEncoder.encode(password);
		user.setPassword(digest);
		
		userRepository.insert(user);
	}
	
	/**
	 * メールアドレスからユーザー情報を取得.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public User findByMailAddress(String email) {
		return userRepository.findByMailAddress(email);
	}
	
	
	/**
	 * ログインする.
	 * 
	 * @param email メールアドレス
	 * @param password パスワード
	 * @return ユーザー情報
	 */
	public User login(String email, String password) {
		User user = userRepository.findByMailAddress(email);
		if(user == null) {
			return null;
		}
		if(passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}
		return null;
	}
	
	/**
	 * ユーザー情報を更新する.
	 * 
	 * @param user 更新されたユーザー情報
	 */
	public void update(User user) {
		String password = user.getPassword();
		String digest = passwordEncoder.encode(password);
		user.setPassword(digest);
		
		userRepository.update(user);
	}
	
	/**
	 * ユーザー情報を削除する.
	 * 
	 * @param id ID
	 */
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}
	
}
