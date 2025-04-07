package main.java.net.faustinelli.checkout.product;

import main.java.net.faustinelli.checkout.pricing.PricingRule;

public interface Product {

  public String price(int qty, PricingRule rule);

  public int unitaryPrice();
}
