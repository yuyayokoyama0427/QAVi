package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;


/**
 * articlesテーブルを操作するリポジトリ.
 * 
 * @author yuyayokoyama
 *
 */

@Repository
public class ArticleRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i)->{
		
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		
		return article;
	};
	
	
	/**
	 * 記事情報の取得
	 * 
	 * @return 記事全件
	 */
	public List<Article> findAll(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, content FROM articles ORDER BY id DESC;");
		List<Article> articleList = template.query(sql.toString(), ARTICLE_ROW_MAPPER);
		
		return articleList;
	}
	
	/**
	 * 記事の追加.
	 * 
	 * @param article 記事
	 */
	public Article insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String insertSql = "INSERT INTO articles(name, content) VALUES(:name, :content);";
		template.update(insertSql, param);
		return article;
	}
	
	/**
	 * 記事の削除.
	 * コメントも同時に削除。
	 * 
	 * @param articleId
	 */
	public void delete(int articleId) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", articleId);
		String sql = "WITH deleted AS (DELETE FROM articles WHERE id = :id RETURNING id) DELETE FROM comments WHERE article_id IN (SELECT id FROM deleted)";
		template.update(sql, param);
	}
	
	//参考：http://aoyagikouhei.blog8.fc2.com/blog-entry-183.html

	
	
}
