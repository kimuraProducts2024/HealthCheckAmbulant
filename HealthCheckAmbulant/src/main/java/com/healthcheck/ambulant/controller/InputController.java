package com.healthcheck.ambulant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.healthcheck.ambulant.common.CommonFunc;
import com.healthcheck.ambulant.entity.MTestItem;
import com.healthcheck.ambulant.form.InputForm;
import com.healthcheck.ambulant.service.MTestItemService;

import jakarta.servlet.http.HttpSession;

/**
 * 入力処理コントローラ
 * 確認画面、完了画面への遷移や処理を行う
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
	 * 各入力画面：確認ボタンクリック
	 * @param inputForm 入力画面情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	@PostMapping(value = "/input", params = "confirmBtn")
	public String showConfirm(InputForm inputForm, HttpSession session, Model model)
	{
		// 入力値チェックを行い、次画面への遷移先を返す
		return CommonFunc.getNextConfPageName(inputForm, session, model);
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
		// 入力画面情報を元に、入力画面へ戻る
		return CommonFunc.getPrevInputPageName(inputForm, session, model);
	}
	
	/**
	 * 確認画面：確定ボタンクリック
	 * 入力値から、MTestItemテーブルの情報を更新し、
	 * 完了の場合、完了画面へ遷移する
	 * @param inputForm 入力画面情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	@PostMapping(value = "/confirm", params = "completeBtn")
	public String showComplete(InputForm inputForm, HttpSession session, Model model)
	{
		// ユーザIDを元に、対象検査項目情報の更新処理を実施
		return CommonFunc.updateMTestItem(inputForm, session, model, mTestItem, mTestItemService);
	}
}
