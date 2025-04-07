package test.java.net.faustinelli.vending;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.java.net.faustinelli.vending.*;

public class VendingMachineTests {

  private VendingMachine machine;

  @Before
  public void setUp() {
    machine = new VendingMachine();
  }

  @Test
  public void test_createMachine() {
    assertEquals("main.java.net.faustinelli.vending.VendingMachine", machine.toString().split("@")[0]);
  }

  @Test
  public void test_acceptCoins() {
    machine.acceptCoin(CoinSize.MM12);
    assertEquals("CREDIT 10 CENTS", machine.display());
    machine.acceptCoin(CoinSize.MM12);
    assertEquals("CREDIT 20 CENTS", machine.display());
    machine.acceptCoin(CoinSize.MM20);
    assertEquals("CREDIT 45 CENTS", machine.display());
    machine.acceptCoin(CoinSize.MM7);
    assertEquals("CREDIT 45 CENTS", machine.display());
  }

  @Test
  public void test_notEnoughCredit() {
    machine.loadProduct(Product.COLA, 1);
    machine.chooseProduct(Product.COLA);
    assertEquals("PRICE 100", machine.display());
    assertEquals("PRICE 100", machine.display());
  }

  @Test
  public void test_notEnoughProduct() {
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    assertEquals("CREDIT 100 CENTS", machine.display());

    machine.chooseProduct(Product.COLA);
    assertEquals("Cola SOLD OUT", machine.display());
  }

  @Test
  public void test_removeProduct() {
    machine.loadProduct(Product.COLA, 1);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.chooseProduct(Product.COLA);
    assertEquals("THANK YOU!!", machine.display());
  }

  @Test
  public void test_subtractCredit() {
    machine.loadProduct(Product.COLA, 1);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.chooseProduct(Product.COLA);
    assertEquals("THANK YOU!!", machine.display());
    assertEquals("CREDIT 0 CENTS", machine.display());
  }

  @Test
  public void test_removeBoughtProduct() {
    machine.loadProduct(Product.COLA, 1);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    assertEquals("CREDIT 225 CENTS", machine.display());
    machine.chooseProduct(Product.COLA);
    assertEquals("THANK YOU!!", machine.display());
    assertEquals("CREDIT 125 CENTS", machine.display());

    machine.chooseProduct(Product.COLA);
    assertEquals("Cola SOLD OUT", machine.display());
    assertEquals("CREDIT 125 CENTS", machine.display());
  }

  @Test
  public void test_returnAllCoins() {
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    assertEquals("CREDIT 50 CENTS", machine.display());

    machine.returnCoins();
    assertEquals("CREDIT 0 CENTS", machine.display());
  }

  @Test
  public void test_returnSomeCoins() {
    machine.loadProduct(Product.CANDY, 1);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    assertEquals("CREDIT 100 CENTS", machine.display());
    machine.chooseProduct(Product.CANDY);
    assertEquals("THANK YOU!!", machine.display());
    assertEquals("CREDIT 35 CENTS", machine.display());

    machine.returnCoins();
    assertEquals("CREDIT 10 CENTS", machine.display());
  }

  @Test
  public void test_soldOut() {
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    machine.acceptCoin(CoinSize.MM20);
    assertEquals("CREDIT 75 CENTS", machine.display());
    machine.chooseProduct(Product.CANDY);
    assertEquals("Candy SOLD OUT", machine.display());
    assertEquals("CREDIT 75 CENTS", machine.display());
  }

}