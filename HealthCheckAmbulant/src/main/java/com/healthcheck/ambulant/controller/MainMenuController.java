package com.healthcheck.ambulant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.healthcheck.ambulant.common.CommonFunc;
import com.healthcheck.ambulant.entity.M_Test_Item;
import com.healthcheck.ambulant.service.M_Test_ItemService;

import jakarta.servlet.http.HttpSession;

/**
 * メインメニューコントローラ
 * 初期表示、入力画面への遷移、ログアウト時の処理を行う
 */
@Controller
public class MainMenuController {
	// 検査項目情報
	M_Test_Item mTestItem;
	
	// ユーザ検査項目サービス定義
	@Autowired
	M_Test_ItemService mTestItemService;
	
	CommonFunc comFunc = new CommonFunc();
	
	/**
	 * メインメニュー画面を表示する
	 * @param session セッション
	 * @param mav モデルアンドビュー
	 * @return 遷移先のHTMLファイル名
	 */
	@GetMapping("/MainMenu")
	public ModelAndView showMainMenu(HttpSession session, ModelAndView mav) {
		// 入力画面タイプのセッションをnullに設定
		session.setAttribute("InputType", null);
		
		// メインメニュー画面を表示
		return mav;
	}
	
	/**
	 * 身長入力画面に遷移する
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	@GetMapping("/InputHeight") 
	public String showInputHeight(HttpSession session, Model model) {
		// 身長入力の初期処理
		comFunc.initInputHeight(session, model, mTestItem, mTestItemService);
		 
		// 遷移先のHTMLファイル名を返す
		return "InputHeight";
	}
	 
	/**
	 * 体重入力画面に遷移する
	 * @param session セッション
	 * @return 遷移先のHTMLファイル名
	 */
	@GetMapping("/InputWeight") 
	public String showInputWeight(HttpSession session, Model model) {
		// 体重入力の初期処理
		comFunc.initInputWeight(session, model, mTestItem, mTestItemService);
		
		// 遷移先のHTMLファイル名を返す
	    return "InputWeight";
	}
	
	/**
	 * 視力入力画面に遷移する
	 * @param session セッション
	 * @return 遷移先のHTMLファイル名
	 */
	@GetMapping("/InputEyeTest") 
	public String showInputEyeTest(HttpSession session, Model model) {
//		// M_Test_Item情報を取得
//		mTestItem = getMTestItem(session);
		
		// 遷移先のHTMLファイル名を返す
	    return "InputEyeTest";
	}
	
	/**
	 * 聴力入力画面に遷移する
	 * @param session セッション
	 * @return 遷移先のHTMLファイル名
	 */
	@GetMapping("/InputHearingTest") 
	public String showInputHearing(HttpSession session, Model model) {
//		// M_Test_Item情報を取得
//		mTestItem = getMTestItem(session);
		
		// 遷移先のHTMLファイル名を返す
	    return "InputHearingTest";
	}
	
	/**
	 * 血圧入力画面に遷移する
	 * @param session セッション
	 * @return 遷移先のHTMLファイル名
	 */
	@GetMapping("/InputBloodPressure") 
	public String showInputBloodPressure(HttpSession session, Model model) {
//		// M_Test_Item情報を取得
//		mTestItem = getMTestItem(session);
		
		// 遷移先のHTMLファイル名を返す
	    return "InputBloodPressure";
	}
	
	/**
	 * ログアウトボタン押下時処理
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
