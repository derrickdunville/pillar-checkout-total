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
	WeightedItem weightedItemTwo;
	Checkout checkout;
	
	@Before
	public void initializeTest() {
		store = new Store();
		quantifiedItemOne = new QuantifiedItem("QuantifiedItemOne", 2.59);
		quantifiedItemTwo = new QuantifiedItem("QuantifiedItemTwo", 1.59);
		weightedItemOne = new WeightedItem("WeightedItemOne", 1.49);
		weightedItemTwo = new WeightedItem("WeightedItemTwo", 1.79);
		store.addItem(quantifiedItemOne);
		store.addItem(quantifiedItemTwo);
		store.addItem(weightedItemOne);
		store.addItem(weightedItemTwo);
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
	
	@Test
	public void canScanTwoDifferentWeightedItems() {
		assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), weightedItemOne.getPrice() * 1.5, delta);
		assertEquals(checkout.scanItem(weightedItemTwo.getName(), 1.5), weightedItemOne.getPrice() * 1.5 + weightedItemTwo.getPrice() * 1.5, delta);
	}
	
	@Test
	public void canScanAWeightedItemMulitipleTimes() {
		assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), weightedItemOne.getPrice() * 1.5, delta);
		assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), weightedItemOne.getPrice() * 1.5 * 2, delta);
	}
	
	@Test
	public void canScanBothAWeightedItemAndAQuatifiedItem() {
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
		assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), quantifiedItemOne.getPrice() + weightedItemOne.getPrice() * 1.5 , delta);
	}
	
	@Test
	public void canScanMultipleWeightedItemsAndQuatifiedItems() {
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
		assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), quantifiedItemOne.getPrice() + weightedItemOne.getPrice() * 1.5 , delta);
		assertEquals(checkout.scanItem(quantifiedItemTwo.getName()), quantifiedItemOne.getPrice() + weightedItemOne.getPrice() * 1.5 + quantifiedItemTwo.getPrice(), delta);
		assertEquals(checkout.scanItem(weightedItemTwo.getName(), 2.5), quantifiedItemOne.getPrice() + weightedItemOne.getPrice() * 1.5 + quantifiedItemTwo.getPrice() + weightedItemTwo.getPrice() * 2.5, delta);
	}
	
	@Test
	public void canScanAQuantifiedItemWithAMarkdown() {
		double priceBeforeMarkdown = quantifiedItemOne.getPrice();
		quantifiedItemOne.setMarkdown(0.29);
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), priceBeforeMarkdown - 0.29, delta);
	}
	
	@Test
	public void canScanAWeightedItemWithAMarkdown() {
		double priceBeforeMarkdown = weightedItemOne.getPrice();
		weightedItemOne.setMarkdown(0.39);
		assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), (priceBeforeMarkdown - 0.39)*1.5, delta);
	}
	
	@Test
	public void canScanThreeWithBuyTwoGetOneFreeSpecial() {
		quantifiedItemOne.setSpecial(2, 1, 100.00);
		// Scan 2 items
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
		// Scan a 3rd item and should be total of 2, since buy 2 get 1 free special is set
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
	}
		
	@Test
	public void canScanSixWithBuyTwoGetOneFreeSpecial() {
		quantifiedItemOne.setSpecial(2, 1, 100.00);
		// Scan 2 items
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
		// Scan a 3rd item and should be total of 2, since buy 2 get 1 free special is set
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 3, delta);
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 4, delta);
		// Scan a 6th item and should get 2 for free
		assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 4, delta);
	}
}
