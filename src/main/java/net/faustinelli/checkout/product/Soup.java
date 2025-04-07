package main.java.net.faustinelli.checkout.product;

import main.java.net.faustinelli.checkout.pricing.PricingRule;

public class Soup implements Product {

  @Override
  public String price(int qty, PricingRule rule) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'price'");
  }

  @Override
  public int unitaryPrice() {
    return 100;
  }

}
