package main.java.net.faustinelli.checkout.pricing;

import main.java.net.faustinelli.checkout.Checkout;
import main.java.net.faustinelli.checkout.product.Product;

public class BuyXGetYFree implements PricingRule {
  private int paidBatch = 1;
  private int freeBatch = 1;

  public BuyXGetYFree(int paidBatch, int freeBatch) {
    this.paidBatch = paidBatch;
    this.freeBatch = freeBatch;
  }

  @Override
  public int price(Product product, Checkout checkout) {

    int unitaryPrice = product.unitaryPrice();
    int result = unitaryPrice;
    int checkedSoFar = checkout.checkedSoFar(product);

    System.out.printf("checkedSoFar %s \n", checkedSoFar);
    System.out.printf("paidBatch %s \n", paidBatch);
    System.out.printf("freeBatch %s \n", freeBatch);

    if (checkedSoFar % (paidBatch + freeBatch) == 0) {
      result = 0;
    }

    return result;
  }
}
