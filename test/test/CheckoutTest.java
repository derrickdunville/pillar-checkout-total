package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import pillar.Checkout;
import pillar.Store;
import pillar.item.QuantifiedItem;
import pillar.item.WeightedItem;
import pillar.item.exception.InvalidSpecialException;
import pillar.item.exception.ItemNotFoundException;
import pillar.item.exception.QuantifiedItemException;
import pillar.item.exception.RangeException;
import pillar.item.exception.WeightedItemException;

public class CheckoutTest {

	double delta = 0.000001;
	Store store;
	QuantifiedItem quantifiedItemOne;
	QuantifiedItem quantifiedItemTwo;
	QuantifiedItem quantifiedItemNotInStore;
	WeightedItem weightedItemOne;
	WeightedItem weightedItemTwo;
	WeightedItem weightedItemNotInStore;
	Checkout checkout;
	
	@Before
	public void initializeTest() {
		store = new Store();
		quantifiedItemOne = new QuantifiedItem("QuantifiedItemOne", 2.59);
		quantifiedItemTwo = new QuantifiedItem("QuantifiedItemTwo", 1.59);
		quantifiedItemNotInStore = new QuantifiedItem("NotInStore", 1.29);
		weightedItemOne = new WeightedItem("WeightedItemOne", 1.49);
		weightedItemTwo = new WeightedItem("WeightedItemTwo", 1.79);
		weightedItemNotInStore = new WeightedItem("WeightedNotInStore", 1.29);
		store.addItem(quantifiedItemOne);
		store.addItem(quantifiedItemTwo);
		store.addItem(weightedItemOne);
		store.addItem(weightedItemTwo);
		checkout = new Checkout(store);
	}
	
