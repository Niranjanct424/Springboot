package com.niranjan.executor.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_TBL")
public class User {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String email;
	private String gender;

}
