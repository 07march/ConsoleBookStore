package by.entity;

import java.io.Serializable;
import java.util.Objects;

public class Author implements Serializable {
  private int id;
  private String name;

  public Author(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public Author(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Author{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Author author = (Author) o;

    return Objects.equals(name, author.name);
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
