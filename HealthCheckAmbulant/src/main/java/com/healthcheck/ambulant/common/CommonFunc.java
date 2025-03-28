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
	private static String screenName = null;
	
	/**
	 * メインメニュー画面：リンククリック時の処理
	 * 各入力画面に遷移する際の初期処理
	 * MTestItem情報を取得し、Model、sessionに登録する
	 * @param id クリックしたリンクのID
	 * @param session セッション
	 * @param model モデル
	 * @param mTestItem 検査項目情報
	 * @param mTestItemService 検査項目サービス
	 */
	public static void initInput(Integer id, HttpSession session, Model model, 
						MTestItem mTestItem, MTestItemService mTestItemService) {
		// 入力画面情報
		InputForm inputForm = new InputForm();
		
        // MTestItem情報を取得
		mTestItem = mTestItemService.selectMTestItem(session);
		
		// 入力画面情報を設定
		setInputInfo(id, mTestItem, inputForm);
		
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
		
		// 入力画面タイプを設定
		setInputType(id, session);
	}
	
	/**
	 * メインメニュー画面：リンククリック時の処理
	 * いずれかのリンクをクリックした際、
	 * リンクIDをもとに、遷移先HTMLファイル名を設定し、返却する
	 * @param id クリックしたリンクのID
	 * @return 遷移先のHTMLファイル名
	 */
	public static String getNextPageName(Integer id) {
		// 遷移先のHTMLファイル名
		screenName = null;
		
		// 遷移先のHTMLファイル名を設定する
		setScreenName(id);
		
		// 遷移先のHTMLファイル名を返す
		return screenName;
	}
	
	/**
	 * 各入力画面：確認ボタンクリック時の処理
	 * 入力チェックを行い、
	 * エラーの場合は、エラーメッセージを設定し、
	 * 入力画面の遷移先を返す
	 * エラーがない場合は、確認画面の遷移先を返す
	 * @param inputForm 入力画面の各項目情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	public static String getNextConfPageName(InputForm inputForm, HttpSession session, Model model) {
		// 入力画面タイプをsessionから取得
		InputType inputType = (InputType)session.getAttribute("InputType");
		
		// 遷移先画面情報を設定する
		setNextPageInfo(inputType, inputForm, model);
				
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
		
		// 遷移先のHTMLファイル名を返す
		return screenName;
	}
	
	/**
	 * 確認画面：戻るボタンクリック時の処理
	 * InputTypeを元に、遷移先の入力画面を設定・返却する
	 * @param inputForm 入力画面の各項目情報
	 * @param session セッション
	 * @param model モデル
	 * @return 遷移先のHTMLファイル名
	 */
	public static String getPrevInputPageName(InputForm inputForm, HttpSession session, Model model) {
		// 入力画面タイプの取得
		InputType inputType = (InputType)session.getAttribute("InputType");
		
		// メッセージのクリア
		inputForm.setErrorLabel("");
		
		// 遷移先のHTMLファイル名を設定する
		setScreenName(inputType);
		
		// 遷移先のHTMLファイル名を返す
		return screenName;
	}
	
	/**
	 * 確認画面：確定ボタンクリック時の処理
	 * 入力内容を元に、MTestItem情報を更新する
	 * 失敗の場合、入力画面へ遷移する
	 * 成功の場合、完了画面へ遷移する
	 * @param inputForm 入力画面の各項目情報
	 * @param session セッション
	 * @param model モデル
	 * @param mTestItem 検査項目情報
	 * @param mTestItemService ユーザ検査項目サービス
	 * @return 遷移先のHTMLファイル名
	 */
	public static String updateMTestItem(InputForm inputForm, HttpSession session, Model model,
			MTestItem mTestItem, MTestItemService mTestItemService) {
		// 入力画面タイプの取得
		InputType inputType = (InputType)session.getAttribute("InputType");
		
		// 対象検査項目情報の更新件数
		int resultInt = 0;
		
		// ユーザIDから対象検査項目情報を更新
		resultInt = mTestItemService.updateMTestItem(inputForm, session, model);
		
		// 更新件数が0件以下の場合
		if (resultInt <= 0) {
			// エラーメッセージの設定
			inputForm.setErrorLabel("データの更新に失敗しました。");
			
			// Modelに登録
			model.addAttribute("inputType", inputType);
			
			// 遷移先のHTMLファイル名を設定する
			setScreenName(inputType);
			
		}
		// 更新件数が1件以上の場合
		else {
			// 完了画面に設定
			screenName = "Complete";
		}
		
		// 遷移先のHTMLファイル名を返す
		return screenName;
	}
	
	/*
	 * mTestItem情報から、InputFormを設定する
	 * @param id クリックしたリンクのID
	 * @param mTestItem 検査項目テーブルオブジェクト
	 * @param inputForm 入力画面情報
	 */
	private static void setInputInfo(int id, MTestItem mTestItem, InputForm inputForm) {
		// 取得したMTestItem情報がnullの場合
		if (mTestItem == null) {
			// 処理をせず終了
			return;
		}
		
		// クリックしたリンクのID
		switch (id) {
			// 身長入力の場合
			case 1:
				// 身長がnullでない場合
				if (mTestItem.getHeight() != null) {
					// 身長小数部1桁を10倍にした値を取得
					BigDecimal tenDecimal = new BigDecimal("10.0");
					BigDecimal decimalHeight = mTestItem.getHeight().remainder(BigDecimal.ONE).multiply(tenDecimal);
					
					// 身長の整数部を設定
					inputForm.setIntegerPart(mTestItem.getHeight().intValue());
					
					// 身長の小数部を設定
					inputForm.setDecimalPart(decimalHeight.intValue());
				}
				break;
			// 体重入力の場合
			case 2:
				// 体重がnullでない場合
				if (mTestItem.getWeight() != null) {
					// 体重小数部1桁を10倍にした値を取得
					BigDecimal tenDecimal = new BigDecimal("10.0");
					BigDecimal decimalWeight = mTestItem.getWeight().remainder(BigDecimal.ONE).multiply(tenDecimal);
					
					// 体重の整数部を設定
					inputForm.setIntegerPart(mTestItem.getWeight().intValue());
					
					// 体重の小数部を設定
					inputForm.setDecimalPart(decimalWeight.intValue());
				}
				break;
			// 視力入力の場合
			case 3:
				// 左目視力がnullでない場合
				if (mTestItem.getVisionLeft() != null) {
					// 左目視力小数部1桁を1000倍にした値を取得
					BigDecimal thousandDecimal = new BigDecimal("1000.0");
					BigDecimal decimalVision = mTestItem.getVisionLeft().remainder(BigDecimal.ONE).multiply(thousandDecimal);
					
					// 取得した左目視力小数部を3桁0埋めする
					String strVisionLeft = String.format("%03d", decimalVision.intValue());
					
					// 左目視力の整数部を設定
					inputForm.setLeftIntegerPart(mTestItem.getVisionLeft().intValue());
					
					// 左目視力の小数部を設定
					inputForm.setLeftDecimalPart(strVisionLeft);
				}
				
				// 右目視力がnullでない場合
				if (mTestItem.getVisionRight() != null) {
					// 右目視力小数部1桁を1000倍にした値を取得
					BigDecimal thousandDecimal = new BigDecimal("1000.0");
					BigDecimal decimalVision = mTestItem.getVisionRight().remainder(BigDecimal.ONE).multiply(thousandDecimal);
					
					// 取得した右目視力小数部を3桁0埋めする
					String strVisionRight = String.format("%03d", decimalVision.intValue());
					
					// 右目視力の整数部を設定
					inputForm.setRightIntegerPart(mTestItem.getVisionRight().intValue());
					
					// 右目視力の小数部を設定
					inputForm.setRightDecimalPart(strVisionRight);
				}
				break;
			// 聴力入力の場合
			case 4:
				// 聴力がnullでない場合
				if (mTestItem.getHearing() != null) {
					// 聴力を設定
					inputForm.setIntHearing(mTestItem.getHearing());
				}
				break;
			// 血圧入力の場合
			case 5:
				// 最大血圧がnullでない場合
				if (mTestItem.getSystolicBloodPressure() != null) {
					// 最大血圧を設定
					inputForm.setSystolicBloodPressure(mTestItem.getSystolicBloodPressure());
				}
				
				// 最小血圧がnullでない場合
				if (mTestItem.getDiastolicBloodPressure() != null) {
					// 最小血圧を設定
					inputForm.setDiastolicBloodPressure(mTestItem.getDiastolicBloodPressure());
				}
				break;
		}
	}
	
	/**
	 * 遷移先画面情報を設定する
	 * 入力画面で確認ボタンをクリックした際、
	 * 入力チェックを行い、遷移先を設定する
	 * @param inputType 入力画面タイプ
	 * @param inputForm 入力画面の各項目情報
	 * @param model モデル
	 */
	private static void setNextPageInfo(InputType inputType, InputForm inputForm, Model model) {
		// 入力チェックがエラーの場合
		if (!inputCheck(inputType, inputForm, model)) {
			// 各入力画面を設定
			setScreenName(inputType);
			return;
		}
		
		// 確認画面情報を設定
		setConfirmInfo(inputType, inputForm);
		
		// 確認画面を設定
		screenName = "Confirm";
	}
	
	/**
	 * 入力チェックを行う
	 * エラーの場合は、エラーメッセージを設定し、falseを返す
	 * エラーがない場合は、trueを返す
	 * @param inputType 入力画面タイプ
	 * @param inputForm 入力画面の各項目情報
	 * @param model モデル
	 * @return エラーなし：true、false：エラー
	 */
	private static Boolean inputCheck(InputType inputType, InputForm inputForm, Model model) {
		// 入力チェック結果
		Boolean resultBoo = true;
		
		// 入力画面タイプ
		switch(inputType) {
			// 身長の場合
			case HEIGHT:
				// 身長整数部が未入力の場合
				if (inputForm.getIntegerPart() == null) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("身長整数部の値を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 身長小数部が未入力の場合
				if (inputForm.getDecimalPart() == null) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("身長小数部の値を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 身長整数部が4桁以上の場合
				if (inputForm.getIntegerPart() >= 1000) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("身長整数部は3桁以下で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 身長小数部が2桁以上の場合
				if (inputForm.getDecimalPart() >= 10) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("身長小数部は1桁で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				break;
			// 体重の場合
			case WEIGHT:
				// 体重整数部が未入力の場合
				if (inputForm.getIntegerPart() == null) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("体重整数部の値を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 体重小数部が未入力の場合
				if (inputForm.getDecimalPart() == null) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("体重小数部の値を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 体重整数部が4桁以上の場合
				if (inputForm.getIntegerPart() >= 1000) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("体重整数部は3桁以下で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 体重整数部が2桁以上の場合
				if (inputForm.getDecimalPart() >= 10) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("体重小数部は1桁で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				break;
			// 視力の場合
			case EYETEST:
				// 左目視力整数部が未入力の場合
				if (inputForm.getLeftIntegerPart() == null) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("左目整数部の値を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 左目視力小数部が未入力の場合
				if (inputForm.getLeftDecimalPart() == null) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("左目小数部の値を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 右目視力整数部が未入力の場合
				if (inputForm.getRightIntegerPart() == null) {
					// エラーメッセージを設定し、モデルに登録
					setLowerErrorLabel("右目整数部の値を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 右目視力小数部が未入力の場合
				if (inputForm.getRightDecimalPart() == null) {
					// エラーメッセージを設定し、モデルに登録
					setLowerErrorLabel("右目小数部の値を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 左目視力整数部が3桁以上の場合
				if (inputForm.getLeftIntegerPart() >= 100) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("左目整数部は2桁以下で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 左目視力小数部が4桁以上の場合
				if (Integer.parseInt(inputForm.getLeftDecimalPart()) >= 1000) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("左目小数部は3桁以下で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 右目視力整数部が3桁以上の場合
				if (inputForm.getRightIntegerPart() >= 100) {
					// エラーメッセージを設定し、モデルに登録
					setLowerErrorLabel("右目整数部は2桁以下で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 右目視力小数部が4桁以上の場合
				if (Integer.parseInt(inputForm.getRightDecimalPart()) >= 1000) {
					// エラーメッセージを設定し、モデルに登録
					setLowerErrorLabel("右目小数部は3桁以下で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				break;
			// 聴力の場合
			case HEARINGTEST:
				// 聴力が未選択の場合
				if (inputForm.getIntHearing() == 0) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("聴力を選択", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				break;
			// 血圧の場合
			case BLOODPRESSURE:
				// 最大血圧が未入力の場合
				if (inputForm.getSystolicBloodPressure() == null) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("最大血圧を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 最小血圧が未入力の場合
				if (inputForm.getDiastolicBloodPressure() == null) {
					// エラーメッセージを設定し、モデルに登録
					setLowerErrorLabel("最小血圧を入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 最大血圧が4桁以上の場合
				if (inputForm.getSystolicBloodPressure() >= 1000) {
					// エラーメッセージを設定し、モデルに登録
					setErrorLabel("最大血圧は3桁以下で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				
				// 最小血圧が4桁以上の場合
				if (inputForm.getDiastolicBloodPressure() >= 1000) {
					// エラーメッセージを設定し、モデルに登録
					setLowerErrorLabel("最小血圧は3桁以下で入力", inputForm, model);
					
					// 入力チェック結果をfalseに設定
					resultBoo = false;
					break;
				}
				break;
		}
		
		return resultBoo;
	}
	
	/**
	 * 確認画面情報の設定を行う
	 * 入力画面タイプを元に、ラベル文字列を設定、
	 * inputFormに入力画面タイプを設定する
	 * @param inputType 入力画面タイプ
	 * @param inputForm 入力画面の各項目情報
	 */
	private static void setConfirmInfo(InputType inputType, InputForm inputForm) {
		// 入力画面タイプ
		switch(inputType) {
			// 身長の場合
			case HEIGHT:
				// 画面表示文字列を身長に設定
				inputForm.setLabelPart("身長");
				
				// InputTypeを設定
				inputForm.setInputType(InputType.HEIGHT);
				break;
			// 体重の場合
			case WEIGHT:
				// 画面表示文字列を体重に設定
				inputForm.setLabelPart("体重");
				
				// InputTypeを設定
				inputForm.setInputType(InputType.WEIGHT);
				break;
			// 視力の場合
			case EYETEST:
				// 画面表示文字列を視力に設定
				inputForm.setLabelPart("視力");
				
				// InputTypeを設定
				inputForm.setInputType(InputType.EYETEST);
				break;
			// 聴力の場合
			case HEARINGTEST:
				// 画面表示文字列を聴力に設定
				inputForm.setLabelPart("聴力");
				
				// InputTypeを設定
				inputForm.setInputType(InputType.HEARINGTEST);
				break;
			// 血圧の場合
			case BLOODPRESSURE:
				// 画面表示文字列を血圧に設定
				inputForm.setLabelPart("血圧");
				
				// InputTypeを設定
				inputForm.setInputType(InputType.BLOODPRESSURE);
				break;
		}
	}
	
	/*
	 * クリックしたリンクのIDによって、InputTypeを設定する
	 * @param id クリックしたリンクのID
	 * @param session セッション
	 */
	private static void setInputType(int id, HttpSession session) {
		// 入力画面タイプ
		InputType inputType = null;
				
		// クリックしたリンクのID
		switch (id) {
			// 身長入力の場合
			case 1:
				// 入力画面タイプを設定
				inputType = InputType.HEIGHT;
				break;
			// 体重入力の場合
			case 2:
				// 入力画面タイプを設定
				inputType = InputType.WEIGHT;
				break;
			// 視力入力の場合
			case 3:
				// 入力画面タイプを設定
				inputType = InputType.EYETEST;
				break;
			// 聴力入力の場合
			case 4:
				// 入力画面タイプを設定
				inputType = InputType.HEARINGTEST;
				break;
			// 血圧入力の場合
			case 5:
				// 入力画面タイプを設定
				inputType = InputType.BLOODPRESSURE;
				break;
		}
		
		// 入力画面タイプをセッションに設定
		session.setAttribute("InputType", inputType);
	}
	
	/*
	 * リンクのIDをもとに、
	 * 遷移先のHTMLファイル名を設定する
	 * @param inputType 入力画面タイプ
	 */
	private static void setScreenName(int id) {
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
	}
	
	/*
	 * 入力画面タイプをもとに、
	 * 遷移先のHTMLファイル名を設定する
	 * @param inputType 入力画面タイプ
	 */
	private static void setScreenName(InputType inputType) {
		// 入力画面タイプ
		switch(inputType) {
			// 身長の場合
			case HEIGHT:
				// 身長入力画面に設定
				screenName = "InputHeight";
				break;
			// 体重の場合
			case WEIGHT:
				// 体重入力画面に設定
				screenName = "InputWeight";
				break;
			// 視力の場合
			case EYETEST:
				// 視力入力画面に設定
				screenName = "InputEyeTest";
				break;
			// 聴力の場合
			case HEARINGTEST:
				// 聴力入力画面に設定
				screenName = "InputHearingTest";
				break;
			// 血圧の場合
			case BLOODPRESSURE:
				// 血圧入力画面に設定
				screenName = "InputBloodPressure";
				break;
		}
	}
	
	/**
	 * InputFormのエラーラベルに、
	 * 引数のエラーメッセージを設定し、モデルに登録する
	 * @param strMessage エラーメッセージ文字列
	 * @param inputForm 入力画面の各項目情報
	 * @param model モデル
	 */
	private static void setErrorLabel(String strMessage, InputForm inputForm, Model model) {
		// エラーメッセージの設定
		inputForm.setErrorLabel(strMessage + "してください。");
		
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
	}
	
	/**
	 * InputFormの下部エラーラベルに、
	 * 引数のエラーメッセージを設定し、モデルに登録する
	 * @param strMessage エラーメッセージ文字列
	 * @param inputForm 入力画面の各項目情報
	 * @param model モデル
	 */
	private static void setLowerErrorLabel(String strMessage, InputForm inputForm, Model model) {
		// エラーメッセージの設定
		inputForm.setLowerErrorLabel(strMessage + "してください。");
		
		// Modelに登録
		model.addAttribute("inputForm", inputForm);
	}
}
