package com.atin.searchweb.auth.service;

public interface SecurityService {
	public String findLoggedInUsername();
	void autologin(String username, String password);
}