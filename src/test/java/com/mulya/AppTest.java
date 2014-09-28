package com.mulya;

import com.fasterxml.jackson.jr.ob.JSON;
import com.mulya.beans.RootBean;
import com.mulya.enums.Case;
import com.mulya.enums.Gender;
import com.mulya.enums.NamePart;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * User: mulya
 * Date: 25/09/2014
 */
@Test
public class AppTest {

	public void testJsonRead() throws Exception {
		String str = new String(Files.readAllBytes(Paths.get("src/main/resources/rules.json")));
		assertTrue(str.length() > 0);
		RootBean rootBean = JSON.std.beanFrom(RootBean.class, str);
		assertNotNull(rootBean);
		assertNotNull(rootBean.getFirstname());
		assertNotNull(rootBean.getFirstname().getExceptions());
		assertNotNull(rootBean.getFirstname().getExceptions().get(0).getGender());
	}

	@Test
	public void testMe() throws Exception {
		PetrovichDeclinationMaker declinationMaker = PetrovichDeclinationMaker.getInstance();

		//PetrovichDeclinationMaker.MALE.FIRSTNAME.toGenitive("")
		//declinationMaker.MALE_FIRSTNAME.toGenitive("")

		assertEquals(declinationMaker.make(NamePart.FIRSTNAME, Gender.MALE, Case.GENITIVE, "Ринат"), "Рината");
		assertEquals(declinationMaker.make(NamePart.FIRSTNAME, Gender.MALE, Case.DATIVE, "Ринат"), "Ринату");
		assertEquals(declinationMaker.make(NamePart.FIRSTNAME, Gender.MALE, Case.ACCUSATIVE, "Ринат"), "Рината");
		assertEquals(declinationMaker.make(NamePart.FIRSTNAME, Gender.MALE, Case.INSTRUMENTAL, "Ринат"), "Ринатом");
		assertEquals(declinationMaker.make(NamePart.FIRSTNAME, Gender.MALE, Case.PREPOSITIONAL, "Ринат"), "Ринате");

		assertEquals(declinationMaker.make(NamePart.LASTNAME, Gender.MALE, Case.GENITIVE, "Мулюков"), "Мулюкова");
		assertEquals(declinationMaker.make(NamePart.LASTNAME, Gender.MALE, Case.DATIVE, "Мулюков"), "Мулюкову");
		assertEquals(declinationMaker.make(NamePart.LASTNAME, Gender.MALE, Case.ACCUSATIVE, "Мулюков"), "Мулюкова");
		assertEquals(declinationMaker.make(NamePart.LASTNAME, Gender.MALE, Case.INSTRUMENTAL, "Мулюков"), "Мулюковым");
		assertEquals(declinationMaker.make(NamePart.LASTNAME, Gender.MALE, Case.PREPOSITIONAL, "Мулюков"), "Мулюкове");

		assertEquals(declinationMaker.make(NamePart.MIDDLENAME, Gender.MALE, Case.GENITIVE, "Рашитович"), "Рашитовича");
		assertEquals(declinationMaker.make(NamePart.MIDDLENAME, Gender.MALE, Case.DATIVE, "Рашитович"), "Рашитовичу");
		assertEquals(declinationMaker.make(NamePart.MIDDLENAME, Gender.MALE, Case.ACCUSATIVE, "Рашитович"), "Рашитовича");
		assertEquals(declinationMaker.make(NamePart.MIDDLENAME, Gender.MALE, Case.INSTRUMENTAL, "Рашитович"), "Рашитовичем");
		assertEquals(declinationMaker.make(NamePart.MIDDLENAME, Gender.MALE, Case.PREPOSITIONAL, "Рашитович"), "Рашитовиче");

	}
}
