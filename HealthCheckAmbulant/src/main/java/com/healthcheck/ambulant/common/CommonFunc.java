package com.healthcheck.ambulant.common;

import java.math.BigDecimal;

import org.springframework.ui.Model;

import com.healthcheck.ambulant.entity.MTestItem;
import com.healthcheck.ambulant.form.InputForm;
import com.healthcheck.ambulant.form.InputForm.InputType;
import com.healthcheck.ambulant.service.MTestItemService;

import jakarta.servlet.http.HttpSession;

/*
 * 共通機能クラス
 * 画面初期化、画面遷移等の処理を行う
 */
public class CommonFunc {
	// 遷移先のHTMLファイル名
	private String screenName = null;
	
	/**
	 * メインメニュー画面：各入力画面に遷移する際の初期処理
	 * MTestItem情報を取得し、Modelに登録する
	 * @param id クリックしたリンクのID
	 * @param session セッション
	 * @param model モデル
	 * @param mTestItem 検査項目情報
	 * @param mTestItemService 検査項目サービス
	 */
	public void initInput(Integer id, HttpSession session, Model model, 
						MTestItem mTestItem, MTestItemService mTestItemService) {
		// 入力画面情報
		InputForm inputForm = new InputForm();
		
        // MTestItem情報を取得
		mTestItem = mTestItemService.selectMTestItem(session);
		
		// 入力画面タイプ
		InputType inputType = null;
		
		// クリックしたリンクのID
		switch (id) {
			// 身長入力の場合
			case 1:
				// 取得したMTestItem情報、身長がともにnullでない場合
				if (mTestItem != null && mTestItem.getHeight() != null) {
					// 身長小数部1桁を10倍にした値を取得
					BigDecimal tenDecimal = new BigDecimal("10.0");
					BigDecimal decimalHeight = mTestItem.getHeight().remainder(BigDecimal.ONE).multiply(tenDecimal);
					
					// 身長の整数部を設定
					inputForm.setIntegerPart(mTestItem.getHeight().intValue());
					
					// 身長の小数部を設定
					inputForm.setDecimalPart(decimalHeight.intValue());
				}
				
				// 入力画面タイプをHEIGHTに設定
				inputType = InputType.HEIGHT;
				break;
			// 体重入力の場合
			case 2:
				break;
			// 視力入力の場合
			case 3:
				break;
			// 聴力入力の場合
			case 4:
				break;
			// 血圧入力の場合
			case 5:
				break;
			// それ以外の場合
			default:
					
		}
		
		// メッセージの初期設定
		inputForm.setErrorLabel("");
		
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
		
		// 入力画面タイプをセッションに設定
		session.setAttribute("InputType", inputType);
	}
	
	/**
	 * メインメニュー画面：メインメニューの
	 * いずれかのリンクをクリックした際、
	 * 遷移先HTMLファイル名を取得する処理
	 * @param id クリックしたリンクのID
	 * @return 遷移先のHTMLファイル名
	 */
	public String getNextPageName(Integer id) {
		// 遷移先のHTMLファイル名
		screenName = null;
		
		// クリックしたリンクのID
		switch (id) {
			// 身長入力の場合
			case 1:
				screenName = "InputHeight";
				break;
			// 体重入力の場合
			case 2:
				screenName = "InputWeight";
				break;
			// 視力入力の場合
			case 3:
				screenName = "InputEyeTest";
				break;
			// 聴力入力の場合
			case 4:
				screenName = "InputHearingTest";
				break;
			// 血圧入力の場合
			case 5:
				screenName = "InputBloodPressure";
				break;
			// それ以外の場合
			default:
				screenName = "MainMenu";
		}
		
		// 遷移先のHTMLファイル名を返す
		return screenName;
	}
	
