package com.common.utils.utils.common.reflect;

import java.math.BigDecimal;
import java.util.Date;


public class People {

	private String name;
	
	private String sex;
	
	private Integer age;
	private BigDecimal money;
	private Date bir;
	
	
	public class teacher{
		
		private String add;
		
		
		public void setAdd(String add) {
			this.add = add;
		}
		
		
		public String getAdd() {
			return add;
		}
	}
	
	public People() {
		super();
	}


	public People(String name, String sex, Integer age,BigDecimal money,Date bir) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.money= money;
		this.bir = bir;
	}


	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getSex() {
		return sex;
	}

	
	public void setSex(String sex) {
		this.sex = sex;
	}

	
	public Integer getAge() {
		return age;
	}

	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	
	public Date getBir() {
		return bir;
	}
	
	public void setBir(Date bir) {
		this.bir = bir;
	}
	
}
