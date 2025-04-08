package main.java.net.faustinelli.checkout.pricing;

import main.java.net.faustinelli.checkout.Checkout;
import main.java.net.faustinelli.checkout.product.Product;

public class NForP implements PricingRule {
  private int batchSize = 1;
  private int cumulativePrice = 0;

  public NForP(int batchSize, int cumulativePrice) {
    this.batchSize = batchSize;
    this.cumulativePrice = cumulativePrice;
  }

  @Override
  public int price(Product product, Checkout checkout) {
    int result = product.unitaryPrice();
    int checkedSoFar = checkout.checkedSoFar(product);

    if (checkedSoFar % batchSize == 0)
      result = cumulativePrice - (batchSize - 1) * product.unitaryPrice();

    return result;
  }
}
