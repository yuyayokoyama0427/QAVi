package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * userテーブルを操作するリポジトリ.
 * 
 * @author yuyayokoyama
 *
 */
@Repository
public class UserRepository {
	
	/**
	 * userオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
	
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		
		return user;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 新規ユーザーの登録.
	 * 
	 * @param user ユーザー情報
	 */
	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("INSERT INTO users(name,email,password,zipcode,address,telephone)");
		insertSql.append(" VALUES(:name,:email,:password,:zipcode,:address,:telephone);");
		template.update(insertSql.toString(), param);
	}

}
