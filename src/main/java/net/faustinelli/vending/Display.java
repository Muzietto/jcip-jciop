package main.java.net.faustinelli.vending;

public enum Display {
  NO_CREDIT("0 CENTS - INSERT COINS"),
  CREDIT_INSIDE("CREDIT #credit CENTS"),
  THANK_YOU("THANK YOU!!"),
  PRICE("PRICE #price"),
  SOLD_OUT("#product SOLD OUT");

  public String message;

  Display(String messageArg) {
    message = messageArg;
  }
  
}
