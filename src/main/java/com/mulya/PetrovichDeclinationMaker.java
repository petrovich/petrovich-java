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
	private static final String MODS_KEEP_IT_ALL_SYMBOL = ".";
	private static final String MODS_REMOVE_LETTER_SYMBOL = "-";

	private RootBean rootRulesBean;

	public GenderCurryedMaker male = new GenderCurryedMaker(Gender.MALE);
	public GenderCurryedMaker female = new GenderCurryedMaker(Gender.FEMALE);
	public GenderCurryedMaker androgynous = new GenderCurryedMaker(Gender.ANDROGYNOUS);


	private PetrovichDeclinationMaker(String pathToRulesFile) throws IOException {
		rootRulesBean = JSON.std.beanFrom(RootBean.class, new String(Files.readAllBytes(Paths.get(pathToRulesFile))));
	}

	public static PetrovichDeclinationMaker getInstance() throws IOException {
		return getInstance(DEFAULT_PATH_TO_RULES_FILE);
	}

	public static PetrovichDeclinationMaker getInstance(String pathToRulesFile) throws IOException {
		return new PetrovichDeclinationMaker(pathToRulesFile);
	}

	public String make(NamePart namePart, Gender gender, Case caseToUse, String originalName) {
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

		RuleBean ruleToUse = null;
		RuleBean exceptionRuleBean = findInRuleBeanList(nameBean.getExceptions(), gender, originalName);
		RuleBean suffixRuleBean = findInRuleBeanList(nameBean.getSuffixes(), gender, originalName);
		if (exceptionRuleBean != null && exceptionRuleBean.getGender().equals(gender.getValue())) {
			ruleToUse = exceptionRuleBean;
		} else if (suffixRuleBean != null && suffixRuleBean.getGender().equals(gender.getValue())) {
			ruleToUse = suffixRuleBean;
		} else {
			ruleToUse = exceptionRuleBean != null ? exceptionRuleBean : suffixRuleBean;
		}

		if (ruleToUse != null) {
			String modToApply = ruleToUse.getMods().get(caseToUse.getValue());
			result = applyModToName(modToApply, originalName);
		}

		return result;
	}

	protected String applyModToName(String modToApply, String name) {
		String result = name;
		if (!modToApply.equals(MODS_KEEP_IT_ALL_SYMBOL)) {
			if (modToApply.contains(MODS_REMOVE_LETTER_SYMBOL)) {
				for (int i = 0; i < modToApply.length(); i++) {
					if (Character.toString(modToApply.charAt(i)).equals(MODS_REMOVE_LETTER_SYMBOL)) {
						result = result.substring(0, result.length() - 1);
					} else {
						result += modToApply.substring(i);
						break;
					}
				}
			} else {
				result = name + modToApply;
			}
		}
		return result;
	}

	protected RuleBean findInRuleBeanList(List<RuleBean> ruleBeanList, Gender gender, String originalName) {
		RuleBean result = null;
		if (ruleBeanList != null) {
			out:
			for(RuleBean ruleBean : ruleBeanList) {
				for (String test : ruleBean.getTest()) {
					if (originalName.endsWith(test)) {
						if (ruleBean.getGender().equals(Gender.ANDROGYNOUS.getValue())) {
							result = ruleBean;
							break out;
						} else if ((ruleBean.getGender().equals(gender.getValue()))) {
							result = ruleBean;
							break out;
						}
					}
				}
			}
		}

		return result;
	}

	protected class GenderCurryedMaker {
		private Gender gender;

		protected GenderCurryedMaker(Gender gender) {
			this.gender = gender;
		}

		public GenderAndNamePartCurryedMaker firstname() {
			return new GenderAndNamePartCurryedMaker(gender, NamePart.FIRSTNAME);
		}

		public GenderAndNamePartCurryedMaker lastname() {
			return new GenderAndNamePartCurryedMaker(gender, NamePart.LASTNAME);
		}

		public GenderAndNamePartCurryedMaker middlename() {
			return new GenderAndNamePartCurryedMaker(gender, NamePart.MIDDLENAME);
		}
	}

	protected class GenderAndNamePartCurryedMaker {
		private NamePart namePart;
		private Gender gender;

		protected GenderAndNamePartCurryedMaker(Gender gender, NamePart namePart) {
			this.gender = gender;
			this.namePart = namePart;
		}

		public String toGenitive(String name) {
			return make(namePart, gender, Case.GENITIVE, name);
		}

		public String toDative(String name) {
			return make(namePart, gender, Case.DATIVE, name);
		}

		public String toAccusative(String name) {
			return make(namePart, gender, Case.ACCUSATIVE, name);
		}

		public String toInstrumental(String name) {
			return make(namePart, gender, Case.INSTRUMENTAL, name);
		}

		public String toPrepositional(String name) {
			return make(namePart, gender, Case.PREPOSITIONAL, name);
		}
	}
}
