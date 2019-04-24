package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pillar.item.QuantifiedItem;
import pillar.item.WeightedItem;

public class ItemTest {

	double delta = 0.000001;
	QuantifiedItem quantifiedItem;
	WeightedItem weightedItem; 
	
	@Before
	public void initializeTest() {
		quantifiedItem = new QuantifiedItem("QuantifiedItem", 2.59);
		weightedItem = new WeightedItem("Test", 2.59);
	}
	@Test
	public void quantifiedItemCreatedWithNameAndPrice() {
		assertEquals(quantifiedItem.getName(), "Test");
		assertEquals(quantifiedItem.getPrice(), 2.59, delta);	
	}
	
	@Test
	public void quantifiedItemSubTotalBasedOnQuantity() {
		assertEquals(quantifiedItem.getSubTotal(2), 2.59*2, delta);
	}
	
	@Test
	public void weightedItemCreatedWithNameAndPrice() {
		assertEquals(weightedItem.getName(), "Test");
		assertEquals(weightedItem.getPrice(), 2.59, delta);	
	}
	
	@Test
	public void weightedItemSubTotalBasedOnWeight() {
		assertEquals(weightedItem.getSubTotal(2.00), 2.59*2.00, delta);
	}
}
