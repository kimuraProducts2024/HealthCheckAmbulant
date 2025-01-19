// エラーダイアログ
let errorDialog;

// エラーダイアログ内の閉じるボタン
let closeBtn;

// エラーダイアログ内のエラーメッセージ
let errorMessage;

/**
 * DOMツリー読み込み後
 * 各要素の取得、各イベントリスナーの追加
 */
window.addEventListener("DOMContentLoaded", function() {
	// ログインボタン要素を取得
	loginBtn = document.getElementById("loginBtn");
	
	// エラーダイアログ要素を取得
	errorDialog = document.getElementById("dialog");
	
	// エラーダイアログ内の閉じるボタン要素を取得
	closeBtn = document.getElementById("closeBtn");
	
	// エラーダイアログのエラーメッセージ要素を取得
	errorMessage = document.getElementById("errorMessage");
	
	// submit時のEventListenerを追加
	window.addEventListener("onsubmit", txtEmptyCheck);
	
	// エラーダイアログを移動するEventListenerを追加
	errorDialog.addEventListener("pointermove", (event) => { 
		dialogMove(event);
	});	
	
	// ボタンフォーカス時の色を設定するEventListenerを追加
	closeBtn.addEventListener("mouseover", () => {
		closeBtn.style.backgroundColor = "#FFD700";
	});
	
	// ボタンロストフォーカス時の色を設定するEventListenerを追加
	closeBtn.addEventListener("mouseleave", () => {
		closeBtn.style.backgroundColor = "#FFCC66";
	});

	// エラーダイアログを閉じるEventListenerを追加
	closeBtn.addEventListener("click", () => {
		errorDialog.close();
	});
});

/**
 * ユーザID、パスワード空文字チェック
 */
function txtEmptyCheck() {
	// ユーザID要素を取得
	let txtUserId = document.getElementById("userId");
	
	// パスワード要素を取得
	let txtPassword = document.getElementById("password");

	// ユーザIDが未入力の場合
	if (txtUserId.value == "") {
		// メッセージを設定
		errorMessage.innerHTML = "ユーザIDが未入力です。";
		
		// エラーダイアログを開く
		errorDialog.showModal();
		
		// ユーザIDテキストボックスにフォーカスする
		txtUserId.focus();
		
		// falseを返し、サーバ処理をしないようにする
		return false;
	}
	
	// パスワードが未入力の場合
	if (txtPassword.value == "") {
		// メッセージを設定
		errorMessage.innerHTML = "パスワードが未入力です。";
		
		// エラーダイアログを開く
		errorDialog.showModal();
		
		// パスワードテキストボックスにフォーカスする
		txtPassword.focus();
		
		// falseを返し、サーバ処理をしないようにする
		return false;
	}
}

/**
 * エラーダイアログをマウスでDrag＆Dropして
 * 移動させる処理
 */
function dialogMove(event) {
	// マウスドラッグの場合
    if(event.buttons){
		// エラーダイアログのX座標を設定
        errorDialog.style.left = (event.pageX - errorDialog.offsetLeft) + "px";
        
        // エラーダイアログのY座標を設定
        errorDialog.style.top = (event.pageY - errorDialog.offsetTop) + "px";
        
        // エラーダイアログをabsoluteにする
        errorDialog.style.position = 'absolute';
        
        // エラーダイアログを前に表示させる
        errorDialog.style.zIndex = 1000;
        
        // ドラッグを不可にする
        errorDialog.draggable = false;
        
        // pointerCaptureを設定する
        errorDialog.setPointerCapture(event.pointerId);
    }
}
