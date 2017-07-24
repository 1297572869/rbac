package com.cgeel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "sys_upload_file")
public class UploadFile {

	@Id
	@Column(name="id")
	private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="dir")
	private String dir;
	@Column(name="path")
	private String path;
	@Column(name="create_time")
	private Long createTime;
	@Column(name="state")
	private Integer state;

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

	public void setDir(String dir){
		this.dir=dir;
	}

	public String getDir(){
		return dir;
	}

	public void setPath(String path){
		this.path=path;
	}

	public String getPath(){
		return path;
	}

	public void setCreateTime(Long createTime){
		this.createTime=createTime;
	}

	public Long getCreateTime(){
		return createTime;
	}

	public void setState(Integer state){
		this.state=state;
	}

	public Integer getState(){
		return state;
	}

}
