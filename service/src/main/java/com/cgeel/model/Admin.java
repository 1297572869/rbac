package com.cgeel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "sys_admin")
public class Admin implements Serializable{

	@Id
	@Column(name="id")
	private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="phone")
	private String phone;
	@Column(name="password")
	private String password;
	@Column(name="status")
	private Integer status;
	@Column(name="group_id")
	private Integer groupId;
	@Column(name="create_time")
	private Long createTime;

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public String getPassword(){
		return password;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return status;
	}

	public void setGroupId(Integer groupId){
		this.groupId=groupId;
	}

	public Integer getGroupId(){
		return groupId;
	}

	public void setCreateTime(Long createTime){
		this.createTime=createTime;
	}

	public Long getCreateTime(){
		return createTime;
	}

}
