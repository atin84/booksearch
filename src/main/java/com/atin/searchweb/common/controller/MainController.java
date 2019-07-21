package com.atin.searchweb.common.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

	@RequestMapping(value = "/main")
	public String main(String title, Integer pageNumber) {
		return "main";
	}
}
