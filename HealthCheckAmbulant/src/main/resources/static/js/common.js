/**
 * DOMツリー読み込み後
 * 各要素の取得、各イベントリスナーの追加
 */
window.addEventListener("DOMContentLoaded", function() {
	// 身長入力画面要素
	// 身長整数部テキスト要素を取得
	integerPartNum = document.getElementById("integerPart");
	
	// 身長少数部テキスト要素を取得
	decimalPartNum = document.getElementById("decimalPart");
	
	// 身長整数部テキスト要素がnullでない場合
	if (integerPartNum != null) {
		// 入力時、数値以外の入力不可にするEventListenerを追加
		integerPartNum.addEventListener("input", () => {
			integerPartNum.value = integerPartNum.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		});
	}
	
	// 身長小数部テキスト要素がnullでない場合
	if (decimalPartNum != null) {
		// 入力時、数値以外の入力不可にするEventListenerを追加
		decimalPartNum.addEventListener("input", () => {
			decimalPartNum.value = decimalPartNum.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
		});
	} 
	
})