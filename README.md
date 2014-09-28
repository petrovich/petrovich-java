![Petrovich](petrovich.png) petrovich-java
==========================================

__petrovich-java__ is library which inflects Russian names to given grammatical case. It supports first names, last names and middle names inflections.

__petrovich-java__ is Java implementation of Petrovich ruby gem.

[![Build Status](https://travis-ci.org/mulya/petrovich-java.svg?branch=master)](https://travis-ci.org/mulya/petrovich-java)

## Building

```
mvn -DskipTests=true clean package install
```

## Usage

```
PetrovichDeclinationMaker maker = PetrovichDeclinationMaker.getInstance();

maker.make(NamePart.FIRSTNAME, Gender.MALE, Case.GENITIVE, "Иван");     //Ивана
maker.make(NamePart.LASTNAME, Gender.MALE, Case.INSTRUMENTAL, "Иванов");   //Ивановым
maker.make(NamePart.MIDDLENAME, Gender.FEMALE, Case.DATIVE, "Ивановна");   //Ивановне
```

Also you can use more convenient syntax

```
PetrovichDeclinationMaker maker = PetrovichDeclinationMaker.getInstance();

maker.male.firstname().toGenitive("Иван");      //"Ивана"
maker.male.lastname().toInstrumental("Иванов");     //"Ивановым"
maker.female.middlename().toDative("Ивановна");     //"Ивановне"
```

### Custom rule file

You can replace default rules file with some custom one. Only JSON format supported by now.
```
PetrovichDeclinationMaker maker = PetrovichDeclinationMaker.getInstance("/path/to/custom/rules.file.json");
```
