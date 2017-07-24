package com.cgeel.model;

import javax.persistence.*;


@Entity
@Table(name = "auth_role_function")
public class AuthRoleFunction {

	@Id
	@Column(name="id")
	private Long id;
	@Column(name="role_id")
	private Long roleId;
	@Column(name="function_id")
	private Long functionId;
	@Column(name="status")
	private Integer status;

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

	public void setFunctionId(Long functionId){
		this.functionId=functionId;
	}

	public Long getFunctionId(){
		return functionId;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

}
