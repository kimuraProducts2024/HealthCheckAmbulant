package com.healthcheck.ambulant.entity;

import java.math.BigDecimal;

import lombok.Data;

/**
 * M_Test_Itemテーブルエンティティクラス
 */
@Data
public class M_Test_Item {
	// ユーザID
	private Integer user_id;
	
	// 身長
	private BigDecimal height;
	
	// 体重
	private BigDecimal weight;
	
	// 右目視力
	private BigDecimal vision_right;
	
	// 左目視力
	private BigDecimal vision_left;
	
	// 聴力
	private Integer hearing;
	
	// 最大血圧
	private Integer systolic_blood_pressure;
	
	// 最小血圧
	private Integer disastolic_blood_pressure;
}
