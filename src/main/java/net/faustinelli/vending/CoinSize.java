package main.java.net.faustinelli.vending;

public enum CoinSize {
  MM7("penny", 1), // 1c
  MM12("dime", 10), // 10c
  MM15("nickel", 5), // 5c
  MM20("quarter", 25); // 25c

  public String name;
  public int value;

  CoinSize(String nameArg, int valueArg) {
    name = nameArg;
    value = valueArg;
  }
}
