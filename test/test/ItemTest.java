package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pillar.item.QuantifiedItem;
import pillar.item.WeightedItem;

public class ItemTest {

	@Test
	public void quantifiedItemCreatedWithNameAndPrice() {
		QuantifiedItem testItem = new QuantifiedItem("Test", 2.59);
		assertEquals(testItem.getName(), "Test");
		assertEquals(testItem.getPrice(), 2.59, 2);	
	}
	
	@Test
	public void quantifiedItemSubTotalBasedOnQuantity() {
		QuantifiedItem testItem = new QuantifiedItem("Test", 2.59);
		assertEquals(testItem.getSubTotal(2), 2.59*2, 2);
	}
	
	@Test
	public void weightedItemCreatedWithNameAndPrice() {
		WeightedItem testItem = new WeightedItem("Test", 2.59);
		assertEquals(testItem.getName(), "Test");
		assertEquals(testItem.getPrice(), 2.59, 2);	
	}
	
	@Test
	public void weightedItemSubTotalBasedOnWeight() {
		WeightedItem testItem = new WeightedItem("Test", 2.59);
		assertEquals(testItem.getSubTotal(2.00), 2.59*2.00, 2);
	}
}
