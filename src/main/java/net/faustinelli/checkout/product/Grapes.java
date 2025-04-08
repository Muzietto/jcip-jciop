package main.java.net.faustinelli.checkout.product;

public class Grapes implements Product {

  @Override
  public int unitaryPrice() {
    return 400;
  }

  @Override
  public final int hashCode() {
    return 1234;
  }

  @Override
  public boolean equals(Object obj) {
    if (this.hashCode() == obj.hashCode())
      return true;

    return false;
  }
}
