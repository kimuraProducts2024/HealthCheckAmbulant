package com.healthcheck.ambulant.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcheck.ambulant.entity.MTestItem;
import com.healthcheck.ambulant.repository.MTestItemMapper;

/**
 * MTestItemサービスクラス
 */
@Service
public class MTestItemService {
	// ユーザマッパー定義
	@Autowired
	private MTestItemMapper mTestItemMapper;
	
	/**
	 * UTestItemテーブルから対象ユーザを検索
	 * @param userId ユーザID
	 * @return 検索結果のMTestItemオブジェクトを返す
	 */
	public MTestItem selectMTestItem(String userId) {
		// 検索結果のM_Test_Itemオブジェクトを返す
		return mTestItemMapper.selectMTestItem(userId);
	}
	
	/**
	 * UTestItemテーブルから対象ユーザの身長を更新
	 * @param userId ユーザID
	 * @param height 身長
	 * @return 検索結果のMTestItemオブジェクトを返す
	 */
	public int updateHeight(String userId, BigDecimal height) {
		return mTestItemMapper.updateHeight(userId, height);
	}
	
	/**
	 * UTestItemテーブルから対象ユーザの体重を更新
	 * @param userId ユーザID
	 * @param height 体重
	 * @return 検索結果のM_Test_Itemオブジェクトを返す
	 */
	public int updateWeight(String userId, BigDecimal weight) {
		return mTestItemMapper.updateWeight(userId, weight);
	}
}
