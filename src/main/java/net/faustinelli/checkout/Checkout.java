package main.java.net.faustinelli.checkout;

import java.util.List;
import java.util.ArrayList;

import main.java.net.faustinelli.checkout.pricing.PricingRule;
import main.java.net.faustinelli.checkout.product.Product;

public class Checkout {
  private List<Product> checkedSoFar = new ArrayList<Product>();

  public String scan(Product product, int qty, PricingRule rule) {
    checkedSoFar.add(product);

    String result = "";

    if (rule == null) {
      result += product.unitaryPrice() * qty + " CENTS";
    }

    if (rule != null) {
      result = rule.price(product, this) + " CENTS";
    }
    return result;
  }

  public String scan(Product product, PricingRule rule) {
    return this.scan(product, 1, rule);
  }

  public String scan(String sku) {
    int price = 0;

    if (sku == "SOUP")
      price = 100;
    if (sku == "RAMEN")
      price = 40;

    return price + " CENTS";
  }

  public int checkedSoFar(Product product) {
    int result = checkedSoFar.stream()
        .filter(p -> p.equals(product))
        .toList()
        .size();

    return result;
  }
}
