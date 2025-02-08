package com.healthcheck.ambulant.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcheck.ambulant.entity.M_Test_Item;
import com.healthcheck.ambulant.repository.M_Test_ItemMapper;

/**
 * M_Test_Itemサービスクラス
 */
@Service
public class M_Test_ItemService {
	// ユーザマッパー定義
	@Autowired
	private M_Test_ItemMapper m_Test_ItemMapper;
	
	/**
	 * U_Test_Itemテーブルから対象ユーザを検索
	 * @param user_id ユーザID
	 * @return 検索結果のM_Test_Itemオブジェクトを返す
	 */
	public M_Test_Item selectM_Test_Item(String user_id) {
		// 検索結果のM_Test_Itemオブジェクトを返す
		return m_Test_ItemMapper.selectM_Test_Item(user_id);
	}
	
	/**
	 * U_Test_Itemテーブルから対象ユーザの身長を更新
	 * @param user_id ユーザID
	 * @param height 身長
	 * @return 検索結果のM_Test_Itemオブジェクトを返す
	 */
	public int updateHeight(String user_id, BigDecimal height) {
		return m_Test_ItemMapper.updateHeight(user_id, height);
	}
}