	@Test
	public void canScanAQuantifiedItem(){
		try {
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 2.59, delta);
		} catch (ItemNotFoundException | WeightedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanTwoDifferentQuantifiedItems() {
		try {
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemTwo.getName()), quantifiedItemOne.getPrice() + quantifiedItemTwo.getPrice(), delta);
		} catch (ItemNotFoundException | WeightedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanAQuantifiedItemMultipleTimes() {
		try {
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice()*2, delta);
		} catch (ItemNotFoundException | WeightedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanAWeightedItem() {
		try {
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.0), weightedItemOne.getPrice() * 1.0, delta);
		} catch (ItemNotFoundException | QuantifiedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanTwoDifferentWeightedItems() {
		try {
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), weightedItemOne.getPrice() * 1.5, delta);
			assertEquals(checkout.scanItem(weightedItemTwo.getName(), 1.5), weightedItemOne.getPrice() * 1.5 + weightedItemTwo.getPrice() * 1.5, delta);
		} catch (ItemNotFoundException | QuantifiedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanAWeightedItemMulitipleTimes() {
		try {
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), weightedItemOne.getPrice() * 1.5, delta);
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), weightedItemOne.getPrice() * 1.5 * 2, delta);
		} catch (ItemNotFoundException | QuantifiedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanBothAWeightedItemAndAQuatifiedItem() {
		try {
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), quantifiedItemOne.getPrice() + weightedItemOne.getPrice() * 1.5 , delta);
		} catch (ItemNotFoundException | WeightedItemException | QuantifiedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanMultipleWeightedItemsAndQuatifiedItems() {
		try {
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), quantifiedItemOne.getPrice() + weightedItemOne.getPrice() * 1.5 , delta);
			assertEquals(checkout.scanItem(quantifiedItemTwo.getName()), quantifiedItemOne.getPrice() + weightedItemOne.getPrice() * 1.5 + quantifiedItemTwo.getPrice(), delta);
			assertEquals(checkout.scanItem(weightedItemTwo.getName(), 2.5), quantifiedItemOne.getPrice() + weightedItemOne.getPrice() * 1.5 + quantifiedItemTwo.getPrice() + weightedItemTwo.getPrice() * 2.5, delta);
		} catch (ItemNotFoundException | WeightedItemException | QuantifiedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanAQuantifiedItemWithAMarkdown() {
		try {
			double priceBeforeMarkdown = quantifiedItemOne.getPrice();
			quantifiedItemOne.setMarkdown(0.29);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), priceBeforeMarkdown - 0.29, delta);
		} catch (ItemNotFoundException | WeightedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanAWeightedItemWithAMarkdown() {
		try {
			double priceBeforeMarkdown = weightedItemOne.getPrice();
			weightedItemOne.setMarkdown(0.39);
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), (priceBeforeMarkdown - 0.39)*1.5, delta);
		} catch (ItemNotFoundException | QuantifiedItemException e) {
			fail();
		}
	}
	
	@Test 
	public void scanTwoWithBuyOneGetOneFreeSpecial() {
		try {
			quantifiedItemOne.setSpecial(1, 1, 100.00);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			// 2nd Item should be free
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException e) {
			fail();
		}
	}
	
	@Test
	public void canScanThreeWithBuyTwoGetOneFreeSpecial() throws ItemNotFoundException, WeightedItemException {
		try {
			quantifiedItemOne.setSpecial(2, 1, 100.00);
			// Scan 2 items
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
			// Scan a 3rd item and should be total of 2, since buy 2 get 1 free special is set
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException e) {
			fail();
		}
	}
		
	@Test
	public void canScanSixWithBuyTwoGetOneFreeSpecial() {
		try {
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
		} catch (ItemNotFoundException | WeightedItemException | RangeException e) {
			fail();
		}
	}
	
	@Test
	public void canScanTwoWithBuyOneGetOneHalfOffSpecial() {
		try {
			quantifiedItemOne.setSpecial(1, 1, 50.00);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			// 2nd item should be half price
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 1.5, delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException e) {
			fail();
		}
	}
	
	@Test
	public void canScanFourWithBuyTwoGetTwoHalfOffSpecial() {
		try {
			// essentially the same as buy 3 get one free
			quantifiedItemOne.setSpecial(2, 2, 50.00);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 3, delta);
			// Scanning a 4th should trigger the special
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 3, delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException e) {
			fail();
		}
	}
	
	@Test
	public void canScanThreeWithThreeForFiveSpecial() {
		try {
			quantifiedItemOne.setSpecial(3, 5.00);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 5.00, delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException | InvalidSpecialException e) {
			fail();
		}
	}
	
	@Test
	public void canScanFiveWithThreeForFiveSpecial()  {
		try {
			quantifiedItemOne.setSpecial(3, 5.00);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 5.00, delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 5.00 + quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 5.00 + quantifiedItemOne.getPrice() * 2, delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException | InvalidSpecialException e) {
			fail();
		}
	}
	
	@Test
	public void canScanSixWithThreeForFiveSpecial() {
		try {
			quantifiedItemOne.setSpecial(3, 5.00);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 5.00, delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 5.00 + quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 5.00 + quantifiedItemOne.getPrice() * 2, delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), 10.00, delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException | InvalidSpecialException e) {
			fail();
		}
	}
	
	@Test
	public void canScanEightWithBuyTwoGetOneFreeSpecialLimitSix() {
		try {
			quantifiedItemOne.setSpecial(2, 1, 100.00);
			quantifiedItemOne.setSpecialLimit(6);
			for(int i = 0; i < 7; ++i) {
				checkout.scanItem(quantifiedItemOne.getName());
			}
			// 8th item should not be discounted since limit is 6
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice()*6, delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException e) {
			fail();
		}
	}
	
	@Test
	public void canScanNineWithThreeForFiveSpecialLimitSix() {
		try {
			quantifiedItemOne.setSpecial(3, 5.00);
			quantifiedItemOne.setSpecialLimit(6);
			for(int i = 0; i < 8; ++i) {
				checkout.scanItem(quantifiedItemOne.getName());
			}
			// 3rd set of 3 should be full price
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice()*3 + 10.00, delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException | InvalidSpecialException e) {
			fail();
		}
	}
	
	@Test
	public void canRemoveAScannedQuantifiedItem() {
		try {
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.removeItem(quantifiedItemOne.getName()), 0, delta);	
		} catch (ItemNotFoundException | WeightedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canRemoveQuantifiedItemThatHasABuy2Get1FreeSpecial() {
		try {
			quantifiedItemOne.setSpecial(2, 1, 100.00);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice(), delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2 , delta);
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);
			assertEquals(checkout.removeItem(quantifiedItemOne.getName()), quantifiedItemOne.getPrice() * 2, delta);	
		} catch (ItemNotFoundException | WeightedItemException | RangeException e) {
			fail();
		}
	}
	
	@Test
	public void canRemoveAScannedWeightedItem() {
		try {
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 1.5), weightedItemOne.getPrice() * 1.5, delta);
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 2.0), weightedItemOne.getPrice() * (1.5 + 2.0) , delta);
			assertEquals(checkout.removeItem(weightedItemOne.getName(), 1.5), weightedItemOne.getPrice() * 2.0, delta);
		} catch (ItemNotFoundException | QuantifiedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanThreePoundsWithBuyTwoPoundsGetOnePoundFreeSpecial() {
		weightedItemOne.setSpecial(2.0, 1.0, 100.0);
		try {
			assertEquals(checkout.scanItem(weightedItemOne.getName(), 3.0), weightedItemOne.getPrice() * 2.0, delta);
		} catch (ItemNotFoundException | QuantifiedItemException e) {
			fail();
		}
	}
	
	@Test
	public void canScanTwoWithBuyOneGetOneFreeSpecialAndMarkdown() {
		try {
			double priceBeforeMarkdown = quantifiedItemOne.getPrice();
			quantifiedItemOne.setSpecial(1, 1, 100.0);
			quantifiedItemOne.setMarkdown(0.29);
			checkout.scanItem(quantifiedItemOne.getName());
			assertEquals(checkout.scanItem(quantifiedItemOne.getName()), priceBeforeMarkdown - 0.29, delta);
		} catch (ItemNotFoundException | WeightedItemException | RangeException e) {
			fail();
		}
	}
	
	@Test
	public void cantScanAQuantifiedItemThatIsNotAValidStoreItem() {
		boolean itemNotFoundExceptionCaught = false;
		try {
			assertEquals(checkout.scanItem(quantifiedItemNotInStore.getName()), 0.0, delta);
		} catch (ItemNotFoundException | WeightedItemException ex) {
			itemNotFoundExceptionCaught = true;
			assertTrue(ex instanceof ItemNotFoundException);
			assertEquals(ex.getMessage(), "Item not found");
		} finally {
			assertTrue(itemNotFoundExceptionCaught);
		}
	}
	
	@Test
	public void cantScanAWeightedItemThatIsNotAValidStoreItem() {
		boolean itemNotFoundExceptionCaught = false;
		try {
			assertEquals(checkout.scanItem(weightedItemNotInStore.getName(), 2.00), 0.0, delta);
		} catch (ItemNotFoundException | QuantifiedItemException e) {
			assertTrue(e instanceof ItemNotFoundException);
			itemNotFoundExceptionCaught = true;
		} finally {
			assertTrue(itemNotFoundExceptionCaught);
		}
	}
	
	@Test
	public void removingAQuantifiedItemThatHasNotBeenScannedShouldThrowItemNotFoundException() {
		boolean itemNotFoundExceptionCaught = false;
		try {
			checkout.scanItem(quantifiedItemOne.getName());
			assertEquals(checkout.removeItem(quantifiedItemTwo.getName()), quantifiedItemOne.getPrice(), delta);
		} catch (ItemNotFoundException | WeightedItemException e) {
			assertTrue(e instanceof ItemNotFoundException);
			itemNotFoundExceptionCaught = true;
		} finally {
			assertTrue(itemNotFoundExceptionCaught);
		}
	}
	
	@Test
	public void removingAWeightedItemThatHasNotBeenScannedShouldThrowItemNotFoundException() {
		boolean itemNotFoundExceptionCaught = false;
		try {
			checkout.scanItem(weightedItemOne.getName(), 1.5);
			assertEquals(checkout.removeItem(weightedItemTwo.getName(), 2.0), weightedItemOne.getPrice() * 1.5, delta);
		} catch (ItemNotFoundException | QuantifiedItemException e) {
			assertTrue(e instanceof ItemNotFoundException);
			itemNotFoundExceptionCaught = true;
		} finally {
			assertTrue(itemNotFoundExceptionCaught);
		}
	}
	
	@Test
	@SuppressWarnings("unused")
	public void storeCannotBeNull() {
		boolean exceptionCaught = false;
		try {
			Checkout testCheckout = new Checkout(null);
		} catch (IllegalArgumentException ex) {
			exceptionCaught = true;
			assertEquals(ex.getMessage(), "Store can not be null.");
		} finally {
			assertTrue(exceptionCaught);
		}
	}
	
	@Test
	public void scanningAWeightedItemWithoutProvidingWeightShouldThrowWeightedItemException() {
		boolean weightedItemExceptionCaught = false;
		try {
			checkout.scanItem(weightedItemOne.getName());
		} catch (ItemNotFoundException | WeightedItemException e) {
			assertTrue(e instanceof WeightedItemException);
			assertEquals(e.getMessage(), "Must provide item weight");
			weightedItemExceptionCaught = true;
		} finally {
			assertTrue(weightedItemExceptionCaught);
		}
	}
	
	@Test
	public void scanningAQuantifiedItemWhileProvidingWeightShouldThrowQuantifiedItemException() {
		boolean quantifiedItemExceptionCaught = false;
		try {
			checkout.scanItem(quantifiedItemOne.getName(), 1.25);
		} catch (ItemNotFoundException | QuantifiedItemException e) {
			assertTrue(e instanceof QuantifiedItemException);
			assertEquals(e.getMessage(), "Item weight should not be provided");
			quantifiedItemExceptionCaught = true;
		} finally {
			assertTrue(quantifiedItemExceptionCaught);
		}
	}
}
