package com.healthcheck.ambulant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

/**
 * メインメニューコントローラ
 */
@Controller
public class MainMenuController {
	// メインメニュー画面表示
	@GetMapping("/MainMenu")
	public ModelAndView showMainMenu(ModelAndView mav) {
		// メインメニュー画面を表示
		return mav;
	}
	
	/**
	 * クリックしたリンクのリンク先に遷移する
	 * @param id リンクの種類を示すID
	 * @return 遷移先のHTMLファイル名
	 */
	@GetMapping("/transition/{id}")
	public String screenTransition(@PathVariable("id") Integer id) {
		// 遷移先のHTMLファイル名
		String screenName = null;
		
		// クリックしたリンクのID
		switch (id) {
			// 身長入力の場合
			case 1:
				screenName = "InputHeight.html";
				break;
			// 体重入力の場合
			case 2:
				screenName = "InputWidth.html";
				break;
			// 視力入力の場合
			case 3:
				screenName = "InputEyeTest.html";
				break;
			// 聴力入力の場合
			case 4:
				screenName = "InputHearingTest.html";
				break;
			// 血圧入力の場合
			case 5:
				screenName = "InputBloodPressure.html";
				break;
			// それ以外の場合
			default:
				screenName = "MainMenu.html";
		}
		
		// 遷移先のHTMLファイル名を返す
		return screenName;
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
		return "Login.html";
	}
	
	
}
