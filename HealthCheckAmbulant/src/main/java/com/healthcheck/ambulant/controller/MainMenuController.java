package com.healthcheck.ambulant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.healthcheck.ambulant.common.CommonFunc;
import com.healthcheck.ambulant.entity.MTestItem;
import com.healthcheck.ambulant.form.InputForm;
import com.healthcheck.ambulant.service.MTestItemService;

import jakarta.servlet.http.HttpSession;

/**
 * メインメニューコントローラ
 * 初期表示、各入力画面への遷移、ログアウト時の処理を行う
 */
@Controller
public class MainMenuController {
	// 検査項目情報
	MTestItem mTestItem;
	
	// ユーザ検査項目サービス定義
	@Autowired
	MTestItemService mTestItemService;
	
	/**
	 * 登録完了画面：メインメニュー画面に遷移する
	 * @return 遷移先のHTMLファイル名
	 */
	@GetMapping("/mainmenu")
	public String showMainMenu() {
		return "MainMenu.html";
	}
	
	/**
	 * メインメニュー画面：クリックしたリンクの、リンク先に遷移する
	 * @param id リンクの種類を示すID
	 * @param inputForm 入力画面の各項目情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	@GetMapping("/transition/{id}")
	public String showNextPage(@PathVariable("id") Integer id, 
			InputForm inputForm, HttpSession session, Model model) {
		// エラーメッセージをクリアする
		inputForm.setErrorLabel("");
		
		// 入力画面の各項目情報をモデルに追加する
		model.addAttribute("inputForm", inputForm);
		
		// 共通機能クラスインスタンス生成
		CommonFunc comFunc = new CommonFunc();
		
		// 各入力画面に遷移する際の初期処理
		comFunc.initInput(id, session, model, mTestItem, mTestItemService);
		
		// 遷移先のHTMLファイル名を返す
		return comFunc.getNextPageName(id);
	}

	/**
	 * メインメニュー画面：ログアウトボタン押下時処理
	 * セッションを削除し、ログアウト
	 * ログイン画面を表示する
	 * @param session セッション
	 * @return ログイン画面のHTMLファイル名
	 */
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		// セッションをクリアする
		session = null;
		
		// ログイン画面に遷移する
		return "redirect:/";
	}
}
