package main.java.net.faustinelli.checkout;

import java.util.List;
import java.util.ArrayList;

import main.java.net.faustinelli.checkout.pricing.PricingRule;
import main.java.net.faustinelli.checkout.product.Product;

public class Checkout {

  public String scan(Product product, int qty, PricingRule rule) {
    String result = "";

    if (rule == null) {
      result += product.unitaryPrice() * qty + " CENTS";
    }

    if (rule != null) {
      result = product.price(qty, rule);
    }
    return result;
  }

  public String scan(Product product, PricingRule rule) {
    return this.scan(product, 1, rule);
  }

  public String scan(String sku) {
    int price = 0;

    if (sku == "SOUP") price = 100;
    if (sku == "RAMEN") price = 40;

    return price + " CENTS";
  }
}
