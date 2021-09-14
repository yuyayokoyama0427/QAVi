package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

/**
 * 記事関連サービスクラス.
 * 
 * @author yuyayokoyama
 *
 */
@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	/**
	 * 記事一覧を取得.
	 * 
	 * @return コメントを含んだ記事一覧情報
	 */
	public List<Article> findAll(){
		return articleRepository.findAll();
	}
	
	/**
	 * 記事の登録.
	 * 
	 * @param article 記事情報
	 * @return 登録した記事
	 */
	public Article insert(Article article) {
		return articleRepository.insert(article);
	}

}
