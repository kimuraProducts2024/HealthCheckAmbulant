/**
 * DOMツリー読み込み後
 * 各要素の取得、各イベントリスナーの追加
 */
window.addEventListener("DOMContentLoaded", function() {
	// 入力画面要素
	// 身長・体重整数部テキスト要素を取得
	integerPartNum = document.getElementById("integerPart");
	
	// 身長・体重少数部テキスト要素を取得
	decimalPartNum = document.getElementById("decimalPart");
	
	// 左目視力整数部テキスト要素を取得
	leftEyeIntegerPartNum = document.getElementById("leftIntegerPart");
	
	// 左目視力少数部テキスト要素を取得
	leftEyeDecimalPartNum = document.getElementById("leftDecimalPart");
	
	// 右目視力整数部テキスト要素を取得
	rightEyeIntegerPartNum = document.getElementById("rightIntegerPart");
	
	// 右目視力少数部テキスト要素を取得
	rightEyeDecimalPartNum = document.getElementById("rightDecimalPart");
	
	// 身長・体重整数部テキスト要素がnullでない場合
	if (integerPartNum != null) {
		// 入力時、数値以外の入力不可にするEventListenerを追加
		integerPartNum.addEventListener("input", () => {
			integerPartNum.value = integerPartNum.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		});
	}
	
	// 身長・体重小数部テキスト要素がnullでない場合
	if (decimalPartNum != null) {
		// 入力時、数値以外の入力不可にするEventListenerを追加
		decimalPartNum.addEventListener("input", () => {
			decimalPartNum.value = decimalPartNum.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		});
	} 
	
	// 左目視力整数部テキスト要素がnullでない場合
	if (leftEyeIntegerPartNum != null) {
		// 入力時、数値以外の入力不可にするEventListenerを追加
		leftEyeIntegerPartNum.addEventListener("input", () => {
			leftEyeIntegerPartNum.value = leftEyeIntegerPartNum.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		});
	}
	
	// 左目視力小数部テキスト要素がnullでない場合
	if (leftEyeDecimalPartNum != null) {
		// 入力時、数値以外の入力不可にするEventListenerを追加
		leftEyeDecimalPartNum.addEventListener("input", () => {
			leftEyeDecimalPartNum.value = leftEyeDecimalPartNum.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		});
	}
	
	// 右目視力整数部テキスト要素がnullでない場合
	if (rightEyeIntegerPartNum != null) {
		// 入力時、数値以外の入力不可にするEventListenerを追加
		rightEyeIntegerPartNum.addEventListener("input", () => {
			rightEyeIntegerPartNum.value = rightEyeIntegerPartNum.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		});
	}
	
	// 右目視力小数部テキスト要素がnullでない場合
	if (rightEyeDecimalPartNum != null) {
		// 入力時、数値以外の入力不可にするEventListenerを追加
		rightEyeDecimalPartNum.addEventListener("input", () => {
			rightEyeDecimalPartNum.value = rightEyeDecimalPartNum.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		});
	} 
})