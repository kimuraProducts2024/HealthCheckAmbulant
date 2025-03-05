package com.healthcheck.ambulant.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.healthcheck.ambulant.entity.MTestItem;
import com.healthcheck.ambulant.entity.MUser;
import com.healthcheck.ambulant.form.InputForm;
import com.healthcheck.ambulant.form.InputForm.InputType;
import com.healthcheck.ambulant.repository.MTestItemMapper;

import jakarta.servlet.http.HttpSession;

/**
 * MTestItemサービスクラス
 */
@Service
public class MTestItemService {
	// ユーザマッパー定義
	@Autowired
	private MTestItemMapper mTestItemMapper;
	
	/**
	 * M_Test_Itemテーブルから対象ユーザを検索し、
	 * 検索結果を返す
	 * @param session セッション
	 * @return 検索結果のMTestItemオブジェクト
	 */
	public MTestItem selectMTestItem(HttpSession session) {
		// MUserセッション情報を取得
		MUser mUser = (MUser)session.getAttribute("MUser");
		
		// 検索結果のMTestItemオブジェクトを返す
		return mTestItemMapper.selectMTestItem(mUser.getUserId().toString());
	}
	
	/**
	 * 入力画面ごとにM_Test_Itemテーブルの対象列を更新
	 * @param inputForm 入力画面の各項目情報
	 * @param session セッション
	 * @param model モデル
	 * @return 更新件数
	 */
	public int updateMTestItem(InputForm inputForm, HttpSession session, Model model) {
		// 戻り値の更新件数
		int resultInt = 0;
		
		// 入力タイプの取得
		InputType inputType = (InputType)session.getAttribute("InputType");
		
		// 入力タイプ
		switch (inputType) {
			// 身長の場合
			case HEIGHT:
				// 身長の更新処理を実行し、更新件数を取得
				resultInt = updateHeight(session, inputForm.getIntegerPart(), inputForm.getDecimalPart());
				break;
			// 体重入力の場合
			case WEIGHT:
				// 体重の更新処理を実行し、更新件数を取得
				resultInt = updateWeight(session, inputForm.getIntegerPart(), inputForm.getDecimalPart());
				break;
			// 視力入力の場合
			case EYETEST:
				// 視力の更新処理を実行し、更新件数を取得
				resultInt = updateVision(session, 
									inputForm.getLeftIntegerPart(), inputForm.getLeftDecimalPart(),
									inputForm.getRightIntegerPart(), inputForm.getRightDecimalPart());
				break;
			// 聴力入力の場合
			case HEARINGTEST:
				// 聴力の更新処理を実行し、更新件数を取得
				break;
			// 血圧入力の場合
			case BLOODPRESSURE:
				// 血圧の更新処理を実行し、更新件数を取得
				break;
			// それ以外の場合
			default:
				break;
		}
		
		// 更新件数を返す
		return resultInt;
	}
	
	/**
	 * 身長入力画面の入力値から、M_Test_Itemテーブルの身長列を更新
	 * @param session セッション
	 * @param integerPart 身長整数部
	 * @param decimalPart 身長小数部
	 * @return 更新件数
	 */
	public int updateHeight(HttpSession session, int integerPart, int decimalPart) {
		// MUserセッション情報を取得
		MUser mUser = (MUser)session.getAttribute("MUser");
		
		// 身長の値に変換
		BigDecimal heightBg = new BigDecimal(String.valueOf(integerPart) + "." + String.valueOf(decimalPart));
		
		// ユーザIDから対象検査項目情報を更新
		int resultInt = mTestItemMapper.updateHeight(String.valueOf(mUser.getUserId()), heightBg);

		// 更新結果を返す
		return resultInt;
	}

	/**
	 * 体重入力画面の入力値から、M_Test_Itemテーブルの体重列を更新
	 * @param session セッション
	 * @param integerPart 体重整数部
	 * @param decimalPart 体重小数部
	 * @return 更新件数
	 */
	public int updateWeight(HttpSession session, int integerPart, int decimalPart) {
		// MUserセッション情報を取得
		MUser mUser = (MUser)session.getAttribute("MUser");
		
		// 体重の値に変換
		BigDecimal weightBg = new BigDecimal(String.valueOf(integerPart) + "." + String.valueOf(decimalPart));
		
		// ユーザIDから対象検査項目情報を更新
		int resultInt = mTestItemMapper.updateWeight(String.valueOf(mUser.getUserId()), weightBg);

		// 更新結果を返す
		return resultInt;
	}
	
	/**
	 * 視力入力画面の入力値から、M_Test_Itemテーブルの右目視力、左目視力列を更新
	 * @param session セッション
	 * @param inputPart 左目、右目の入力値配列
	 *         0：左目整数部、1：左目小数部、2：右目整数部、3：右目小数部
	 * @return 更新件数
	 */
	public int updateVision(HttpSession session, 
					Integer leftIntegerPart, String leftDecimalPart,
					Integer rightIntegerPart, String rightDecimalPart) {
		// MUserセッション情報を取得
		MUser mUser = (MUser)session.getAttribute("MUser");
		
		// 左目視力の値に変換
		BigDecimal visionLeftBg = new BigDecimal(String.valueOf(leftIntegerPart) + "." + leftDecimalPart);

		// 右目視力の値に変換
		BigDecimal visionRightBg = new BigDecimal(String.valueOf(rightIntegerPart) + "." + rightDecimalPart);
		
		// ユーザIDから対象検査項目情報を更新
		int resultInt = mTestItemMapper.updateVision(String.valueOf(mUser.getUserId()), visionLeftBg, visionRightBg);

		// 更新結果を返す
		return resultInt;
	}
}
