package test.java.net.faustinelli.checkout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

import main.java.net.faustinelli.checkout.*;
import main.java.net.faustinelli.checkout.product.*;

public class CheckoutTests {

  private Checkout checkout;

  @Before
  public void setUp() {
    checkout = new Checkout();
  }

  @Test
  public void test_setUp() {
    String toString = checkout.toString();
    assertEquals("main.java.net.faustinelli.checkout.Checkout", toString.split("@")[0]);

  }

  @Test
  public void test_scan01() {
    String result = checkout.scan("SOUP");
    assertEquals("100 CENTS", result);
    
    result = checkout.scan("RAMEN");
    assertEquals("40 CENTS", result);
  }

  @Test
  public void test_scan02() {
    String result = checkout.scan(new Soup(), null);
    assertEquals("100 CENTS", result);
    result = checkout.scan(new Soup(), 4, null);
    assertEquals("400 CENTS", result);
    result = checkout.scan(new Grapes(), 3, null);
    assertEquals("1200 CENTS", result);
  }

}