package by.entity;

import java.io.Serializable;

public class User implements Serializable {
  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Role role;
  private boolean isOrdered;

  public User() {
  }

  public User(int id, String firstName, String lastName, String email, String password, Role role, boolean isOrdered) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
    this.isOrdered = isOrdered;
  }

  public User(int id, String firstName, String lastName, String email, String password, Role role) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public User(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
  }

  public User(String firstName, String lastName, String email, String password, Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public void setId(int id) {
    this.id = id;
  }

  public static void main(String[] args) {
    User user = new User("s", "фыв", "sad", "sdsdfdsf");
    Moderator moderator = (Moderator) user;
    moderator.setStore(null);
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", role=" + role +
            ", isOrdered=" + isOrdered +
            '}';
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isOrdered() {
    return isOrdered;
  }

  public void setOrdered(boolean ordered) {
    isOrdered = ordered;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
