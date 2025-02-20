package com.healthcheck.ambulant.repository;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;

import com.healthcheck.ambulant.entity.MTestItem;

/**
 * MTestItemテーブルマッパーインタフェース
 */
@Mapper
public interface MTestItemMapper {
	// データ検索
	MTestItem selectMTestItem(String userId);
	
	// ユーザIDをキーとして身長更新
	int updateHeight(String userId, BigDecimal height);
}
