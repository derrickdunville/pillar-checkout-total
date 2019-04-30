package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import pillar.Store;
import pillar.item.QuantifiedItem;
import pillar.exception.RangeException;

public class StoreTest {

	@Test
	public void emptyStoreCreated() {
		Store store = new Store();
		assertTrue(store.getStoreItems().size() == 0);
	}
	
	@Test
	public void canAddQuantifiedItemToStore() {
		try {
			QuantifiedItem testItem = new QuantifiedItem("Test", 2.59);
			Store testStore = new Store();
			testStore.addItem(testItem);
			assertTrue(testStore.getItem(testItem.getName()) != null);
		} catch (RangeException e) {
			fail();
		}
	}
}
