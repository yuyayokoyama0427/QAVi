package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Comment;
import com.example.repository.CommentRepository;

/**
 * コメント関連サービス.
 * 
 * @author yuyayokoyama
 *
 */
@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	
	/**
	 * コメントの登録.
	 * 
	 * @param comment コメント
	 */
	public void insert(Comment comment) {
		commentRepository.insert(comment);
	}

}
