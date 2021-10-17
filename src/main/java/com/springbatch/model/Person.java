package com.springbatch.model;

public class Person {

  private String personId;
  private String lastName;
  private  String firstName;

  public Person(String personId, String lastName, String firstName) {
    this.personId = personId;
    this.lastName = lastName;
    this.firstName = firstName;
  }

  public Person() {
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId = personId;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String toString() {
    return "firstName: " + firstName + ", lastName: " + lastName;
  }

}
