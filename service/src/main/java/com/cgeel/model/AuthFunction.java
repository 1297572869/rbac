package com.cgeel.model;

import javax.persistence.*;


@Entity
@Table(name = "auth_function")
public class AuthFunction {

	@Id
	@Column(name="id")
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="parent_id")
	private Long parentId;
	@Column(name="url")
	private String url;
	@Column(name="serial_num")
	private Integer serialNum;
	@Column(name="accordion")
	private Integer accordion;

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

	public void setParentId(Long parentId){
		this.parentId=parentId;
	}

	public Long getParentId(){
		return parentId;
	}

	public void setUrl(String url){
		this.url=url;
	}

	public String getUrl(){
		return url;
	}

	public void setSerialNum(Integer serialNum){
		this.serialNum=serialNum;
	}

	public Integer getSerialNum(){
		return serialNum;
	}

	public void setAccordion(Integer accordion){
		this.accordion=accordion;
	}

	public Integer getAccordion(){
		return accordion;
	}

}
