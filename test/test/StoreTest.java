package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pillar.Store;
import pillar.item.QuantifiedItem;

public class StoreTest {

	@Test
	public void emptyStoreCreated() {
		Store store = new Store();
		assertTrue(store.getStoreItems().size() == 0);
	}
	
	@Test
	public void canAddQuantifiedItemToStore() {
		QuantifiedItem testItem = new QuantifiedItem("Test", 2.59);
		Store testStore = new Store();
		testStore.addItem(testItem);
		assertTrue(testStore.getItem(testItem.getName()) != null);
	}
}
