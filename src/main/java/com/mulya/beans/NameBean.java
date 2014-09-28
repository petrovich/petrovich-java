package com.mulya.beans;

import java.util.List;

/**
 * User: mulya
 * Date: 25/09/2014
 */
public class NameBean {
	/**
	 * Исключения
	 */
	private List<RuleBean> exceptions;
	/**
	 * Правила
	 */
	private List<RuleBean> suffixes;

	public List<RuleBean> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<RuleBean> exceptions) {
		this.exceptions = exceptions;
	}

	public List<RuleBean> getSuffixes() {
		return suffixes;
	}

	public void setSuffixes(List<RuleBean> suffixes) {
		this.suffixes = suffixes;
	}
}
