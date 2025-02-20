package com.healthcheck.ambulant.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.healthcheck.ambulant.entity.MUser;
import com.healthcheck.ambulant.repository.MUserMapper;

import jakarta.servlet.http.HttpSession;

/**
 * MUserサービスクラス
 */
@Service
public class MUserService {
	// ユーザマッパー定義
	@Autowired
	private MUserMapper mUserMapper;
	
	/**
	 * 入力値チェック後、次ページへ遷移する
	 * ユーザID、パスワード未入力チェック、
	 * ユーザID存在チェック、パスワード整合性チェック
	 * OKの場合：メインメニュー画面へ遷移する
	 * NGの場合：ログイン画面に戻る
	 * @param userId ユーザID
	 * @param password パスワード
	 * @param redirectAttributes リダイレクト受渡しオブジェクト
	 * @param session セッション変数
	 * @return true：正常、false：異常
	 * @throws NoSuchAlgorithmException MessageDigest呼出に伴う例外
	 */
	public String sendNextPage(String userId, String password, RedirectAttributes redirectAttributes,
							HttpSession session) throws NoSuchAlgorithmException {
		// エラーメッセージクリア
		redirectAttributes.addFlashAttribute("errorLabel", "");
		
		// ユーザID未入力チェック
		if (userId == null || userId.isBlank()) {
			// パスワード受渡し
			redirectAttributes.addFlashAttribute("password", password);
			
			// エラーメッセージの設定
			redirectAttributes.addFlashAttribute("errorLabel", "ユーザIDが未入力です。");
			
			// ログイン画面に戻る
			return "redirect:";
		}
		
		// パスワード未入力チェック
		if (password == null || password.isBlank()) {
			// ユーザID受渡し
			redirectAttributes.addFlashAttribute("userId", userId);
			
			// エラーメッセージの設定
			redirectAttributes.addFlashAttribute("errorLabel", "パスワードが未入力です。");
			
			// ログイン画面に戻る
			return "redirect:";
		}
		
		// MUserクラスオブジェクト
		MUser mUser = null;
		
		// ユーザIDから対象ユーザを検索
		mUser = mUserMapper.selectMUser(userId);
		
		// 該当ユーザ存在チェック
		// ユーザが存在しない場合
		if (mUser == null) {
			// ユーザID受渡し
			redirectAttributes.addFlashAttribute("userId", userId);
			
			// パスワード受渡し
			redirectAttributes.addFlashAttribute("password", password);
			
			// エラーメッセージの設定
			redirectAttributes.addFlashAttribute("errorLabel", "該当ユーザは存在しません。");
			
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
			
			// エラーメッセージの設定
			redirectAttributes.addFlashAttribute("errorLabel", "パスワードが異なります。");
			
			// ログイン画面に戻る
			return "redirect:";
		}
		
		// MUser情報をセッションに設定
		session.setAttribute("MUser", mUser);
		
		// メインメニューに遷移する
		return "MainMenu";
	}
}
