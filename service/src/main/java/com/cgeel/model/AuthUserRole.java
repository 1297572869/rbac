package com.cgeel.model;

import javax.persistence.*;


@Entity
@Table(name = "auth_user_role")
public class AuthUserRole {

	@Id
	@Column(name="id")
	private Long id;
	@Column(name="role_id")
	private Long roleId;
	@Column(name="user_id")
	private Long userId;

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setRoleId(Long roleId){
		this.roleId=roleId;
	}

	public Long getRoleId(){
		return roleId;
	}

	public void setUserId(Long userId){
		this.userId=userId;
	}

	public Long getUserId(){
		return userId;
	}

}
