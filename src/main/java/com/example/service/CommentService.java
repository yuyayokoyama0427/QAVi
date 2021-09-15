package com.example.service;

import java.util.List;

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
	 * コメント一覧を取得.
	 * 
	 * @param articleId
	 * @return
	 */
	public List<Comment> findByArticleId(int articleId){
		return commentRepository.findByArticleId(articleId);
	}
	
	/**
	 * コメントの登録.
	 * 
	 * @param comment コメント
	 */
	public void insert(Comment comment) {
		commentRepository.insert(comment);
	}

}
