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
		weightedItem = new WeightedItem("WeightedItem", 2.59);
	}
	
	@Test
	public void quantifiedItemCreatedWithNameAndPrice() {
		assertEquals(quantifiedItem.getName(), "QuantifiedItem");
		assertEquals(quantifiedItem.getPrice(), 2.59, delta);	
	}
	
	@Test
	public void quantifiedItemSubTotalBasedOnQuantity() {
		assertEquals(quantifiedItem.getSubTotal(2), 2.59*2, delta);
	}
	
	@Test
	public void weightedItemCreatedWithNameAndPrice() {
		assertEquals(weightedItem.getName(), "WeightedItem");
		assertEquals(weightedItem.getPrice(), 2.59, delta);	
	}
	
	@Test
	public void weightedItemSubTotalBasedOnWeight() {
		assertEquals(weightedItem.getSubTotal(2.00), 2.59*2.00, delta);
	}
	
	@Test
	public void markdownAnItem() {
		double priceBeforeMarkdown = quantifiedItem.getPrice();
		quantifiedItem.setMarkdown(0.29);
		assertEquals(quantifiedItem.getPrice(), priceBeforeMarkdown - 0.29, delta);
		
		priceBeforeMarkdown = weightedItem.getPrice();
		weightedItem.setMarkdown(0.39);
		assertEquals(weightedItem.getPrice(), priceBeforeMarkdown - 0.39, delta);
	}
	
	@Test
	public void addBuyNGetMAtPercentOffSpecialToQuantifiedItem() {
		quantifiedItem.setSpecial(1, 1, 100.00);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 1);
		assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 1);
		assertEquals(quantifiedItem.getSpecialDiscountPercent(), 100.00, delta);
	}
}