	/**
	 * 各入力画面：確認ボタン押下時の処理
	 * 入力チェックを行い、
	 * エラーの場合は、エラーメッセージを設定し、
	 * 入力画面の遷移先を返す
	 * エラーがない場合は、確認画面の遷移先を返す
	 * @param inputForm 入力画面の各項目情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	public String getNextConfPageName(InputForm inputForm, HttpSession session, Model model) {
		// 入力画面タイプをsessionから取得
		InputType inputType = (InputType)session.getAttribute("InputType");
		
		// 入力画面タイプ
		switch(inputType) {
			// 身長の場合
			case HEIGHT:
				// 身長整数部が未入力の場合
				if (inputForm.getIntegerPart() == null) {
					// エラーメッセージを設定し、モデルに登録する
					setErrorLabel("身長整数部の値を入力してください。", inputForm, model);
					
					// 身長入力画面に設定
					screenName = "InputHeight";
					break;
				}
				
				// 身長小数部が未入力の場合
				if (inputForm.getDecimalPart() == null) {
					// エラーメッセージを設定し、モデルに登録する
					setErrorLabel("身長小数部の値を入力してください。", inputForm, model);
					
					// 身長入力画面に設定
					screenName = "InputHeight";
					break;
				}
				
				// 身長整数部が1000以上の場合
				if (inputForm.getIntegerPart() >= 1000) {
					// エラーメッセージを設定し、モデルに登録する
					setErrorLabel("身長整数部は3桁以下で入力してください。", inputForm, model);
					
					// 身長入力画面に設定
					screenName = "InputHeight";
					break;
				}
				
				// 身長整数部が2桁以上の場合
				if (inputForm.getDecimalPart() >= 10) {
					// エラーメッセージを設定し、モデルに登録する
					setErrorLabel("身長小数部は1桁で入力してください。", inputForm, model);
					
					// 身長入力画面に設定
					screenName = "InputHeight";
					break;
				}
				
				// 画面表示文字列を身長に設定
				inputForm.setLabelPart("身長");
				
				// InputTypeをHEIGHTに設定
				inputForm.setInputType(InputType.HEIGHT);
				
				// 確認画面に設定
				screenName = "Confirm";
				break;
			default:
				
		}
				
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
		
		// 遷移先のHTMLファイル名を返す
		return screenName;
	}
	
	/**
	 * 確認画面：各入力画面に戻る際の処理
	 * @param inputForm 入力画面の各項目情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	public String getPrevInputPageName(InputForm inputForm, HttpSession session, Model model) {
		// 入力画面タイプの取得
		InputType inputType = (InputType)session.getAttribute("InputType");
		
		// メッセージのクリア
		inputForm.setErrorLabel("");
		
		// 入力画面タイプ
		switch(inputType) {
			// 身長の場合
			case HEIGHT:
				// 身長入力画面に設定
				screenName = "InputHeight";
				break;
			// 体重入力の場合
			case WEIGHT:
				// 体重入力画面に設定
				screenName = "InputWeight";
				break;
			// 視力入力の場合
			case EYETEST:
				// 視力入力画面に設定
				screenName = "InputEyeTest";
				break;
			// 聴力入力の場合
			case HEARINGTEST:
				// 聴力入力画面に設定
				screenName = "InputHearingTest";
				break;
			// 血圧入力の場合
			case BLOODPRESSURE:
				// 血圧入力画面に設定
				screenName = "InputBloodPressure";
				break;
			// それ以外の場合
			default:
				// メインメニュー画面に設定
				screenName = "MainMenu";
		}
		
		// 遷移先のHTMLファイル名を返す
		return screenName;
	}
	
	/**
	 * 確認画面：入力内容を元に、MTestItem情報を更新し、
	 * 成功の場合登録完了画面へ遷移する
	 * @param inputForm 入力画面の各項目情報
	 * @param session セッション
	 * @param model モデル
	 * @param mTestItem 検査項目情報
	 * @param mTestItemService ユーザ検査項目サービス
	 * @return 遷移先のHTMLファイル名
	 */
	public String updateMTestItem(InputForm inputForm, HttpSession session, Model model,
			MTestItem mTestItem, MTestItemService mTestItemService) {
		// 入力画面タイプの取得
		InputType inputType = (InputType)session.getAttribute("InputType");
		
		// 対象検査項目情報の更新件数
		int resultInt = 0;
		
		// 入力画面タイプ
		switch(inputType) {
			// 身長の場合
			case HEIGHT:
				// ユーザIDから対象検査項目情報を更新
				resultInt = mTestItemService.updateMTestItem(inputForm, session, model);
				
				// 更新件数が0件以下の場合
				if (resultInt <= 0) {
					// エラーメッセージの設定
					inputForm.setErrorLabel("データの更新に失敗しました。");
					
					// Modelに登録
					model.addAttribute("inputType", inputType);
					
					// 身長入力画面に設定
					screenName = "InputHeight";
					break;
				}
				
				// 登録完了画面に設定
				screenName = "Complete";
				break;
			default:
				break;
		}
		
		// 遷移先のHTMLファイル名を返す
		return screenName;
	}
	
	/**
	 * InputFormに、引数のエラーメッセージ
	 * を設定し、モデルに登録する
	 * @param strMessage エラーメッセージ文字列
	 * @param inputForm 入力画面の各項目情報
	 * @param model モデル
	 */
	private void setErrorLabel(String strMessage, InputForm inputForm, Model model) {
		// エラーメッセージの設定
		inputForm.setErrorLabel(strMessage);
		
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
	}
}
