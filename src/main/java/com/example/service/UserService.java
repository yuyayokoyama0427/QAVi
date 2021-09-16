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
	
	
	
	
}
