package com.healthcheck.ambulant.controller;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.healthcheck.ambulant.entity.MUser;
import com.healthcheck.ambulant.service.MUserService;

import jakarta.servlet.http.HttpSession;

/**
 * ログインコントローラ
 * 初期表示、ログイン時の処理を行う
 */
@Controller
public class LoginController {
	// ユーザサービス定義
	@Autowired
	MUserService mUserService;
	
	// ログイン画面表示
	@GetMapping("/")
	public String showLogin() {
		// ログイン画面を表示
		return "Login";
	}
	
	// ログインボタン押下時
	@PostMapping("/login")
	public String login(@RequestParam String userId, @RequestParam String password, 
							RedirectAttributes redirectAttributes, HttpSession session) throws Exception {
		// ユーザIDから対象ユーザを検索
		MUser mUser = mUserService.selectMUser(userId);
		
		// 該当ユーザ存在チェック
		// ユーザが存在しない場合
		if (mUser == null) {
			// ユーザID受渡し
			redirectAttributes.addFlashAttribute("userId", userId);
			
			// パスワード受渡し
			redirectAttributes.addFlashAttribute("password", password);
			
			// エラーメッセージステータス受渡し
			redirectAttributes.addFlashAttribute("errorstatus", "1");
			
			// ログイン画面に戻る
			return "redirect:";
		}
		
		// パスワードチェック
		// MessageDigest
		var md = MessageDigest.getInstance("SHA-512");
		
		// パスワードからbyte配列を取得
		byte[] cipherBytes = md.digest(password.getBytes());
		StringBuilder sb = new StringBuilder();
		
		// byte配列を文字列に変換
		for(int i=0; i<cipherBytes.length;i++) {
			sb.append(String.format("%02x", cipherBytes[i]&0xff));
		}
		
		// 入力したパスワードの値がDBのパスワードの値と異なる場合
		if (!mUser.getPassword().equals(sb.toString())) {
			// ユーザID受渡し
			redirectAttributes.addFlashAttribute("userId", userId);
			
			// パスワード受渡し
			redirectAttributes.addFlashAttribute("password", password);
			
			// エラーメッセージステータス受渡し
			redirectAttributes.addFlashAttribute("errorstatus", "2");
			
			// ログイン画面に戻る
			return "redirect:";
		}
		
		// MUser情報をセッションに設定
		session.setAttribute("mUser", mUser);
		
		// メインメニューに遷移する
		return "redirect:/MainMenu";
	}
	
}
