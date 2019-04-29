package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import pillar.item.QuantifiedItem;
import pillar.item.WeightedItem;
import pillar.item.exception.InvalidSpecialException;
import pillar.item.exception.RangeException;

public class ItemTest {

	double delta = 0.000001;
	QuantifiedItem quantifiedItem;
	WeightedItem weightedItem; 
	
	@Before
	public void initializeTest() {
		try {
			quantifiedItem = new QuantifiedItem("QuantifiedItem", 2.59);
			weightedItem = new WeightedItem("WeightedItem", 2.59);
		} catch (RangeException e) {
			fail();
		}	
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
		try {
			double priceBeforeMarkdown = quantifiedItem.getPrice();
			quantifiedItem.setMarkdown(0.29);
			assertEquals(quantifiedItem.getPrice(), priceBeforeMarkdown - 0.29, delta);
			
			priceBeforeMarkdown = weightedItem.getPrice();
			weightedItem.setMarkdown(0.39);
			assertEquals(weightedItem.getPrice(), priceBeforeMarkdown - 0.39, delta);
		} catch (RangeException e) {
			fail();
		}
	}
	
	@Test
	public void addBuyNGetMAtPercentOffSpecialToQuantifiedItem() {
		try {
			quantifiedItem.setSpecial(1, 1, 100.00);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 1);
			assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 1);
			assertEquals(quantifiedItem.getSpecialDiscountPercent(), 100.00, delta);
		} catch (RangeException e) {
			fail();
		}
	}
	
	@Test
	public void addBuyNForXPrice() {
		try {
			quantifiedItem.setSpecial(3, 5.00);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 3);
			assertEquals(quantifiedItem.getDiscountPrice(), 5.00, delta);
		} catch (RangeException | InvalidSpecialException e) {
			fail();
		}
	}
	
	@Test
	public void addSpecialLimit() {
		try {
			quantifiedItem.setSpecial(2,  1, 100.00);
			quantifiedItem.setSpecialLimit(6);
			assertEquals(quantifiedItem.getSpecialLimit(), 6);
		} catch (RangeException e) {
			fail();
		}
	}
	
	@Test
	public void specialMustBeCheaperThenFullPrice() {
		boolean invalidSpecialExceptionCaught = false;
		try {
			quantifiedItem.setSpecial(3, quantifiedItem.getPrice()*3 + .01);
		} catch (RangeException | InvalidSpecialException e) {
			invalidSpecialExceptionCaught = true;
			assertTrue(e instanceof InvalidSpecialException);
		} finally {
			assertTrue(invalidSpecialExceptionCaught);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
		}
	}
	
	@Test
	public void settingSpecialNForXShouldClearBuyNGetNXPercentOff() {
		try {
			quantifiedItem.setSpecial(2, 1, 100.00);
			quantifiedItem.setSpecial(3, 5.00);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 3);
			assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
			assertEquals(quantifiedItem.getDiscountPrice(), 5.00, delta);
		} catch (RangeException | InvalidSpecialException e) {
			fail();
		}
	}
	
	@Test
	public void settingBuyNGetNXPercentOffShouldClearNForX() {
		try {
			quantifiedItem.setSpecial(3, 5.00);
			quantifiedItem.setSpecial(2, 1, 100.00);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 2);
			assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 1);
			assertEquals(quantifiedItem.getSpecialDiscountPercent(), 100.00, delta);
			assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
		} catch (RangeException | InvalidSpecialException e) {
			fail();
		}
	}
	
	@Test
	public void canClearNForXSpecial() {
		try {
			quantifiedItem.setSpecial(3, 5);
			quantifiedItem.setSpecial(0, 0.0);
			assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
		} catch (RangeException | InvalidSpecialException e) {
			fail();
		}
	}
	
	@Test
	public void canClearBuyNGetMForXPercentOffSpecial() {
		try {
			quantifiedItem.setSpecial(2, 1, 100.00);
			quantifiedItem.setSpecial(0, 0, 0.0);
			assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
		} catch (RangeException e) {
			fail();
		}
	}
	
	@Test
	public void settingBuyNGetMForXPercentOffForQuantifiedItemWithAValueNotWithinRangeShouldThrowRangeException() {
		boolean rangeExceptionCaught = false;
		try {
			quantifiedItem.setSpecial(0, 1, 100.00);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger quantity, discounted quantity and discount percent must either all be 0 or all greater than 0.");
			rangeExceptionCaught = true;
		} finally {
			assertTrue(rangeExceptionCaught);
		}
		
		rangeExceptionCaught = false;
		try {
			quantifiedItem.setSpecial(1, 0, 100.00);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger quantity, discounted quantity and discount percent must either all be 0 or all greater than 0.");
			rangeExceptionCaught = true;
		} finally {
			assertTrue(rangeExceptionCaught);
		}
		
		rangeExceptionCaught = false;
		try {
			quantifiedItem.setSpecial(1, 1, 0.0);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger quantity, discounted quantity and discount percent must either all be 0 or all greater than 0.");
			rangeExceptionCaught = true;
		} finally {
			assertTrue(rangeExceptionCaught);
		}
	}
	
	@Test
	public void settingBuyNGetMForXPercentOffForWeightedItemWithAValueNotWithinRangeShouldThrowRangeException() {
		boolean rangeExceptionCaught = false;
		try {
			weightedItem.setSpecial(0.0, 1.0, 100.00);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger weight, discounted weight and discount percent must either all be 0.0 or all greater than 0.0");
			rangeExceptionCaught = true;
		} finally {
			assertTrue(rangeExceptionCaught);
		}
		
		rangeExceptionCaught = false;
		try {
			weightedItem.setSpecial(1.0, 0.0, 100.00);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger weight, discounted weight and discount percent must either all be 0.0 or all greater than 0.0");
			rangeExceptionCaught = true;
		} finally {
			assertTrue(rangeExceptionCaught);
		}
		
		rangeExceptionCaught = false;
		try {
			weightedItem.setSpecial(1.0, 1.0, 0.0);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger weight, discounted weight and discount percent must either all be 0.0 or all greater than 0.0");
			rangeExceptionCaught = true;
		} finally {
			assertTrue(rangeExceptionCaught);
		}
		
		rangeExceptionCaught = false;
		try {
			weightedItem.setSpecial(-1.0, 1.0, 100.0);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger weight must be greater than 0.0");
			rangeExceptionCaught = true;
		} finally {
			assertTrue(rangeExceptionCaught);
		}
		
		rangeExceptionCaught = false;
		try {
			weightedItem.setSpecial(1.0, -1.0, 100.0);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "discounted weight must be greater than 0.0");
			rangeExceptionCaught = true;
		} finally {
			assertTrue(rangeExceptionCaught);
		}
	}
	
	@Test
	public void BuyNGetMForXPercentOffMustHavePercentBetweenZeroAndHundredInclusive() {
		try {
			quantifiedItem.setSpecial(2, 1, -0.01);
		} catch (RangeException e) {
			assertEquals(e.getMessage(), "discount percent must be between greater than or equal to 0.00 and less than or equal to 100.00");
		} finally {
			assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
		}
		
		try {	
			quantifiedItem.setSpecial(2, 1, 100.01);
		} catch (RangeException e) {
			assertEquals(e.getMessage(), "discount percent must be between greater than or equal to 0.00 and less than or equal to 100.00");
		} finally {
			assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
		}
	}
	
	@Test
	public void BuyNGetMForXPercentOffMustHaveTriggerQuantityGreaterThenEqualToZero() {
		try {
			quantifiedItem.setSpecial(-1, 1, 100.0);

		} catch (RangeException e) {
			assertEquals(e.getMessage(), "trigger quantity must be greater than or equal to 0");
		} finally {
			assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
		}
	}
	
	@Test
	public void BuyNGetMForXPercentOffMustHaveDiscountedQuantityGreaterThenEqualToZero() {
		try {
			quantifiedItem.setSpecial(1, -1, 100.0);
		} catch (RangeException e) {
			assertEquals(e.getMessage(), "discounted quantity must be greater than or equal to 0");
		} finally {
			assertEquals(quantifiedItem.getSpecialDiscountedQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getSpecialDiscountPercent(), 0.0, delta);
		}
	}
	
	@Test
	public void BuyNForXMustHaveTriggerQuantityGreaterThanEqualZero() {
		try {
			quantifiedItem.setSpecial(-1, 5.00);
		} catch (RangeException | InvalidSpecialException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger quantity must be greater than or equal to 0.");
		} finally {
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
		}
	}
	
	@Test
	public void BuyNForXMustHaveDiscountPriceGreaterThanEqualZero() {
		try {
			quantifiedItem.setSpecial(2, -1.00);
		} catch (RangeException | InvalidSpecialException e) {
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "discount price must be greater than or equal to 0.");
		} finally {
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
		}
	}
	
	@Test
	public void BuyNForXMustHaveBothTriggerQuantityAndDiscountPriceGreaterThanZeorWhenSettingSpeical() {
		boolean discountRangeExceptionCaught = false;
		try {
			quantifiedItem.setSpecial(2, 0);
		} catch (RangeException | InvalidSpecialException e) {
			discountRangeExceptionCaught = true;
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger quantity and discount price must either be both 0 or both greater than 0.");
		} finally {
			assertTrue(discountRangeExceptionCaught);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
		}
		
		boolean triggerQuantityRangeExceptionCaught = false;
		try {
			quantifiedItem.setSpecial(0, 2.00);
		} catch (RangeException | InvalidSpecialException e) {
			triggerQuantityRangeExceptionCaught = true;
			assertTrue(e instanceof RangeException);
			assertEquals(e.getMessage(), "trigger quantity and discount price must either be both 0 or both greater than 0.");
		} finally {
			assertTrue(triggerQuantityRangeExceptionCaught);
			assertEquals(quantifiedItem.getSpecialTriggerQuantity(), 0);
			assertEquals(quantifiedItem.getDiscountPrice(), 0.0, delta);
		}
	}
	
	@Test
	public void specialLimitMustBeGreaterThanEqualZero() {
		try {
			quantifiedItem.setSpecialLimit(-1);
		} catch (RangeException e) {
			assertEquals(e.getMessage(), "special limit must be greater than or equal to 0.");
		} finally {
			assertEquals(quantifiedItem.getSpecialLimit(), 0);
		}
	}
	
	@Test
	@SuppressWarnings("unused")
	public void itemNameCannotBeNullOrEmptyString() {
		try {
			QuantifiedItem quantifiedItem = new QuantifiedItem(null, 0.00);
		} catch (IllegalArgumentException | RangeException ex) {
			assertEquals(ex.getMessage(), "Item name cannot be null or empty String");
		}
		try {
			QuantifiedItem quantifiedItem = new QuantifiedItem("", 0.00);
		} catch (IllegalArgumentException | RangeException ex) {
			assertEquals(ex.getMessage(), "Item name cannot be null or empty String");
		}
	}
	
	@Test
	@SuppressWarnings("unused")
	public void itemPriceMustBeGreaterThanZero() {
		boolean rangeExceptionCaught = false;
		try {
			QuantifiedItem quantifiedItem = new QuantifiedItem("Test", 0.00);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			rangeExceptionCaught = true;
			assertEquals(e.getMessage(), "Item price must be greater than zero");
		} finally {
			assertTrue(rangeExceptionCaught);
		}
	}
	
	@Test
	public void markdownCannotBeGreaterThenEqualPrice() {
		boolean rangeExceptionCaught = false;
		try {
			quantifiedItem.setMarkdown(quantifiedItem.getPrice());
			assertTrue(quantifiedItem.getPrice() > 0);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			rangeExceptionCaught = true;
			assertEquals(e.getMessage(), "Markdown amount must be less than item price.");
		} finally {
			assertTrue(rangeExceptionCaught);
		}
		
		rangeExceptionCaught = false;
		try {
			quantifiedItem.setMarkdown(quantifiedItem.getPrice() + .01);
			assertTrue(quantifiedItem.getPrice() > 0);
		} catch (RangeException e) {
			assertTrue(e instanceof RangeException);
			rangeExceptionCaught = true;
			assertEquals(e.getMessage(), "Markdown amount must be less than item price.");
		} finally {
			assertTrue(rangeExceptionCaught);
		}
	}
}
