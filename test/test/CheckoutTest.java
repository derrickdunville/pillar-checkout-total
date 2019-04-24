package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pillar.Checkout;
import pillar.Store;
import pillar.item.QuantifiedItem;
import pillar.item.WeightedItem;

public class CheckoutTest {

	double delta = 0.000001;
	Store store;
	QuantifiedItem quantifiedItemOne;
	QuantifiedItem quantifiedItemTwo;
	WeightedItem weightedItemOne;
	Checkout checkout;
	
	@Before
	public void initializeTest() {
		store = new Store();
		quantifiedItemOne = new QuantifiedItem("QuantifiedItemOne", 2.59);
		quantifiedItemTwo = new QuantifiedItem("QuantifiedItemTwo", 1.59);
		weightedItemOne = new WeightedItem("WeightedItemOne", 1.49);
		store.addItem(quantifiedItemOne);
		store.addItem(quantifiedItemTwo);
		store.addItem(weightedItemOne);
		checkout = new Checkout(store);
	}
	
	@Test
	public void canScanAQuantifiedItem() {
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 2.59, delta);
	}
	
	@Test
	public void canScanTwoDifferentQuantifiedItems() {
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
		assertEquals(checkout.scanItem(quantifiedItemTwo.getName()), quantifiedItemOne.getPrice() + quantifiedItemTwo.getPrice(), delta);
	}
	
	@Test
	public void canScanAQuantifiedItemMultipleTimes() {
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice()*2, delta);
	}
	
	@Test
	public void canScanAWeightedItem() {
		assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.0), weightedItemOne.getPrice() * 1.0, delta);
	}
}
