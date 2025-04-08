package main.java.net.faustinelli.checkout.pricing;

import main.java.net.faustinelli.checkout.Checkout;
import main.java.net.faustinelli.checkout.product.Product;

public interface PricingRule {

  public int price(Product product, Checkout checkout);

}
