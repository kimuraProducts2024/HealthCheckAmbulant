package com.healthcheck.ambulant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcheck.ambulant.entity.M_User;
import com.healthcheck.ambulant.repository.M_UserMapper;

/**
 * M_Userサービスクラス
 */
@Service
public class M_UserService {
	// ユーザマッパー定義
	@Autowired
	private M_UserMapper m_UserMapper;
	
	/**
	 * M_Userテーブルから対象ユーザを検索
	 * @param user_id ユーザID
	 * @return 検索結果のM_Userオブジェクト
	 */
	public M_User selectM_user(String user_id) {
		// 検索結果のM_Userオブジェクトを返す
		return m_UserMapper.selectM_user(user_id);
	}
}
