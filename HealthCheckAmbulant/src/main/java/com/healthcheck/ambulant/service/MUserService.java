package com.healthcheck.ambulant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcheck.ambulant.entity.MUser;
import com.healthcheck.ambulant.repository.MUserMapper;

/**
 * MUserサービスクラス
 */
@Service
public class MUserService {
	// ユーザマッパー定義
	@Autowired
	private MUserMapper mUserMapper;
	
	/**
	 * MUserテーブルから対象ユーザを検索
	 * @param userId ユーザID
	 * @return 検索結果のMUserオブジェクト
	 */
	public MUser selectMUser(String userId) {
		// 検索結果のM_Userオブジェクトを返す
		return mUserMapper.selectMUser(userId);
	}
}
