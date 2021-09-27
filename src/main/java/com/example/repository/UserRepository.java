package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
	
	/**
	 * メールアドレスからユーザ情報を取得.
	 * 
	 * @param email メールアドレス
	 * @return ユーザ情報 存在しない場合はnullを返す
	 */
	public User findByMailAddress(String email) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone FROM users where email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	/**
	 * ユーザー情報を主キー検索.
	 * 
	 * @param id ID
	 * @return ユーザー情報 存在しない場合はnullを返す
	 */
	public User load(Integer id) {
		String sql = "SELECT id, name, email, password, zipcode, address, telephone FROM users WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	/**
	 * メールアドレスとパスワードからユーザー情報を取得.
	 * 
	 * @param email メールアドレス
	 * @param password パスワード
	 * @return ユーザー情報 存在しない場合はnullを返す
	 */
	public User findByMailAddressAndPassword(String email, String password) {
String sql = "SELECT id,name, email, password, zipcode, address, telephone FROM users where email=:email and password=:password";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password",password);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	/**
	 * ユーザー情報の更新.
	 * 
	 * @param user 更新したユーザー情報.
	 */
	public void update(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String updateSql = "UPDATE users SET name=:name, email=:email, password=:password, zipcode=:zipcode, address=:address, telephone=:telephone WHERE id=:id ;";
		template.update(updateSql, param);
	}
	
	/**
	 * ユーザーIDからユーザー情報を削除する.
	 * 
	 * @param id ID
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM users WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
