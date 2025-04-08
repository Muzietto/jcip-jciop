package main.java.net.faustinelli.checkout.product;

public class Ramen implements Product {

  @Override
  public int unitaryPrice() {
    return 40;
  }

  @Override
  public final int hashCode() {
    return 123;
  }

  @Override
  public boolean equals(Object obj) {
    if (this.hashCode() == obj.hashCode())
      return true;

    return false;
  }
}
