package com.healthcheck.ambulant.entity;

import lombok.Data;

/**
 * M_Userテーブルエンティティクラス
 */
@Data
public class M_User {
	// ユーザID
	private Integer user_id;
	
	// パスワード
	private String password;
	
	// 苗字
	private String first_name;
	
	// 名前
	private String last_name;
	
	// 年齢
	private int age;
	
	// 性別
	private int sex;
	
	// 電話番号
	private long tel;	
}
