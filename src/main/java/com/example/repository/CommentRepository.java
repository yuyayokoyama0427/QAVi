package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * commentsテーブルを操作するリポジトリ.
 * 
 * @author yuyayokoyama
 *
 */
@Repository
public class CommentRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	/**
	 * コメントの新規作成.
	 * 
	 * @param comment コメント
	 * @return 作成されたコメント情報
	 */
	public Comment insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		if(comment.getId() != null) {
			throw new NullPointerException();
		}
		template.update("INSERT INTO comments(name, content, article_id) VALUES(:name, :content, :articleId)", param);
		return comment;
	}

}
