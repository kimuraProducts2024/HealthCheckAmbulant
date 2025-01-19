package com.healthcheck.ambulant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
