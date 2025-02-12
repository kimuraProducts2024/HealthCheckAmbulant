package com.healthcheck.ambulant.entity;

import lombok.Data;

/**
 * MUserテーブルエンティティクラス
 */
@Data
public class MUser {
	// ユーザID
	private Integer userId;
	
	// パスワード
	private String password;
	
	// 苗字
	private String firstName;
	
	// 名前
	private String lastName;
	
	// 年齢
	private int age;
	
	// 性別
	private int sex;
	
	// 電話番号
	private long tel;	
}
