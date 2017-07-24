package com.cgeel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "sys_ueditor_file")
public class UeditorFile {

	@Id
	@Column(name="id")
	private Integer id;
	@Column(name="file_name")
	private String fileName;
	@Column(name="path")
	private String path;
	@Column(name="parent_id")
	private Integer parentId;
	@Column(name="dir")
	private Integer dir;
	@Column(name="type")
	private String type;
	@Column(name="user_id")
	private Integer userId;
	@Column(name="user_type")
	private Integer userType;

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getId(){
		return id;
	}

	public void setFileName(String fileName){
		this.fileName=fileName;
	}

	public String getFileName(){
		return fileName;
	}

	public void setPath(String path){
		this.path=path;
	}

	public String getPath(){
		return path;
	}

	public void setParentId(Integer parentId){
		this.parentId=parentId;
	}

	public Integer getParentId(){
		return parentId;
	}

	public void setDir(Integer dir){
		this.dir=dir;
	}

	public Integer getDir(){
		return dir;
	}

	public void setType(String type){
		this.type=type;
	}

	public String getType(){
		return type;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setUserType(Integer userType){
		this.userType=userType;
	}

	public Integer getUserType(){
		return userType;
	}


}
