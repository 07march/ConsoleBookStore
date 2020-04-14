package by.entity;

public class Session {
  private int id;
  private Basket basket;
  private User user;

  public Session(Basket basket, User user) {
    this.basket = basket;
    this.user = user;
  }

  public Session() {
  }

  public Basket getBasket() {
    return basket;
  }

  public void setBasket(Basket basket) {
    this.basket = basket;
  }

  public int getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Session{" +
            "id=" + id +
            ", basket=" + basket +
            ", user=" + user +
            '}';
  }
}
