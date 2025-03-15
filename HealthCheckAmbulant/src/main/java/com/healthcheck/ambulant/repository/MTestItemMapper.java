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
	
	// ユーザIDをキーとして体重更新
	int updateWeight(String userId, BigDecimal weight);
	
	// ユーザIDをキーとして視力更新
	int updateVision(String userId, BigDecimal visionLeft, BigDecimal visionRight);
	
	// ユーザIDをキーとして聴力更新
	int updateHearing(String userId, Integer intHearing);
}
