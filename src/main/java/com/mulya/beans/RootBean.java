package com.mulya.beans;

/**
 * User: mulya
 * Date: 25/09/2014
 */
public class RootBean {
	/**
	 * Фамилия
	 */
	private NameBean lastname;
	/**
	 * Имя
	 */
	private NameBean firstname;
	/**
	 * Отчество
	 */
	private NameBean middlename;

	public NameBean getLastname() {
		return lastname;
	}

	public void setLastname(NameBean lastname) {
		this.lastname = lastname;
	}

	public NameBean getFirstname() {
		return firstname;
	}

	public void setFirstname(NameBean firstname) {
		this.firstname = firstname;
	}

	public NameBean getMiddlename() {
		return middlename;
	}

	public void setMiddlename(NameBean middlename) {
		this.middlename = middlename;
	}
}
