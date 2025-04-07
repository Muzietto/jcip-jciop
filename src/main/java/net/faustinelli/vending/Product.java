package main.java.net.faustinelli.vending;

public enum Product {
  COLA(100, "Cola"),
  CHIPS(50, "Chips"),
  CANDY(65, "Candy");

  public int price;
  public String name;

  Product(int priceArg, String nameArg) {
    price = priceArg;
    name = nameArg;
  }
}

/*
 * cola for $1.00
chips for $0.50
candy for $0.65
 */
