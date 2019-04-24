package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pillar.Store;

public class StoreTest {

	@Test
	public void EmptyStoreCreated() {
		Store store = new Store();
		assertTrue(store.getStoreItems().size() == 0);
	}
}
