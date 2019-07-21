package com.atin.searchweb.auth.repository;

import com.atin.searchweb.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}