package com.healthcheck.ambulant.repository;

import org.apache.ibatis.annotations.Mapper;

import com.healthcheck.ambulant.entity.MUser;

/**
 * MUserテーブルマッパーインタフェース
 */
@Mapper
public interface MUserMapper {
	// データ検索
	MUser selectMUser(String userId);
}

