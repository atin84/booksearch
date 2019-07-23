package com.atin.searchweb.auth.controller;

import com.atin.searchweb.auth.domain.Role;
import com.atin.searchweb.auth.domain.User;
import com.atin.searchweb.auth.service.SecurityService;
import com.atin.searchweb.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

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

	@RequestMapping(value = "/loginError")
	public String loginError(Model model, String username) {
		model.addAttribute("error", "Your username and password is invalid.");
		model.addAttribute("username", username);
		return "signin";
	}

	@GetMapping("/sign-up")
	public String signup(Model model) {
		model.addAttribute("userForm", new User());
		return "signup";
	}

	@PostMapping("/sign-up")
	public String signup(@ModelAttribute("userForm") User user) {
		String password = user.getPassword();

		Set<Role> rolesSet = new HashSet<Role>();
		rolesSet.add(new Role("ROLE_USER"));
		user.setRoles(rolesSet);

		userService.saveUser(user);
		securityService.autologin(user.getUsername(), password);
		return "redirect:/main";
	}

	@RequestMapping("/forbidden")
	public String access() {
		return "/forbidden";
	}
}
