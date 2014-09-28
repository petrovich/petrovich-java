package com.mulya;

import com.fasterxml.jackson.jr.ob.JSON;
import com.mulya.beans.NameBean;
import com.mulya.beans.RootBean;
import com.mulya.beans.RuleBean;
import com.mulya.enums.Case;
import com.mulya.enums.Gender;
import com.mulya.enums.NamePart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * User: mulya
 * Date: 28/09/2014
 */
public class PetrovichDeclinationMaker {

	private static final String DEFAULT_PATH_TO_RULES_FILE = "src/main/resources/rules.json";

	private static PetrovichDeclinationMaker instance;

	private RootBean rootRulesBean;

	private PetrovichDeclinationMaker(String pathToRulesFile) throws IOException {
		rootRulesBean = JSON.std.beanFrom(RootBean.class, new String(Files.readAllBytes(Paths.get(pathToRulesFile))));
	}

	public static PetrovichDeclinationMaker getInstance() throws IOException {
		return getInstance(DEFAULT_PATH_TO_RULES_FILE);
	}

	public static PetrovichDeclinationMaker getInstance(String pathToRulesFile) throws IOException {
		if (instance == null) {
			instance = new PetrovichDeclinationMaker(pathToRulesFile);
		}
		return instance;
	}

	public String make(NamePart namePart, Gender gender, Case caseToUse, String originalName) {
		String modToApply;
		String result = originalName;

		NameBean nameBean;

		switch (namePart) {
			case FIRSTNAME:
				nameBean = rootRulesBean.getFirstname();
				break;
			case LASTNAME:
				nameBean = rootRulesBean.getLastname();
				break;
			case MIDDLENAME:
				nameBean = rootRulesBean.getMiddlename();
				break;
			default:
				nameBean = rootRulesBean.getMiddlename();
				break;
		}

		modToApply = applyRuleBeanList(nameBean.getExceptions(), gender, caseToUse, originalName);

		if (modToApply != null) {
			modToApply = applyRuleBeanList(nameBean.getSuffixes(), gender, caseToUse, originalName);
		}

		if (modToApply != null) {
			if (modToApply.equals(".")) {
				result = originalName;
			} else if (modToApply.contains("-")) {
				for (int i = 0; i < modToApply.length(); i++) {
					if (modToApply.charAt(i) == '-') {
						result = result.substring(0, result.length() - 1);
					} else {
						result += modToApply.substring(i);
						break;
					}
				}
			} else {
				result = originalName + modToApply;
			}
		}

		return result;
	}

	protected String applyRuleBeanList(List<RuleBean> ruleBeanList, Gender gender, Case caseToUse, String originalName) {
		String result = originalName;
		if (ruleBeanList != null) {
			for(RuleBean ruleBean : ruleBeanList) {
				for (String test : ruleBean.getTest()) {
					if (ruleBean.getGender().equals(gender.getValue()) && originalName.endsWith(test)) {
						result = ruleBean.getMods().get(caseToUse.getValue());
						break;
					}
				}
			}
		}

		return result;
	}
}
