package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pillar.Checkout;
import pillar.Store;
import pillar.item.QuantifiedItem;

public class CheckoutTest {

	Store store;
	QuantifiedItem quantifiedItemOne;
	QuantifiedItem quantifiedItemTwo;
	Checkout checkout;
	
	@Before
	public void initializeTest() {
		store = new Store();
		quantifiedItemOne = new QuantifiedItem("QuantifiedItemOne", 2.59);
		quantifiedItemTwo = new QuantifiedItem("QuantifiedItemTwo", 1.59);
		store.addItem(quantifiedItemOne);
		store.addItem(quantifiedItemTwo);
		checkout = new Checkout(store);
	}
	
	@Test
	public void canScanAQuantifiedItem() {
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 2.59, 2);
	}
	
	@Test
	public void canScanTwoDifferentQuantifiedItems() {
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), 2);
		assertEquals(checkout.scanItem(quantifiedItemTwo.getName()), quantifiedItemOne.getPrice() + quantifiedItemTwo.getPrice(), 2);
	}
	
	@Test
	public void canScanAQuantifiedItemMultipleTimes() {
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), 2);
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice()*2, 2);
	}
}
