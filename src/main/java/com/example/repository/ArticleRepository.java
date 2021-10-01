package com.example.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;


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
	
	private static final ResultSetExtractor<List<Article>> ARTICLE_RESULT_SET_EXTRACTOR = (rs)->{
		
		List<Article> articleList = new LinkedList<Article>();
		List<Comment> commentList = null;
		
		// 前の行の記事IDを退避しておく変数
		long beforeArticleId = 0;
		
		while (rs.next()) {
			// 現在検索されている記事IDを退避
			int nowArticleId = rs.getInt("id");
			
			// 現在の記事IDと前の記事IDが違う場合はArticleオブジェクトを生成
			if (nowArticleId != beforeArticleId) {
				Article article = new Article();
				article.setId(nowArticleId);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				// 空のコメントリストを作成し、Articleオブジェクトにセットしておく
				commentList = new ArrayList<Comment>();
				article.setCommentList(commentList);
				// コメントがセットされていない状態のArticleオブジェクトをarticleListオブジェクトにaddする
				articleList.add(article);
			}
			
			// 記事だけあってコメントがない場合はCommentオブジェクトは作らない
			if (rs.getInt("com_id") != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getLong("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				// コメントをarticleオブジェクト内にセットされているcommentListに直接addする(参照型なので可能)
				commentList.add(comment);
			}
			
			// 現在の記事IDを前の記事IDを入れる退避IDに格納
			beforeArticleId = nowArticleId;
		}
		
		return articleList;
	};
	
	
	/**
	 * 記事一覧の取得.
	 * 記事に含まれているコメント一覧も同時に取得。
	 * 
	 * @return 記事全件
	 */
	public List<Article> findAll(){
		String sql = "SELECT a.id, a.name, a.content, com.id com_id, com.name com_name, com.content com_content,com.article_id "
				+ "FROM articles a LEFT JOIN comments com ON a.id = com.article_id ORDER BY a.id DESC, com.id;";
		List<Article> articleList = template.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);
		
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
