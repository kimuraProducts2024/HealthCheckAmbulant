package com.healthcheck.ambulant.repository;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;

import com.healthcheck.ambulant.entity.M_Test_Item;

/**
 * M_Test_Itemテーブルマッパーインタフェース
 */
@Mapper
public interface M_Test_ItemMapper {
	// データ検索
	M_Test_Item selectM_Test_Item(String user_id);
	
	// ユーザIDをキーとして身長更新
	int updateHeight(String user_id, BigDecimal height);
	
	// ユーザIDをキーとして体重更新
	int updateWeight(String user_id, BigDecimal weight);
}
