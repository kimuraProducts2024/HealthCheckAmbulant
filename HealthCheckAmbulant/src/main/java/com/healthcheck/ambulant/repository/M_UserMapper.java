package com.healthcheck.ambulant.repository;

import org.apache.ibatis.annotations.Mapper;

import com.healthcheck.ambulant.entity.M_User;

/**
 * M_Userテーブルマッパーインタフェース
 */
@Mapper
public interface M_UserMapper {
	// データ検索
	M_User selectM_user(String user_id);
}

