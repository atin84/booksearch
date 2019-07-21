package com.atin.searchweb.auth.controller;

import com.atin.searchweb.auth.domain.User;
import com.atin.searchweb.auth.service.SecurityService;
import com.atin.searchweb.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {
	private final UserService userService;

	private final SecurityService securityService;

	@RequestMapping("/sign-in")
	public String signin(Model model, String logout) {
		if (logout != null) {
			model.addAttribute("logout", "You have been logged out successfully.");
		}
		return "signin";
	}

	// 로그인 실패시
	@RequestMapping(value = "/loginError")
	public String loginError(Model model, String username) {
		model.addAttribute("error", "Your username and password is invalid.");
		model.addAttribute("username", username);
		return "signin";
	}

	// 회원가입폼
	@GetMapping("/sign-up")
	public String signup(Model model) {
		model.addAttribute("userForm", new User());
		return "signup";
	}

	// 회원가입 처리 후 로그인
	@PostMapping("/sign-up")
	public String signup(@ModelAttribute("userForm") User userForm) {
		String password = userForm.getPassword();
		userService.saveUser(userForm);
		securityService.autologin(userForm.getUsername(), password);
		return "redirect:/main";
	}

	// user 사용자 테스트
	@GetMapping("/user")
	public String user() {
		return "/user/user";
	}

	// 권한없는 페이지를 들어갔을때
	@RequestMapping("/forbidden")
	public String access() {
		return "/forbidden";
	}
}
