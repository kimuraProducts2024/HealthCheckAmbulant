package com.healthcheck.ambulant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.healthcheck.ambulant.common.CommonFunc;
import com.healthcheck.ambulant.entity.MTestItem;
import com.healthcheck.ambulant.form.InputForm;
import com.healthcheck.ambulant.form.InputForm.InputType;
import com.healthcheck.ambulant.service.MTestItemService;

import jakarta.servlet.http.HttpSession;

/**
 * 入力処理コントローラ
 * 確認画面、登録画面の処理を行う
 */
@Controller
public class InputController {
	// 検査項目情報
	MTestItem mTestItem;
	
	// ユーザ検査項目サービス定義
	@Autowired
	MTestItemService mTestItemService;
	
	/**
	 * 入力画面：戻るボタンクリック
	 * メインメニュー画面に遷移する
	 * @param session セッション
	 * @return 遷移先のHTMLファイル名
	 */
	@PostMapping(value = "/input", params = "returnBtn")
	public String returnMain(HttpSession session) {
		// 入力画面タイプのセッションをnullに設定
		session.setAttribute("InputType", null);
		
		// 遷移先のHTMLファイル名を返す
		return "MainMenu";
	}
	
	/**
	 * 確認ボタンクリック
	 * @param inputForm 入力画面情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	@PostMapping(value = "/input", params = "confirmBtn")
	public String showConfirm(InputForm inputForm, HttpSession session, Model model)
	{
		// 画面が身長入力画面の場合
		if (session.getAttribute("InputType").equals(InputType.HEIGHT)) {
			// 身長整数部が未入力の場合
			if (inputForm.getIntegerPart() == null) {
				// エラーメッセージの設定
				inputForm.setErrorLabel("身長整数部の値を入力してください。");
				
				// Modelに登録
				model.addAttribute("inputForm", inputForm);
				
				// 遷移先のHTMLファイル名を返す
				return "InputHeight";
			}
			
			// 身長小数部が未入力の場合
			if (inputForm.getDecimalPart() == null) {
				// エラーメッセージの設定
				inputForm.setErrorLabel("身長小数部の値を入力してください。");
				
				// Modelに登録
				model.addAttribute("inputForm", inputForm);
				
				// 遷移先のHTMLファイル名を返す
				return "InputHeight";
			}
			
			// 画面表示文字列を身長に設定
			inputForm.setLabelPart("身長");
			
			// InputTypeをHEIGHTに設定
			inputForm.setInputType(InputType.HEIGHT);
		}
		
		// 画面が体重入力画面の場合
		if (session.getAttribute("InputType").equals(InputType.WEIGHT)) {
			// 体重整数部が未入力の場合
			if (inputForm.getIntegerPart() == null) {
				// エラーメッセージの設定
				inputForm.setErrorLabel("体重整数部の値を入力してください。");
				
				// Modelに登録
				model.addAttribute("inputForm", inputForm);
				
				// 遷移先のHTMLファイル名を返す
				return "InputWeight";
			}
			
			// 体重小数部が未入力の場合
			if (inputForm.getDecimalPart() == null) {
				// エラーメッセージの設定
				inputForm.setErrorLabel("体重小数部の値を入力してください。");
				
				// Modelに登録
				model.addAttribute("inputForm", inputForm);
				
				// 遷移先のHTMLファイル名を返す
				return "InputWeight";
			}
			
			// 画面表示文字列を体重に設定
			inputForm.setLabelPart("体重");
			
			// InputTypeをWEIGHTに設定
			inputForm.setInputType(InputType.WEIGHT);
		}
		
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
		
		// 遷移先のHTMLファイル名を返す
		return "Confirm";
	}
	
	/**
	 * 確認画面：戻るボタンクリック
	 * @param inputForm 入力画面情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	@PostMapping(value = "/confirm", params = "returnBtn")
	public String returnInput(InputForm inputForm, HttpSession session, Model model)
	{
		// 入力タイプの取得
		InputType inputType = (InputType)session.getAttribute("InputType");
		
		// メッセージの初期設定
		inputForm.setErrorLabel("");
		
		// 画面が身長入力画面の場合
		if (inputType.equals(InputType.HEIGHT)) {
			// Modelに登録
			model.addAttribute("inputForm", inputForm);
			
			// 遷移先のHTMLファイル名を返す
			return "InputHeight";
		}
		
		// 画面が体重入力画面の場合
		if (inputType.equals(InputType.WEIGHT)) {
			// Modelに登録
			model.addAttribute("inputForm", inputForm);
			
			// 遷移先のHTMLファイル名を返す
			return "InputWeight";
		}
		
		return "redirect:/";
	}
	
	/**
	 * 確認画面：確定ボタンクリック
	 * 入力値から、MTestItemテーブルの情報を更新し、
	 * 完了の場合、登録完了画面へ遷移する
	 * @param inputForm 入力画面情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	@PostMapping(value = "/confirm", params = "completeBtn")
	public String showComplete(InputForm inputForm, HttpSession session, Model model)
	{
		// 共通機能クラス変数
		CommonFunc comFunc = new CommonFunc();
		
		// 入力タイプの取得
		InputType inputType = (InputType)session.getAttribute("InputType");
		
		// 身長入力の場合
		if (inputType.equals(InputType.HEIGHT)) {
			// ユーザIDから対象検査項目情報を更新
			int resultInt = comFunc.updateHeight(session, mTestItemService, inputForm.getIntegerPart(), inputForm.getDecimalPart());
			
			// 更新件数が0件以下の場合
			if (resultInt <= 0) {
				// エラーメッセージの設定
				inputForm.setErrorLabel("データの更新に失敗しました。");
				
				// リダイレクト変数の設定
				model.addAttribute("inputType", inputType);
				
				// 確認画面に戻る
				return "InputHeight";
			}
		}
		
		// 体重入力の場合
		if (inputType.equals(InputType.WEIGHT)) {
			// ユーザIDから対象検査項目情報を更新
			int resultInt = comFunc.updateWeight(session, mTestItemService, inputForm.getIntegerPart(), inputForm.getDecimalPart());
			
			// 更新件数が0件以下の場合
			if (resultInt <= 0) {
				// エラーメッセージの設定
				inputForm.setErrorLabel("データの更新に失敗しました。");
				
				// リダイレクト変数の設定
				model.addAttribute("inputType", inputType);
				
				// 確認画面に戻る
				return "InputWeight";
			}
		}
		
		// 遷移先のHTMLファイル名を返す
		return "Complete";
	}
}
