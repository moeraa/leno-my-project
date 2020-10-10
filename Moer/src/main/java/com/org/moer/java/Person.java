package com.org.moer.java;

/**
 * @Auther: moer
 * @Date: 2019/8/1 10:09
 * @Description:
 */
public class Person {
  public Person( int id,String name) {
    this.name = name;
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    if (id != person.id) return false;
    return name != null ? name.equals(person.name) : person.name == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + id;
    return result;
  }

  private String name;
  private int id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
