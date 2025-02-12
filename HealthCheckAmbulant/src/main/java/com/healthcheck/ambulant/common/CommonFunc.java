package com.healthcheck.ambulant.common;

import java.math.BigDecimal;

import org.springframework.ui.Model;

import com.healthcheck.ambulant.entity.MTestItem;
import com.healthcheck.ambulant.entity.MUser;
import com.healthcheck.ambulant.form.InputForm;
import com.healthcheck.ambulant.form.InputForm.InputType;
import com.healthcheck.ambulant.service.MTestItemService;

import jakarta.servlet.http.HttpSession;

/*
 * 共通機能クラス
 * 画面表示の初期処理、各データの更新処理など
 */
public class CommonFunc {
	/**
	 * 身長入力画面に遷移する際の初期処理
	 * MTestItem情報を取得し、Modelに登録する
	 * @param session セッション
	 * @param model モデル
	 * @param mTestItem 検査項目情報
	 * @param mTestItemService ユーザ検査項目サービス
	 */
	public void initInputHeight(HttpSession session, Model model, 
						MTestItem mTestItem, MTestItemService mTestItemService) {
		// 入力画面情報
		InputForm inputForm = new InputForm();
		
        // MTestItem情報を取得
		mTestItem = getMTestItem(session, mTestItemService);
		
		// 取得したMTestItem情報、身長がnullでない場合
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
	 * 体重入力画面に遷移する際の初期処理
	 * MTestItem情報を取得し、Modelに登録する
	 * @param session セッション
	 * @param model モデル
	 * @param mTestItem 検査項目情報
	 * @param mTestItemService ユーザ検査項目サービス
	 */
	public void initInputWeight(HttpSession session, Model model, 
						MTestItem mTestItem, MTestItemService mTestItemService) {
		// 入力画面情報
		InputForm inputForm = new InputForm();
		
        // MTestItem情報を取得
		mTestItem = getMTestItem(session, mTestItemService);
		
		// 取得したMTestItem情報、体重がnullでない場合
		if (mTestItem != null && mTestItem.getWeight() != null) {
			BigDecimal tenDecimal = new BigDecimal("10.0");
			BigDecimal decimalWeight = mTestItem.getWeight().remainder(BigDecimal.ONE).multiply(tenDecimal);
			
			// 体重の整数部を設定
			inputForm.setIntegerPart(mTestItem.getWeight().intValue());
			
			// 体重の小数部を設定
			inputForm.setDecimalPart(decimalWeight.intValue());
		}
		
		// メッセージの初期設定
		inputForm.setErrorLabel("");
		
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
		
		// 入力画面タイプをセッションに設定
		session.setAttribute("InputType", InputType.WEIGHT);
	}
	
	/**
	 * MTestItem情報をセッションに設定
	 * @param session セッション
	 * @param mTestItemService ユーザ検査項目サービス
	 */
	public MTestItem getMTestItem(HttpSession session, MTestItemService mTestItemService) {
		// M_Userセッション情報を取得
		MUser mUser = (MUser)session.getAttribute("mUser");
				
		// ユーザIDから対象検査項目情報を検索
		MTestItem mTestItem = mTestItemService.selectMTestItem(mUser.getUserId().toString());

		// 取得したMTestItem情報をセッションに設定
		return mTestItem;
	}
	
	/**
	 * MTestItem情報の身長を更新
	 * @param session セッション
	 * @param mTestItemService ユーザ検査項目サービス
	 */
	public int updateHeight(HttpSession session,MTestItemService mTestItemService, int integerPart, int decimalPart) {
		// MUserセッション情報を取得
		MUser mUser = (MUser)session.getAttribute("mUser");
		
		// 身長の値に変換
		BigDecimal heightBg = new BigDecimal(String.valueOf(integerPart) + "." + String.valueOf(decimalPart));
		
		// ユーザIDから対象検査項目情報を更新
		int resultInt = mTestItemService.updateHeight(String.valueOf(mUser.getUserId()), heightBg);

		// 取得したMTestItem情報をセッションに設定
		return resultInt;
	}
	
	/**
	 * MTestItem情報の体重を更新
	 * @param session セッション
	 * @param mTestItemService ユーザ検査項目サービス
	 */
	public int updateWeight(HttpSession session,MTestItemService mTestItemService, int integerPart, int decimalPart) {
		// MUserセッション情報を取得
		MUser mUser = (MUser)session.getAttribute("mUser");
		
		// 体重の値に変換
		BigDecimal weightBg = new BigDecimal(String.valueOf(integerPart) + "." + String.valueOf(decimalPart));
		
		// ユーザIDから対象検査項目情報を更新
		int resultInt = mTestItemService.updateWeight(String.valueOf(mUser.getUserId()), weightBg);

		// 取得したMTestItem情報をセッションに設定
		return resultInt;
	}
}
