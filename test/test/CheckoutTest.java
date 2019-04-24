package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pillar.Checkout;
import pillar.Store;
import pillar.item.QuantifiedItem;

public class CheckoutTest {

	@Test
	public void canScanAQuantifiedItem() {
		Store store = new Store();
		QuantifiedItem item = new QuantifiedItem("Test", 2.59);
		store.addItem(item);
		Checkout checkout = new Checkout(store);
		assertEquals(checkout.scanItem(item.getName()), 2.59, 2);
	}
}
