package com.healthcheck.ambulant.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 入力画面の各項目情報クラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputForm {
	// 入力画面タイプ
	public InputType inputType;
	
	// 画面表示文字列
	public String labelPart;
	
	// 身長入力：整数部
	private Integer integerPart;
	
	// 身長入力：小数部
	private Integer decimalPart;
	
	// メッセージラベル
	private String errorLabel;
	
	/*
	 * 入力画面タイプ
	 */
	public enum InputType {
		HEIGHT,				// 身長入力
		WEIGHT,				// 体重入力
		EYETEST,			// 視力入力
		HEARINGTEST,		// 聴力入力
		BLOODPRESSURE		// 血圧入力
	}
}
