package by.entity;

import java.util.Objects;

public class Moderator extends User {
  private Store store;

  public Moderator() {
  }

  public Moderator(String firstName, String lastName, String email, String password, Store store) {
    super(firstName, lastName, email, password);
    this.store = store;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Moderator moderator = (Moderator) o;
    return Objects.equals(store, moderator.store);
  }

  @Override
  public int hashCode() {
    return Objects.hash(store);
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  @Override
  public String toString() {
    return "Moderator{" +
            "by.store=" + store +
            '}';
  }
}
