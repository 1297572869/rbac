package com.cgeel.model;

import javax.persistence.*;


@Entity
@Table(name = "auth_role")
public class AuthRole {

	@Id
	@Column(name="id")
	private Long id;
	@Column(name="name")
	private String name;

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

}
