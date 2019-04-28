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
	
	@Test
	public void addBuyNForXPrice() {
		quantifiedItem.setSpecial(3, 5.00);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 3);
		assertEquals(quantifiedItem.getDiscountPrice(), 5.00, delta);
	}
	
	@Test
	public void addSpecialLimit() {
		quantifiedItem.setSpecial(2,  1, 100.00);
		quantifiedItem.setSpecialLimit(6);
		assertEquals(quantifiedItem.getSpecialLimit(), 6);
	}
	
	@Test
	public void specialMustBeCheaperThenFullPrice() {
		quantifiedItem.setSpecial(3, quantifiedItem.getPrice()*3 + .01);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
		assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
	}
	
	@Test
	public void settingSpecialNForXShouldClearBuyNGetNXPercentOff() {
		quantifiedItem.setSpecial(2, 1, 100.00);
		quantifiedItem.setSpecial(3, 5.00);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 3);
		assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
		assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
		assertEquals(quantifiedItem.getDiscountPrice(), 5.00, delta);
	}
	
	@Test
	public void settingBuyNGetNXPercentOffShouldClearNForX() {
		quantifiedItem.setSpecial(3, 5.00);
		quantifiedItem.setSpecial(2, 1, 100.00);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 2);
		assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 1);
		assertEquals(quantifiedItem.getSpecialDiscountPercent(), 100.00, delta);
		assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
	}
	
	@Test
	public void canClearNForXSpecial() {
		quantifiedItem.setSpecial(3, 5);
		quantifiedItem.setSpecial(0, 0.0);
		assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
	}
	
	@Test
	public void BuyNGetMForXPercentOffMustHavePercentBetweenZeroAndHundredInclusive() {
		quantifiedItem.setSpecial(2, 1, -0.01);
		assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
		assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
		
		quantifiedItem.setSpecial(2, 1, 100.01);
		assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
		assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
	}
	
	@Test
	public void BuyNGetMForXPercentOffMustHaveTriggerQuantityGreaterThenEqualToZero() {
		quantifiedItem.setSpecial(-1, 1, 100.0);
		assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
		assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
	}
	
	@Test
	public void BuyNGetMForXPercentOffMustHaveDiscountedQuantityGreaterThenEqualToZero() {
		quantifiedItem.setSpecial(1, -1, 100.0);
		assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
		assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
	}
	
	@Test
	public void BuyNForXMustHaveTriggerQuantityGreaterThanEqualZero() {
		quantifiedItem.setSpecial(-1, 5.00);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
		assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
	}
	
	@Test
	public void BuyNForXMustHaveDiscountPriceGreaterThanEqualZero() {
		quantifiedItem.setSpecial(2, -1.00);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
		assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
	}
	
	@Test
	public void BuyNForXMustHaveBothTriggerQuantityAndDiscountPriceGreaterThanZeorWhenSettingSpeical() {
		quantifiedItem.setSpecial(2, 0);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
		assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
		
		quantifiedItem.setSpecial(0, 2.00);
		assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
		assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
	}
	
	@Test
	public void specialLimitMustBeGreaterThanEqualZero() {
		quantifiedItem.setSpecialLimit(-1);
		assertEquals(quantifiedItem.getSpecialLimit(), 0);
	}
	
	@Test
	public void itemNameCannotBeNullOrEmptyString() {
		try {
			QuantifiedItem quantifiedItem = new QuantifiedItem(null, 0.00);
		} catch (IllegalArgumentException ex) {
			assertEquals(ex.getMessage(), "Item name cannot be null or empty String");
		}
		try {
			QuantifiedItem quantifiedItem = new QuantifiedItem("", 0.00);
		} catch (IllegalArgumentException ex) {
			assertEquals(ex.getMessage(), "Item name cannot be null or empty String");
		}
	}
	
	@Test
	public void itemPriceMustBeGreaterThanZero() {
		try {
			QuantifiedItem quantifiedItem = new QuantifiedItem("Test", 0.00);
		} catch (IllegalArgumentException ex) {
			assertEquals(ex.getMessage(), "Item price must be greater than zero");
		}
	}
}
