package com.atin.searchweb.auth.service;

import com.atin.searchweb.auth.domain.Role;
import com.atin.searchweb.auth.domain.User;
import com.atin.searchweb.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		Set<Role> rolesSet = new HashSet<Role>();
		rolesSet.add(new Role("ROLE_USER"));

		user.setRoles(rolesSet);
		userRepository.save(user);
	}
}