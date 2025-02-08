package com.healthcheck.ambulant.common;

import java.math.BigDecimal;

import org.springframework.ui.Model;

import com.healthcheck.ambulant.entity.M_Test_Item;
import com.healthcheck.ambulant.entity.M_User;
import com.healthcheck.ambulant.form.InputForm;
import com.healthcheck.ambulant.form.InputForm.InputType;
import com.healthcheck.ambulant.service.M_Test_ItemService;

import jakarta.servlet.http.HttpSession;

/*
 * 共通機能クラス
 */
public class CommonFunc {
	/**
	 * 身長入力画面に遷移する際の初期処理
	 * M_Test_Item情報を取得し、Modelに登録する
	 * @param session セッション
	 * @param model モデル
	 * @param mTestItem 検査項目情報
	 * @param mTestItemService ユーザ検査項目サービス
	 */
	public void initInputHeight(HttpSession session, Model model, 
						M_Test_Item mTestItem, M_Test_ItemService mTestItemService) {
		// 入力画面情報
		InputForm inputForm = new InputForm();
		
        // M_Test_Item情報を取得
		mTestItem = getMTestItem(session, mTestItemService);
		
		// 取得したM_Test_Item情報、身長がnullでない場合
		if (mTestItem != null && mTestItem.getHeight() != null) {
			BigDecimal tenDecimal = new BigDecimal("10.0");
			BigDecimal decimalHeight = mTestItem.getHeight().remainder(BigDecimal.ONE).multiply(tenDecimal);
			
			// 身長の整数部を設定
			inputForm.setIntegerPart(mTestItem.getHeight().intValue());
			
			// 身長の小数部を設定
			inputForm.setDecimalPart(decimalHeight.intValue());
		}
		
		// メッセージの初期設定
		inputForm.setErrorLabel("");
		
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
		
		// 入力画面タイプをセッションに設定
		session.setAttribute("InputType", InputType.HEIGHT);
	}
	
	/**
	 * M_Test_Item情報をセッションに設定
	 * @param session セッション
	 * @param mTestItemService ユーザ検査項目サービス
	 */
	public M_Test_Item getMTestItem(HttpSession session, M_Test_ItemService mTestItemService) {
		// M_Userセッション情報を取得
		M_User m_User = (M_User)session.getAttribute("m_User");
				
		// ユーザIDから対象検査項目情報を検索
		M_Test_Item m_Test_Item = mTestItemService.selectM_Test_Item(m_User.getUser_id().toString());

		// 取得したM_Test_Item情報をセッションに設定
		return m_Test_Item;
	}
	
	/**
	 * M_Test_Item情報をセッションに設定
	 * @param session セッション
	 * @param mTestItemService ユーザ検査項目サービス
	 */
	public int updateHeight(HttpSession session,M_Test_ItemService mTestItemService, int integerPart, int decimalPart) {
		// M_Userセッション情報を取得
		M_User m_User = (M_User)session.getAttribute("m_User");
		
		// 身長の値に変換
		BigDecimal heightBg = new BigDecimal(String.valueOf(integerPart) + "." + String.valueOf(decimalPart));
		
		// ユーザIDから対象検査項目情報を更新
		int resultInt = mTestItemService.updateHeight(String.valueOf(m_User.getUser_id()), heightBg);

		// 取得したM_Test_Item情報をセッションに設定
		return resultInt;
	}
}
