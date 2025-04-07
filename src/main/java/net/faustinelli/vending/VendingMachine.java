package main.java.net.faustinelli.vending;

import java.util.List;
import java.util.ArrayList;

public class VendingMachine {

  private List<CoinSize> coins = new ArrayList<CoinSize>();
  private int credit = 0;
  private List<Product> products = new ArrayList<Product>();
  private String displayMessage = Display.NO_CREDIT.message;

  public void returnCoins() {

    while (credit > 0) {
      int lastCredit = credit;

      for (CoinSize coin : coins) {

        if (credit >= coin.value) {
          credit -= coin.value;
          coins.remove(coins.indexOf(coin));
          System.out.printf("rimosso %s \n", coin.name);
          System.out.printf("credito restante %s \n", credit);
          System.out.printf("monete restanti %s \n", coins.size());
          break;
        }
      }

      if (lastCredit == credit)
        break;
    }
    displayMessage = Display.CREDIT_INSIDE.message.replace("#credit", credit + "");
  }

  public void chooseProduct(Product product) {
    if (credit < product.price) {
      displayMessage = Display.PRICE.message.replace("#price", product.price + "");
    }

    if (!products.contains(product)) {
      displayMessage = Display.SOLD_OUT.message.replace("#product", product.name + "");
      return;
    }

    if (credit >= product.price) {
      int firstProduct = products.indexOf(product);
      products.remove(firstProduct);
      credit -= product.price;
      displayMessage = Display.THANK_YOU.message;
    }
  }

  public void acceptCoin(CoinSize coin) {
    if (coin == CoinSize.MM7) {
      return;
    }

    coins.add(coin);
    coins.sort((a, b) -> {
      return Integer.compare(a.value, b.value);
    });
    credit += coin.value;
    displayMessage = Display.CREDIT_INSIDE.message.replace("#credit", credit + "");
  }

  public void loadProduct(Product product, int qty) {
    for (int i = 0; i < qty; i++) {
      products.add(product);
    }
  }

  public String display() {
    String result = displayMessage;

    if (displayMessage.contains("THANK"))
      displayMessage = Display.CREDIT_INSIDE.message.replace("#credit", credit + "");
    if (displayMessage.contains("SOLD OUT"))
      displayMessage = Display.CREDIT_INSIDE.message.replace("#credit", credit + "");

    return result;
  }
}
