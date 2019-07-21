package com.atin.searchweb.auth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
public class Role {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public Role(String name) {
		this.name = name;
	}
}